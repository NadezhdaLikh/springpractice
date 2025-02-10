package com.itmo.springpractice.models.dtos.requests;

import com.itmo.springpractice.models.enums.CarType;
import com.itmo.springpractice.models.enums.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarInfoReq {
    String brand;
    String model;
    CarType type;
    Color color;
    Integer year;
    Long price;
    Boolean isNew;
}
