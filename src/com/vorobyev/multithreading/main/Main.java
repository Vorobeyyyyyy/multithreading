package com.vorobyev.multithreading.main;

import com.vorobyev.multithreading.entity.Truck;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 8; i++) {
            new Truck().start();
        }
        try {
            Thread.currentThread().wait();
        } catch (InterruptedException exception) {

        }

    }
}
