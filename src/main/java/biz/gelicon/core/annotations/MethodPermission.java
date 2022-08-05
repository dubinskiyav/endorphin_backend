package biz.gelicon.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodPermission {
    /**
     * noStore=true предотвращает сохранение и проверку дополнительных контролируемых
     * точек доступа с суффиксами #add и #edit. Иначе действует общее правило. Точки доступа заканчивающиеся
     * на get и save имеют дополнительные контролируемой точки доступа с суффиксами #add и #edit
     * на которые распространяются собственные права доступа
     *
     * @return
     */
    boolean noStore() default false;
}
