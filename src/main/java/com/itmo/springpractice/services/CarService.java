package com.itmo.springpractice.services;

import com.itmo.springpractice.models.dtos.requests.CarInfoReq;
import com.itmo.springpractice.models.dtos.responses.CarInfoResp;

import java.util.List;

public interface CarService {
    CarInfoResp getCar(Long id);
    List<CarInfoResp> getAllCars();

    CarInfoResp addCar(CarInfoReq carInfoReq);
    CarInfoResp updateCar(Long id, CarInfoReq carInfoReq);
    void deleteCar(Long id);
}
