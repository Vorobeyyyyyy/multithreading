package com.vorobyev.multithreading.entity;

import com.vorobyev.multithreading.entity.impl.TransitState;
import com.vorobyev.multithreading.util.IdGenerator;

public class Truck extends Thread {
    private AbstractTruckState truckState = TransitState.getInstance();

    private final int id = IdGenerator.generateId();

    private final int maxProductCount = 10;

    private int productCount = 0;

    @Override
    public void run() {
        super.run();
        while (true) {
            truckState.action(this);
        }
    }

    public AbstractTruckState getTruckState() {
        return truckState;
    }

    public void setTruckState(AbstractTruckState state) {
        this.truckState = state;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getMaxProductCount() {
        return maxProductCount;
    }

    public int getTruckId() {
        return id;
    }
}
