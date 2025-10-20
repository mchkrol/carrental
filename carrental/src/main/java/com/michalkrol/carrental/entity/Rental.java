package com.michalkrol.carrental.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CarType carType;
    private Long customerId;

    private LocalDate rentalDate;
    private Integer rentalDaysNumber;

    public Rental() {
    }

    private Rental(Builder builder) {
        this.id = builder.id;
        this.carType = builder.carType;
        this.customerId = builder.customerId;
        this.rentalDate = builder.rentalDate;
        this.rentalDaysNumber = builder.rentalDaysNumber;
    }

    public Long getId() {
        return id;
    }

    public CarType getCarType() {
        return carType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public Integer getRentalDaysNumber() {
        return rentalDaysNumber;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private CarType carType;
        private Long customerId;
        private LocalDate rentalDate;
        private Integer rentalDaysNumber;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder carType(CarType carType) {
            this.carType = carType;
            return this;
        }

        public Builder customerId(Long customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder rentalDate(LocalDate rentalDate) {
            this.rentalDate = rentalDate;
            return this;
        }

        public Builder rentalDaysNumber(Integer rentalDaysNumber) {
            this.rentalDaysNumber = rentalDaysNumber;
            return this;
        }

        public Rental build() {
            return new Rental(this);
        }
    }
}

