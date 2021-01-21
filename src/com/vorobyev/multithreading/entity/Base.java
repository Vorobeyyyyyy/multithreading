package com.vorobyev.multithreading.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Base {
    private final static Logger logger = LogManager.getLogger();

    private static final int TERMINAL_COUNT = 5;

    private static final int INITIAL_PRODUCT_COUNT = 50;

    private static Base INSTANCE;

    private final List<Terminal> terminals = new ArrayList<>();

    private final AtomicInteger productCount = new AtomicInteger(INITIAL_PRODUCT_COUNT);

    private final Semaphore terminalSemaphore = new Semaphore(TERMINAL_COUNT, true);

    private final Semaphore productSemaphore = new Semaphore(INITIAL_PRODUCT_COUNT, true);

    private static final Lock locker = new ReentrantLock();

    public static Base getInstance() {
        locker.lock();
        try {
            if (INSTANCE == null) {
                INSTANCE = new Base();
            }
        } finally {
            locker.unlock();
        }
        return INSTANCE;
    }

    private Base() {
        for (int i = 0; i < TERMINAL_COUNT; i++) {
            terminals.add(new Terminal());
        }
    }

    public void serveTruck(Truck truck) {
        try {
            terminalSemaphore.acquire();
            locker.lock();
            Optional<Terminal> optionalTerminal = findFreeTerminal();
            if (optionalTerminal.isPresent()) {
                Terminal terminal = optionalTerminal.get();
                terminal.setState(TerminalState.BUSY);
                terminal.setTruck(truck);
            }
        } catch (InterruptedException exception) {
            logger.log(Level.ERROR, exception.getMessage());
        } finally {
            locker.unlock();
        }
    }

    public void leaveTruck(Truck truck) {
        try {
            locker.lock();
            Optional<Terminal> optionalTerminal = terminals.stream().filter(o -> o.getTruck() == truck).findFirst();
            if (optionalTerminal.isPresent()) {
                Terminal terminal = optionalTerminal.get();
                terminal.setTruck(null);
                terminal.setState(TerminalState.FREE);
            }
        } finally {
            locker.unlock();
            terminalSemaphore.release();
        }

    }

    public Optional<Terminal> findTruckTerminal(Truck truck) {
        try {
            locker.lock();
            return terminals.stream().filter(o -> o.getTruck() == truck).findFirst();
        } finally {
            locker.unlock();
        }

    }

    private Optional<Terminal> findFreeTerminal() {
        return terminals.stream().filter(o -> o.getState().equals(TerminalState.FREE)).findFirst();
    }

    public AtomicInteger getProductCount() {
        return productCount;
    }

    public Semaphore getProductSemaphore() {
        return productSemaphore;
    }
}
