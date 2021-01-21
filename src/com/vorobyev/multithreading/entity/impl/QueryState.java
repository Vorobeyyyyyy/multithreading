package com.vorobyev.multithreading.entity.impl;

import com.vorobyev.multithreading.entity.AbstractTruckState;
import com.vorobyev.multithreading.entity.Base;
import com.vorobyev.multithreading.entity.Truck;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class QueryState extends AbstractTruckState {
    private final static Logger logger = LogManager.getLogger();

    private Lock locker = new ReentrantLock();

    private final static QueryState INSTANCE = new QueryState();

    public static QueryState getInstance() {
        return INSTANCE;
    }

    private QueryState() {
    }

    @Override
    public void action(Truck actionTruck) {
        logger.log(Level.INFO, "Truck {} in WAITING state", actionTruck.getTruckId());
        Base.getInstance().serveTruck(actionTruck);
        logger.log(Level.INFO, "Truck {} come to terminal", actionTruck.getTruckId());
        actionTruck.setTruckState(ServingState.getInstance());
    }
}
