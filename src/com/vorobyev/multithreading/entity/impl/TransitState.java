package com.vorobyev.multithreading.entity.impl;

import com.vorobyev.multithreading.entity.AbstractTruckState;
import com.vorobyev.multithreading.entity.Truck;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class TransitState extends AbstractTruckState {
    private final static Logger logger = LogManager.getLogger();

    private final static TransitState INSTANCE = new TransitState();

    private final static int TRANSIT_TIMEOUT = 1;

    public static TransitState getInstance() {
        return INSTANCE;
    }

    private TransitState() {
    }

    @Override
    public void action(Truck actionTruck) {
        logger.log(Level.INFO, "Truck {} is in TRANSIT state", actionTruck.getTruckId());
        try {
            TimeUnit.SECONDS.sleep(TRANSIT_TIMEOUT);
        } catch (InterruptedException exception) {
            logger.log(Level.ERROR, exception.getMessage());
        }
        actionTruck.setTruckState(QueryState.getInstance());
    }
}
