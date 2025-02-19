package com.itmo.springpractice.controllers;

import com.itmo.springpractice.models.dtos.requests.CarInfoReq;
import com.itmo.springpractice.models.dtos.responses.CarInfoResp;
import com.itmo.springpractice.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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

    /*@GetMapping("/all")
    public List<CarInfoResp> getAllCars() {
        return carService.getAllCars();
    }*/

    @GetMapping("/all")
    public Page<CarInfoResp> getAllCars(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(defaultValue = "price") String sortParam,
                                        @RequestParam(defaultValue = "DESC") Sort.Direction sortDirect,
                                        @RequestParam(required = false) String filter) {
        return carService.getAllCarsWithPagination(page, pageSize, sortParam, sortDirect, filter);
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

    @PutMapping("/{carId}/{driverId}")
    public CarInfoResp linkCarToDriver(@PathVariable Long carId, @PathVariable(name = "driverId") Long userId) {
        return carService.linkCarToDriver(carId, userId);
    }

    @GetMapping("/all/{driverId}")
    public List<CarInfoResp> getAllCarsByDriverId(@PathVariable(name = "driverId") Long userId) {
        return carService.getAllCarsByDriverId(userId);
    }
}
