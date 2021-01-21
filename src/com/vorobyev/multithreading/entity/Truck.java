package com.vorobyev.multithreading.entity;

import com.vorobyev.multithreading.entity.impl.TransitState;
import com.vorobyev.multithreading.util.IdGenerator;

public class Truck extends Thread {
    private AbstractTruckState truckState = TransitState.getInstance();

    private final int id = IdGenerator.generateId();

    private final static int MAX_PRODUCT_COUNT = 10;

    private int productCount = 0;

    public Truck() {}

    public Truck(int initialProductCount) {
        productCount = initialProductCount;
    }

    @Override
    public void run() {
        while (truckState.hasNextStage()) {
            truckState.action(this);
        }

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
        return MAX_PRODUCT_COUNT;
    }

    public int getTruckId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Truck truck = (Truck) o;

        if (id != truck.id) return false;
        if (productCount != truck.productCount) return false;
        return truckState != null ? truckState.equals(truck.truckState) : truck.truckState == null;
    }

    @Override
    public int hashCode() {
        int result = truckState != null ? truckState.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + productCount;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Truck{");
        sb.append("truckState=").append(truckState);
        sb.append(", id=").append(id);
        sb.append(", productCount=").append(productCount);
        sb.append('}');
        return sb.toString();
    }
}
