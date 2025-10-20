package com.michalkrol.carrental.repository;

import com.michalkrol.carrental.entity.CarType;
import com.michalkrol.carrental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByCarType(CarType carType);
}
