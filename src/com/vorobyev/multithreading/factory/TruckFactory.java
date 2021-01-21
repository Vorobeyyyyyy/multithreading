package com.vorobyev.multithreading.factory;

import com.vorobyev.multithreading.entity.Truck;

import java.util.ArrayList;
import java.util.List;

public class TruckFactory {
    public List<Truck> createTrucks(List<Integer> values) {
        List<Truck> result = new ArrayList<>();
        for (Integer value : values) {
            result.add(new Truck(value));
        }
        return result;
    }
}
