package com.vorobyev.multithreading.entity.impl;

import com.vorobyev.multithreading.entity.AbstractTruckState;
import com.vorobyev.multithreading.entity.Base;
import com.vorobyev.multithreading.entity.Terminal;
import com.vorobyev.multithreading.entity.Truck;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ServingState extends AbstractTruckState {
    private final static Logger logger = LogManager.getLogger();

    private final static ServingState INSTANCE = new ServingState();

    private final static Base BASE = Base.getInstance();

    public static ServingState getInstance() {
        return INSTANCE;
    }

    private ServingState() {
    }

    @Override
    public void action(Truck actionTruck) {
        Optional<Terminal> optionalTerminal = BASE.findTruckTerminal(actionTruck);
        if (optionalTerminal.isEmpty()) {
            logger.log(Level.ERROR, "No truck {} terminal in serving stage", actionTruck.getTruckId());
            return;
        }

        Terminal terminal = optionalTerminal.get();
        if (actionTruck.getProductCount() != 0) {
            int productCountToUnload = actionTruck.getProductCount();
            terminal.putProducts(productCountToUnload);
            actionTruck.setProductCount(0);
            logger.log(Level.INFO, "Truck {} unload {} products", actionTruck.getTruckId(), productCountToUnload);
        } else {
            int takenProducts = terminal.takeProducts(actionTruck.getMaxProductCount());
            actionTruck.setProductCount(takenProducts);
            logger.log(Level.INFO, "Truck {} take {} products", actionTruck.getTruckId(), takenProducts);
        }
        BASE.leaveTruck(actionTruck);
        actionTruck.setTruckState(TransitState.getInstance());
    }
}
