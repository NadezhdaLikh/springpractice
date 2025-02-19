package com.itmo.springpractice.models.database.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.itmo.springpractice.models.enums.CarStatus;
import com.itmo.springpractice.models.enums.CarType;
import com.itmo.springpractice.models.enums.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "brand")
    String brand;

    @Column(name = "model")
    String model;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    CarType type;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    Color color;

    @Column(name = "year")
    Integer year;

    @Column(name = "price")
    Long price;

    @Column(name = "is_new")
    Boolean isNew;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    CarStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @JsonBackReference
    User user;
}
