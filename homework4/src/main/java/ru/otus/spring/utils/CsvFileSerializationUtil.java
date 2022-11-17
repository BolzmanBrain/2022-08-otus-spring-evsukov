package ru.otus.spring.utils;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Component
public class CsvFileSerializationUtil implements FileSerializationUtil {
    @Override
    public List<Object> serializeFile(String filename, Class type) throws Exception {
        File file = getFileFromResource(filename);
        List<Object> objects = new CsvToBeanBuilder(new FileReader(file))
                .withType(type)
                .withSkipLines(1)
                .build()
                .parse();
        return objects;
    }

    private File getFileFromResource(String fileName) throws Exception {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(fileName);
            if (resource == null) {
                throw new IllegalArgumentException("file not found! " + fileName);
            } else {
                // failed if files have whitespaces or special characters
                //return new File(resource.getFile());
                return new File(resource.toURI());
            }
        }
        catch (URISyntaxException e) {
            throw new Exception(String.format("File %s not found",fileName));
        }
    }
}
