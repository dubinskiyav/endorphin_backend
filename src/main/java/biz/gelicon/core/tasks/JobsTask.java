package biz.gelicon.core.tasks;

import biz.gelicon.core.artifacts.ArtifactKinds;
import biz.gelicon.core.artifacts.ArtifactManagerImpl;
import biz.gelicon.core.components.core.capresource.Artifact;
import biz.gelicon.core.components.core.capjob.Capjob;
import biz.gelicon.core.components.core.capjobhistory.CapjobhistoryRepository;
import biz.gelicon.core.components.core.capjob.CapjobRepository;
import biz.gelicon.core.components.core.capresource.ArtifactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class JobsTask {
    @Autowired
    private CapjobRepository capjobRepository;
    @Autowired
    private CapjobhistoryRepository capjobhistoryRepository;
    @Autowired
    private ArtifactRepository artifactRepository;
    @Autowired
    private ArtifactManagerImpl manager;

    @Value("${gelicon.core.task.shedule.checkjob}")
    private String sheduleCron;

    private static final Logger logger = LoggerFactory.getLogger(JobsTask.class);

    public static final int PERIOD_DAY = 9901; // Ежедневно
    public static final int PERIOD_WEEK = 9902; // Еженедельно
    public static final int PERIOD_MONTH = 9903; // Ежемесячно
    public static final int PERIOD_YEAR = 9904; // Ежегодно
    public static final int PERIOD_HOUR = 9905; // Ежечасно
    public static final int PERIOD_MINUTE = 9906; // Ежеминутно
    public long jobTimeout;


    @PostConstruct
    public void setup() {
        jobTimeout = calculateJobTime();
    }

    /*
     * Запускает проверку нгаличия задач к запуску. Если есть то запускает
     * Запуск, каждые 5 минут (по умолчанию)
     */
    @Scheduled(cron = "${gelicon.core.task.shedule.checkjob}")
    @Transactional
    public void process() {
        Calendar HostCalendar 	= Calendar.getInstance();
        Calendar BeginCalendar	= Calendar.getInstance();
        Calendar LastCalendar 	= Calendar.getInstance();
        Timestamp current 		= new Timestamp(HostCalendar.getTimeInMillis());
        String[] 	days;
        long 	last;
        try {
            List<Capjob> list = capjobRepository.findWhere("m0.capjob_flagactive=1 and m0.capresource_id IS NOT NULL");

            for (Capjob item : list) {
                Integer capjobId = item.getCapjobId();
                Integer capjobIterationcount = item.getCapjobIterationcount();

                Timestamp capjobDatebeg = (Timestamp) item.getCapjobDatebeg();
                Timestamp capjobDateend = (Timestamp) item.getCapjobDateend();
                Integer periodId = item.getPeriodId();

                String capjobDays = (String) item.getCapjobDays();
                Integer capjobInterval = (Integer) item.getCapjobInterval();
                Integer monthcalId = (Integer) item.getMonthcalId();
                Integer dayofweekId = (Integer) item.getDayofweekId();
                Integer daycalId = (Integer) item.getDaycalId();
                Integer capjobWeeknumber = (Integer) item.getCapjobWeeknumber();
                Integer capResourceId = (Integer) item.getCapresourceId();

                Boolean run = false;

                String sql2 = "";
                if (capjobIterationcount == null) {
                    sql2 = "" +
                            "SELECT MAX(CJH.capjobhistory_datebeg) capjobhistory_datebeg " +
                            "FROM   capjobhistory CJH " +
                            "WHERE  CJH.capjob_id = :capjobId " +
                            "GROUP BY CJH.capjob_id";
                }
                else {
                    sql2 = "" +
                            "SELECT MAX(CJH.capjobhistory_datebeg) capjobhistory_datebeg " +
                            "FROM   capjobhistory CJH " +
                            "WHERE  CJH.capjob_id = :capjobId " +
                            "       AND (SELECT COUNT(*) capjobhistory_count " +
                            "            FROM   capjobhistory " +
                            "            WHERE  capjob_id = :capjobId) < :capjobIterationCount " +
                            "GROUP BY CJH.capjob_id";
                }
                Map<String, Object> params = new HashMap<>();
                params.put("capjobId", capjobId);
                params.put("capjobIterationCount", capjobIterationcount);
                Timestamp lastRun = capjobhistoryRepository.findQueryForObject(Timestamp.class, sql2, params);

                BeginCalendar.setTimeInMillis(capjobDatebeg.getTime());

                if (lastRun != null) {
                    LastCalendar.setTimeInMillis(lastRun.getTime());

                    if(periodId != PERIOD_MINUTE){
                        if (periodId != PERIOD_HOUR)
                            LastCalendar.set(Calendar.HOUR_OF_DAY, BeginCalendar.get(Calendar.HOUR_OF_DAY));
                        LastCalendar.set(Calendar.MINUTE, BeginCalendar.get(Calendar.MINUTE));
                    }

                    last = LastCalendar.getTimeInMillis();
                } else
                    last = 0;
                if (current.compareTo(capjobDatebeg) < 0 || (capjobDateend != null && current.compareTo(capjobDateend) > 0))
                    continue;
                switch (periodId) {

                    case PERIOD_YEAR:
                        if (last == 0 || ((HostCalendar.getTimeInMillis() - last) > (24 * 60 * 60 * 1000L)))
                            if (monthcalId != 0) {
                                if (monthcalId == HostCalendar.get(Calendar.MONTH) + 1) {
                                    if (daycalId != 0)
                                        run = (daycalId == HostCalendar.get(Calendar.DAY_OF_MONTH));
                                    else if (capjobWeeknumber != 0) {
                                        if (dayofweekId != 0) {
                                            if (dayofweekId == getDayOfWeek(HostCalendar)
                                                    && (((HostCalendar.get(Calendar.DAY_OF_MONTH) - 1) / 7) + 1) == capjobWeeknumber) {
                                                if (capjobDays != null) {
                                                    days = capjobDays.split(";");
                                                    run = !"0".equalsIgnoreCase(days[HostCalendar.get(Calendar.MONTH)]);
                                                } else
                                                    run = true;
                                            }

                                        } else
                                            run = HostCalendar.get(Calendar.WEEK_OF_MONTH) == capjobWeeknumber;
                                    } else
                                        run = true;
                                }
                            } else
                                run =
                                        (last == 0 || ((HostCalendar.get(Calendar.YEAR) - LastCalendar.get(Calendar.YEAR)) >= capjobInterval));
                        break;

                    case PERIOD_MONTH:
                        if (last == 0 || ((HostCalendar.getTimeInMillis() - last) > (24 * 60 * 60 * 1000L))) {
                            if (daycalId != 0) {
                                if (capjobDays != null) {
                                    days = capjobDays.split(";");
                                    run =
                                            !"0".equalsIgnoreCase(days[HostCalendar.get(Calendar.MONTH)])
                                                    && daycalId == HostCalendar.get(Calendar.DAY_OF_MONTH);
                                } else
                                    run = daycalId == HostCalendar.get(Calendar.DAY_OF_MONTH);
                            } else if (capjobWeeknumber != 0) {
                                if (dayofweekId != 0) {
                                    if (dayofweekId == getDayOfWeek(HostCalendar)
                                            && (((HostCalendar.get(Calendar.DAY_OF_MONTH) - 1) / 7) + 1) == capjobWeeknumber) {
                                        if (capjobDays != null) {
                                            days = capjobDays.split(";");
                                            run = !"0".equalsIgnoreCase(days[HostCalendar.get(Calendar.MONTH)]);
                                        } else
                                            run = true;
                                    }
                                } else
                                    run = HostCalendar.get(Calendar.WEEK_OF_MONTH) == capjobWeeknumber;
                            } else if (capjobDays != null) {
                                days = capjobDays.split(";");
                                run = !"0".equalsIgnoreCase(days[HostCalendar.get(Calendar.MONTH)]);
                            } else
                                run =
                                        (last == 0 || (((HostCalendar.getTimeInMillis() - last) / (30 * 24 * 60 * 60 * 1000L)) >= capjobInterval)
                                                && HostCalendar.get(Calendar.MONTH) != LastCalendar.get(Calendar.MONTH));
                        }
                        break;

                    case PERIOD_WEEK:
                        if (last == 0 || ((HostCalendar.getTimeInMillis() - last) > (24 * 60 * 60 * 1000L))) {
                            if (capjobDays != null) {
                                days = capjobDays.split(";");
                                if (last == 0)
                                    run = !"0".equalsIgnoreCase(days[getDayOfWeek(HostCalendar) - 1]);
                                else
                                    run =(HostCalendar.get(Calendar.DAY_OF_WEEK) != LastCalendar.get(Calendar.DAY_OF_WEEK) && !"0".equalsIgnoreCase(days[getDayOfWeek(HostCalendar) - 1]));
                            } else
                                run = (last == 0 || (((HostCalendar.getTimeInMillis() - last) / (7 * 24 * 60 * 60 * 1000L)) >= capjobInterval));
                        }
                        break;

                    case PERIOD_DAY:
                        run =
                                (last == 0 || (((HostCalendar.getTimeInMillis() - last) / (24 * 60 * 60 * 1000L)) >= capjobInterval));
                        break;

                    case PERIOD_HOUR: {
                        // проверка соответствия cron и периода
                        final long period = capjobInterval * 60 * 60 * 1000L;
                        checkInterval(jobTimeout, period);

                        long time = HostCalendar.getTimeInMillis();

                        // Время следующего запуска
                        long next = last + period;

                        boolean isFirst = last == 0;
                        boolean isMustRun = (((time - last) / (60 * 60 * 1000L)) >= capjobInterval);
                        boolean isSoonMustRun = ((next - time)) <= (jobTimeout / 2);

                        run = isFirst || isMustRun || isSoonMustRun;
                    };
                    break;

                    case PERIOD_MINUTE: {
                        // проверка соответствия cron и периода
                        final long period = capjobInterval * 60 * 1000L;
                        checkInterval(jobTimeout, period);

                        long time = HostCalendar.getTimeInMillis();

                        long next = last + period;

                        boolean isFirst = last == 0;
                        boolean isMustRun = (((time - last) / (60 * 1000L)) >= capjobInterval);
                        boolean isSoonMustRun = ((next - time)) <= (jobTimeout / 2);

                        run = (isFirst || isMustRun || isSoonMustRun);
                    };
                    break;

                }
                boolean isAvilable = run;

                if(periodId != PERIOD_MINUTE && periodId != PERIOD_HOUR){
                    isAvilable = run && HostCalendar.get(Calendar.HOUR_OF_DAY) >= BeginCalendar.get(Calendar.HOUR_OF_DAY)
                            && HostCalendar.get(Calendar.MINUTE) >= BeginCalendar.get(Calendar.MINUTE);
                }


                Artifact atf = artifactRepository.getOne(capResourceId);
                if ((isAvilable) & (atf.getResourceTypeId() == ArtifactKinds.USER_FUNC.getResourceType())) {
                    // запускаем
                    run(atf,item.getCapjobFailiterationcount(),item.getCapjobFailiterationinterval());

                }
            }
        } catch (Exception ex) {
            logger.error("JobTask", ex);
        }
    }

    /**
     * Запускаем в другом потоке. Все попытки образуют очередь потоков.
     * Если поток выполнятся успешно, очередь закрывается
     * @param atf
     * @param maxAttemptCount
     * @param failIterationInterval
     */
    private void run(Artifact atf, Integer maxAttemptCount, Integer failIterationInterval) {
        Map<String, Object> params = new HashMap<>();
        params.put("idRes",atf.getArtifactId());
        params.put("maxAttempt",maxAttemptCount);
        ExecutorService es = Executors.newSingleThreadExecutor();
        for (int i = 0; i < maxAttemptCount; i++) {
            final int atemp = i;
            es.submit(()->{
                try {
                    logger.info(String.format("Call artifact id=%d, attempt %d ",atf.getArtifactId(), atemp));
                    if(atemp>0 && failIterationInterval>0) {
                        logger.info(String.format("-- delay %d sec",failIterationInterval));
                        Thread.sleep(failIterationInterval*1000);
                    }
                    params.put("attempt",atemp);
                    manager.run(ArtifactKinds.USER_FUNC.getResourceType(),atf.getArtifactCode(),params);
                    es.shutdownNow();
                }
                catch (InterruptedException ex) {
                    logger.info("Task interrupted",ex);
                    es.shutdownNow();
                } catch (Throwable ex) {
                    logger.error("Artifact fail: ",ex);
                }
            });
        }
        es.shutdown();
    }

    private int getDayOfWeek(Calendar calendar) {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1)
            return 7;
        else
            return day - 1;
    }

    private void checkInterval(Long cron,Long period) {
        if(cron>period/2) {
            logger.warn(String.format("Warning! The cron expression does not match one of the specified periods %d sec > %d/2 sec",
                    cron/1000,period/1000));
        }
    }

    private long calculateJobTime() {
        CronExpression expr = CronExpression.parse(sheduleCron);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = expr.next(now);
        now = next;
        next = expr.next(now);
        return Duration.between(now,next).get(ChronoUnit.SECONDS)*1000;
    }
}
