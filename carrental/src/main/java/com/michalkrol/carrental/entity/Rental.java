package com.michalkrol.carrental.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    private CarType carType;
    private Long customerId;

    @Schema(
            description = "Car rental date and time",
            example = "2025-10-20T18:00"
    )
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime rentalDate;

    @Schema(
            description = "Car rental time in days",
            example = "3"
    )
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

    public LocalDateTime getRentalDate() {
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
        private LocalDateTime rentalDate;
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

        public Builder rentalDate(LocalDateTime rentalDate) {
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

