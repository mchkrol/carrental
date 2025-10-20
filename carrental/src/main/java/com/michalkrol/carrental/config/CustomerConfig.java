package com.michalkrol.carrental.config;

import lombok.Data;

@Data
public class CustomerConfig {

    private String name;

    public CustomerConfig() {
    }

    private CustomerConfig(Builder builder) {
        this.name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;

        public Builder name(String model) {
            this.name = model;
            return this;
        }

        public CustomerConfig build() {
            return new CustomerConfig(this);
        }
    }

    @Override
    public String toString() {
        return "CustomerConfig{" +
                ", name='" + name + '\'' +
                '}';
    }
}
