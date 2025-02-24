package com.itmo.springpractice.models.database.repositories;

import com.itmo.springpractice.models.database.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID>
    // Optional<User> findById(Long id); // Method is redundant because it's already provided by JpaRepository

    @Query("select u from User u where u.firstName like %:filter% or u.lastName like %:filter%")
    Page<User> findAllFiltered(Pageable pageRequest, @Param("filter") String filter);

    Optional<User> findByEmail(String email);
}
