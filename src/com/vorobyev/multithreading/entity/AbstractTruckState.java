package com.vorobyev.multithreading.entity;

public abstract class AbstractTruckState {
    public void action(Truck actionTruck) {
    }

    public boolean hasNextStage() {
        return true;
    }
}
