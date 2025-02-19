package com.itmo.springpractice.models.database.repositories;

import com.itmo.springpractice.models.database.entities.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    // Optional<Car> findById(Long id); // Method is redundant because it's already provided by JpaRepository

    @Query(value = "select * from cars c where c.driver_id = :driverId", nativeQuery = true)
    List<Car> getAllCarsByUserId(@Param("driverId") Long userId);

    @Query("select c from Car c where c.brand = :filter")
    Page<Car> findAllFiltered(Pageable pageRequest, @Param("filter") String filter);
}
