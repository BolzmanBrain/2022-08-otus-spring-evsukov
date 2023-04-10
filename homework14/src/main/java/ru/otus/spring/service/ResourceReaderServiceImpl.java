package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ResourceReaderServiceImpl implements ResourceReaderService {
    @Override
    public String read(String resourceFilename) {
        try {
            return Files.readString(Paths.get(getClass().getClassLoader().getResource(resourceFilename).toURI()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
