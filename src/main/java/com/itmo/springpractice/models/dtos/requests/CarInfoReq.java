package com.itmo.springpractice.models.dtos.requests;

import com.itmo.springpractice.models.enums.CarType;
import com.itmo.springpractice.models.enums.Color;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Марка автомобиля")
    String brand;

    @Schema(description = "Модель автомобиля")
    String model;

    @Schema(description = "Тип автомобиля")
    CarType type;

    @Schema(description = "Цвет автомобиля")
    Color color;

    @Schema(description = "Год производства автомобиля")
    Integer year;

    @Schema(description = "Стоимость автомобиля")
    Long price;

    @Schema(description = "Автомобиль новый или с пробегом")
    Boolean isNew;
}
