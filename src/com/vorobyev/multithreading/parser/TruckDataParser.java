package com.vorobyev.multithreading.parser;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TruckDataParser {
    private static final Logger logger = LogManager.getLogger();

    private final static String SPACE = " ";

    public List<Integer> parse(String data) {
        List<Integer> result = new ArrayList<>();
        for (String unparsedValue: data.split(SPACE)){
            try {
                result.add(Integer.parseInt(unparsedValue));
            } catch (NumberFormatException exception) {
                logger.log(Level.ERROR, exception.getMessage());
            }
        }
        return result;
    }
}
