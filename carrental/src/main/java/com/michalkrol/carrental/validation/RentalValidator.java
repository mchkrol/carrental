package com.michalkrol.carrental.validation;

import com.michalkrol.carrental.config.RentalProperties;
import com.michalkrol.carrental.entity.Rental;
import com.michalkrol.carrental.repository.CustomerRepository;
import com.michalkrol.carrental.repository.RentalRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RentalValidator {

    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final RentalProperties rentalProperties;

    public RentalValidator(RentalRepository rentalRepository, CustomerRepository customerRepository,
                           RentalProperties rentalProperties) {
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.rentalProperties = rentalProperties;
    }

    public void validateCreateRental(Rental rental) {
        validateRentalRequest(rental);
        Integer carsNumber = rentalProperties.findCarsNumber(rental.getCarType());
        validateCustomerId(rental);
        if (findOverlappingRentals(rental).size() >= carsNumber) {
            throw new RentalValidationException("All " + rental.getCarType() + "s are already reserved at that time.");
        }
    }

    public void validateUpdateRental(Rental rental) {
        validateRentalRequest(rental);
        Integer carsNumber = rentalProperties.findCarsNumber(rental.getCarType());
        validateCustomerId(rental);
        if (findOverlappingRentals(rental).stream()
                .filter(existingRental -> !existingRental.getId().equals(rental.getId()))
                .count() >= Long.valueOf(carsNumber)) {
            throw new RentalValidationException("All " + rental.getCarType() + "s are already reserved in that time.");
        }
    }

    private void validateCustomerId(Rental rental) {
        if (customerRepository.findById(rental.getCustomerId()).isEmpty()) {
            throw new RentalValidationException("A Customer with this ID does not exist.");
        }
    }

    private void validateRentalRequest(Rental rental) {
        if (rental == null) {
            throw new RentalValidationException("Rental cannot be null.");
        }

        if (rental.getCarType() == null || rental.getRentalDate() == null || rental.getRentalDaysNumber() == null) {
            throw new RentalValidationException("Rental Car ID, Rental Date and the number of days must be set.");
        }
    }

    private List<Rental> findOverlappingRentals(Rental rental) {
        return rentalRepository.findByCarType(rental.getCarType()).stream()
                .filter(rentalFromBase -> overlap(rental, rentalFromBase))
                .toList();
    }

    private boolean overlap(Rental rentalToCompare, Rental rentalFromBase) {
        LocalDateTime rentalToCompareStart = rentalToCompare.getRentalDate();
        LocalDateTime rentalToCompareEnd = rentalToCompare.getRentalDate().plusDays(rentalToCompare.getRentalDaysNumber());
        LocalDateTime rentalFromBaseStart = rentalFromBase.getRentalDate();
        LocalDateTime rentalFromBaseEnd = rentalFromBase.getRentalDate().plusDays(rentalFromBase.getRentalDaysNumber());
        return (rentalToCompareStart.isBefore(rentalFromBaseEnd) && rentalToCompareEnd.isAfter(rentalFromBaseStart));
    }
}
