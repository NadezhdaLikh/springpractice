package com.itmo.springpractice.services.impls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmo.springpractice.models.dtos.requests.CarInfoReq;
import com.itmo.springpractice.models.dtos.responses.CarInfoResp;
import com.itmo.springpractice.models.enums.CarType;
import com.itmo.springpractice.models.enums.Color;
import com.itmo.springpractice.services.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final ObjectMapper objectMapper;

    @Override
    public CarInfoResp getCar(Long id) {
        if (id != 1L) {
            log.error("From getCar(): Car with id {} is not found:(", id);
            return null;
        }

        return CarInfoResp.builder()
                .id(1L)
                .brand("Toyota")
                .model("Camry")
                .type(CarType.SEDAN)
                .color(Color.BLACK)
                .year(2018)
                .price(900000L)
                .isNew(false)
                .build();
    }

    @Override
    public List<CarInfoResp> getAllCars() {
        return List.of(CarInfoResp.builder()
                .id(1L)
                .brand("Toyota")
                .model("Camry")
                .type(CarType.SEDAN)
                .color(Color.BLACK)
                .year(2018)
                .price(900000L)
                .isNew(false)
                .build());
    }

    @Override
    public CarInfoResp addCar(CarInfoReq carInfoReq) {
        CarInfoResp carInfoResp = objectMapper.convertValue(carInfoReq, CarInfoResp.class);
        carInfoResp.setId(1L);
        return carInfoResp;
    }

    @Override
    public CarInfoResp updateCar(Long id, CarInfoReq carInfoReq) {
        if (id != 1L) {
            log.error("From updateCar(): Car with id {} is not found:(", id);
            return null;
        }

        return CarInfoResp.builder()
                .id(1L)
                .brand("Toyota")
                .model("Camry")
                .type(CarType.SEDAN)
                .color(Color.RED)
                .year(2020)
                .price(1220000L)
                .isNew(false)
                .build();
    }

    @Override
    public void deleteCar(Long id) {
        if (id != 1L) {
            log.error("From deleteCar(): Car with id {} is not found:(", id);
        } else log.info("User with id {} was successfully deleted!", id);
    }
}
