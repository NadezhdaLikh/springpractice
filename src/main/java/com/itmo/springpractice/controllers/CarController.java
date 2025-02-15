package com.itmo.springpractice.controllers;

import com.itmo.springpractice.models.dtos.requests.CarInfoReq;
import com.itmo.springpractice.models.dtos.responses.CarInfoResp;
import com.itmo.springpractice.services.impls.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/{id}")
    public CarInfoResp getCar(@PathVariable Long id) {
        return carService.getCar(id);
    }

    @GetMapping("/all")
    public List<CarInfoResp> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping
    public CarInfoResp addCar(@RequestBody CarInfoReq carInfoReq) {
        return carService.addCar(carInfoReq);
    }

    @PutMapping("/{id}")
    public CarInfoResp updateCar(@PathVariable Long id, @RequestBody CarInfoReq carInfoReq) {
        return carService.updateCar(id, carInfoReq);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
}
