package biz.gelicon.core.service;

import biz.gelicon.core.utils.UsefulUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;

@Service
public class EMSService {
    private static final Logger logger = LoggerFactory.getLogger(EMSService.class);
    /**
     * кол-во первых символов хеша, которые станут именем каталога
     */
    public static final int CATALOG_NAME_LENGTH = 2;

    @Value("${gelicon.core.file-storage.root}")
    private String rootDirectory;
    @Value("${gelicon.core.file-storage.uri}")
    private String fileStorageRootURI;
    @Value("${gelicon.run-as-test:false}")
    private Boolean runAsTest;


    public String saveIntoStorage(InputStream inStream, String ext) throws IOException, NoSuchAlgorithmException {
        BufferedInputStream buff = new BufferedInputStream(inStream);
        // сохраняем начальную позицию, чтобы вернуться в reset
        buff.mark(buff.available()+1);
        String hash = UsefulUtils.calculateHash(buff);
        // формируем имя и создаем новый каталог, если нет
        // копируем с учетом флага теста (чтобы не мусорить)
        if(!runAsTest) {
            File file = new File(rootDirectory + File.separator + hash.substring(0, CATALOG_NAME_LENGTH),
                    hash+(ext!=null?"."+ext:""));
            if (!file.getParentFile().exists()) {
                if(!file.getParentFile().mkdirs()) {
                    throw new IOException(String.format("Каталог {} не может быть создан", file.getParentFile().getAbsolutePath()));
                }
            }
            buff.reset();
            java.nio.file.Files.copy(buff,file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return hash;
    };

    public String getDownloadFromStorageLink(String hash,String ext){
        return fileStorageRootURI + "/" + hash.substring(0, CATALOG_NAME_LENGTH) + "/" + hash+(ext!=null?"."+ext:"");
    }

}
