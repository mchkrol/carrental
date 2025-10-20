package com.michalkrol.carrental.validation;

import com.michalkrol.carrental.config.RentalProperties;
import com.michalkrol.carrental.entity.CarType;
import com.michalkrol.carrental.entity.Customer;
import com.michalkrol.carrental.entity.Rental;
import com.michalkrol.carrental.repository.CustomerRepository;
import com.michalkrol.carrental.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RentalValidatorTest {

    private RentalRepository rentalRepository;
    private CustomerRepository customerRepository;
    private RentalProperties rentalProperties;
    private RentalValidator rentalValidator;

    @BeforeEach
    void setUp() {
        rentalRepository = Mockito.mock(RentalRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        rentalProperties = Mockito.mock(RentalProperties.class);

        rentalValidator = new RentalValidator(rentalRepository, customerRepository, rentalProperties);

        when(customerRepository.findById(any())).thenReturn(Optional.of(new Customer()));
        when(rentalProperties.findCarsNumber(any())).thenReturn(2); // 2 dostępne samochody każdego typu
    }

    private Rental createRental(Long id, LocalDate startDate, int days, CarType type) {
        return Rental.builder()
                .id(id)
                .customerId(1L)
                .carType(type)
                .rentalDate(startDate)
                .rentalDaysNumber(days)
                .build();
    }

    @Test
    void shouldPassCreateRental_WhenNoOverlap() {
        Rental newRental = createRental(1L, LocalDate.of(2025, 10, 1), 3, CarType.SUV);
        Rental existingRental = createRental(2L, LocalDate.of(2025, 10, 10), 3, CarType.SUV);

        when(rentalRepository.findByCarType(CarType.SUV)).thenReturn(List.of(existingRental));

        assertDoesNotThrow(() -> rentalValidator.validateCreateRental(newRental));
    }

    @Test
    void shouldThrowException_WhenOverlappingRentalsExist() {
        Rental newRental = createRental(1L, LocalDate.of(2025, 10, 1), 5, CarType.SUV);
        Rental existingRentalA = createRental(2L, LocalDate.of(2025, 10, 3), 4, CarType.SUV); // nakłada się
        Rental existingRentalB = createRental(2L, LocalDate.of(2025, 9, 29), 3, CarType.SUV); // nakłada się

        when(rentalRepository.findByCarType(CarType.SUV)).thenReturn(List.of(existingRentalA, existingRentalB));

        RentalValidationException ex = assertThrows(RentalValidationException.class,
                () -> rentalValidator.validateCreateRental(newRental));

        assertTrue(ex.getMessage().contains("reserved"));
    }

    @Test
    void shouldThrowException_WhenCustomerDoesNotExist() {
        Rental rental = createRental(1L, LocalDate.of(2025, 10, 1), 3, CarType.SEDAN);
        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        RentalValidationException ex = assertThrows(RentalValidationException.class,
                () -> rentalValidator.validateCreateRental(rental));

        assertEquals("A Customer with this ID does not exist.", ex.getMessage());
    }

    @Test
    void shouldThrowException_WhenRentalIsNull() {
        RentalValidationException ex = assertThrows(RentalValidationException.class,
                () -> rentalValidator.validateCreateRental(null));

        assertEquals("Rental cannot be null.", ex.getMessage());
    }

    @Test
    void shouldThrowException_WhenRequiredFieldsMissing() {
        Rental rental = Rental.builder()
                .customerId(1L)
                .carType(null)
                .rentalDate(null)
                .rentalDaysNumber(null)
                .build();

        RentalValidationException ex = assertThrows(RentalValidationException.class,
                () -> rentalValidator.validateCreateRental(rental));

        assertTrue(ex.getMessage().contains("must be set"));
    }

    @Test
    void shouldValidateUpdateRental_IgnoringSameId() {
        Rental rental = createRental(1L, LocalDate.of(2025, 10, 1), 3, CarType.VAN);
        Rental overlappingSameId = createRental(1L, LocalDate.of(2025, 10, 2), 3, CarType.VAN);
        Rental existingRentalB = createRental(2L, LocalDate.of(2025, 9, 29), 3, CarType.VAN); // nakłada się

        when(rentalRepository.findByCarType(CarType.VAN)).thenReturn(List.of(overlappingSameId, existingRentalB));

        assertDoesNotThrow(() -> rentalValidator.validateUpdateRental(rental));
    }

    @Test
    void shouldThrowException_WhenUpdateHasTooManyOverlaps() {
        Rental rental = createRental(1L, LocalDate.of(2025, 10, 1), 3, CarType.SEDAN);
        Rental overlapping1 = createRental(2L, LocalDate.of(2025, 10, 2), 3, CarType.SEDAN);
        Rental overlapping2 = createRental(3L, LocalDate.of(2025, 10, 3), 3, CarType.SEDAN);

        when(rentalRepository.findByCarType(CarType.SEDAN)).thenReturn(List.of(overlapping1, overlapping2));
        when(rentalProperties.findCarsNumber(CarType.SEDAN)).thenReturn(2);

        RentalValidationException ex = assertThrows(RentalValidationException.class,
                () -> rentalValidator.validateUpdateRental(rental));

        assertTrue(ex.getMessage().contains("reserved"));
    }
}
