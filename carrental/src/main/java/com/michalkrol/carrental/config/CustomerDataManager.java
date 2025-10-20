package com.michalkrol.carrental.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "customers")
public class CustomerDataManager {

    private List<CustomerConfig> data = Collections.emptyList();

    public List<CustomerConfig> getData() {
        return data;
    }

    public void setData(List<CustomerConfig> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CustomerDataManager{" +
                "data=" + data +
                '}';
    }
}


