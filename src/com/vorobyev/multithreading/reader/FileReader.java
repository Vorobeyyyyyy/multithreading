package com.vorobyev.multithreading.reader;

import com.vorobyev.multithreading.exception.ReaderException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {
    private static final Logger logger = LogManager.getLogger();

    private static final String DEFAULT_PATH = "res/data.txt";

    public String read(String path) throws ReaderException {
        boolean fileExists = Files.exists(Path.of(path));
        if (!fileExists) {
            path = DEFAULT_PATH;
            logger.log(Level.WARN, "File \"{}\" was not found, default will be used", path);
        }
        String result;
        try {
            result = Files.readString(Path.of(path));
            logger.log(Level.INFO,"File \"{}\" was successfully read", path);
        } catch (IOException exception) {
            logger.log(Level.ERROR, "Exception ({}) when reading file \"{}\"",exception.getMessage(), path);
            throw new ReaderException(exception);
        }
        return result;
    }
}
