package com.itmo.springpractice.models.database.repositories;

import com.itmo.springpractice.models.database.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findById(Long id);
}
