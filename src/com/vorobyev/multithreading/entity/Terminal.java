package com.vorobyev.multithreading.entity;

import com.vorobyev.multithreading.util.IdGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Terminal {
    private final static Logger logger = LogManager.getLogger();

    private final static int MINIMUM_PRODUCTS = 1;

    private final int id;

    private TerminalState state = TerminalState.FREE;

    private Truck truck;

    Terminal() {
        id = IdGenerator.generateId();
    }

    public int takeProducts(int wantedCount) {
        AtomicInteger count = Base.getInstance().getProductCount();
        int resultCount;
        if (count.get() >= wantedCount) {
            count.addAndGet(-wantedCount);
            resultCount = wantedCount;
        } else {
            resultCount = count.getAndSet(0);
        }
        if (resultCount == 0) {
            resultCount = MINIMUM_PRODUCTS;
        }
        try {
            Base.getInstance().getProductSemaphore().acquire(resultCount);
        } catch (InterruptedException exception) {
            logger.log(Level.ERROR, exception.getMessage());
        }
        return resultCount;
    }

    public void putProducts(int addedCount) {
        AtomicInteger count = Base.getInstance().getProductCount();
        Base.getInstance().getProductSemaphore().release(addedCount);
        count.addAndGet(addedCount);
    }

    public int getId() {
        return id;
    }

    public TerminalState getState() {
        return state;
    }

    public void setState(TerminalState state) {
        this.state = state;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
