package com.michalkrol.carrental.service;

import com.michalkrol.carrental.entity.Rental;
import com.michalkrol.carrental.repository.RentalRepository;
import com.michalkrol.carrental.validation.RentalValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RentalValidator rentalValidator;

    public RentalService(RentalRepository rentalRepository, RentalValidator rentalValidator) {
        this.rentalRepository = rentalRepository;
        this.rentalValidator = rentalValidator;
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> findById(Long id) {
        return rentalRepository.findById(id);
    }

    public Rental create(Rental rental) {
        rentalValidator.validateCreateRental(rental);
        return rentalRepository.save(rental);
    }

    public Rental update(Rental updatedRental, Rental originalRental) {
        Rental rentalForUpdate = prepareRentalForUpdate(updatedRental, originalRental);
        rentalValidator.validateUpdateRental(rentalForUpdate);
        return rentalRepository.save(rentalForUpdate);
    }

    private static Rental prepareRentalForUpdate(Rental updatedRental, Rental originalRental) {
        return Rental.builder()
                .rentalDate(Optional.ofNullable(updatedRental.getRentalDate()).orElse(originalRental.getRentalDate()))
                .rentalDaysNumber(Optional.ofNullable(updatedRental.getRentalDaysNumber()).orElse(originalRental.getRentalDaysNumber()))
                .carType(Optional.ofNullable(updatedRental.getCarType()).orElse(originalRental.getCarType()))
                .customerId(Optional.ofNullable(updatedRental.getCustomerId()).orElse(originalRental.getCustomerId()))
                .build();
    }

    public void deleteById(Long id) {
        rentalRepository.deleteById(id);
    }
}

