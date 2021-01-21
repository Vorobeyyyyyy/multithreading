package com.vorobyev.multithreading.main;

import com.vorobyev.multithreading.entity.Truck;
import com.vorobyev.multithreading.exception.ReaderException;
import com.vorobyev.multithreading.factory.TruckFactory;
import com.vorobyev.multithreading.parser.TruckDataParser;
import com.vorobyev.multithreading.reader.FileReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    private final static String DATA_PATH = "res/data.txt";

    public static void main(String[] args) {
        FileReader reader = new FileReader();
        TruckDataParser parser = new TruckDataParser();
        TruckFactory factory = new TruckFactory();
        try {
            List<Truck> trucks = factory.createTrucks(parser.parse(reader.read(DATA_PATH)));
            for (Truck truck : trucks) {
                truck.start();
            }
        } catch (ReaderException exception) {
            logger.log(Level.ERROR,exception.getMessage());
        }
    }
}
