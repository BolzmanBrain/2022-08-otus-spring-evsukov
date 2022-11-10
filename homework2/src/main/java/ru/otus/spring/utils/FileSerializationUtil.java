package ru.otus.spring.utils;

import java.util.List;

public interface FileSerializationUtil {
    List<Object> serializeFile(String filename, Class type) throws Exception;
}
