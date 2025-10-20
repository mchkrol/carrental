package com.michalkrol.carrental.config;

import com.michalkrol.carrental.entity.CarType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.michalkrol.carrental.entity.CarType.*;

@Component
@ConfigurationProperties(prefix = "rental")
public class RentalProperties {

    private Map<String, Integer> carsNumber = new HashMap<>();

    public Map<String, Integer> getCarsNumber() {
        return carsNumber;
    }

    public void setCarsNumber(Map<String, Integer> carsNumber) {
        this.carsNumber = carsNumber;
    }

    public Integer findCarsNumber(CarType carType) {
        if (SUV.equals(carType)) {
            return carsNumber.get("suvsnumber");
        } else if (SEDAN.equals(carType)) {
            return carsNumber.get("sedansnumber");
        } else if (VAN.equals(carType)) {
            return carsNumber.get("vansnumber");
        }
        return 0;
    }
}
