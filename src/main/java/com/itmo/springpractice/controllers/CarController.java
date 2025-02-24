package com.itmo.springpractice.controllers;

import com.itmo.springpractice.models.dtos.requests.CarInfoReq;
import com.itmo.springpractice.models.dtos.responses.CarInfoResp;
import com.itmo.springpractice.services.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@Tag(name = "Автомобили")
public class CarController {
    private final CarService carService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить автомобиль по его ID")
    public CarInfoResp getCar(@PathVariable Long id) {
        return carService.getCar(id);
    }

    /*@GetMapping("/all")
    public List<CarInfoResp> getAllCars() {
        return carService.getAllCars();
    }*/

    @GetMapping("/all")
    @Operation(summary = "Получить список пользователей (с пагинацией)")
    public Page<CarInfoResp> getAllCars(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(defaultValue = "price") String sortParam,
                                        @RequestParam(defaultValue = "DESC") Sort.Direction sortDirect,
                                        @RequestParam(required = false) String filter) {
        return carService.getAllCarsWithPagination(page, pageSize, sortParam, sortDirect, filter);
    }

    @PostMapping
    @Operation(summary = "Добавить автомобиль")
    public CarInfoResp addCar(@RequestBody CarInfoReq carInfoReq) {
        return carService.addCar(carInfoReq);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить автомобиль по его ID")
    public CarInfoResp updateCar(@PathVariable Long id, @RequestBody CarInfoReq carInfoReq) {
        return carService.updateCar(id, carInfoReq);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить автомобиль по его ID")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    @PutMapping("/{carId}/{driverId}")
    @Operation(summary = "Привязать автомобиль к водителю через ID")
    public CarInfoResp linkCarToDriver(@PathVariable Long carId, @PathVariable(name = "driverId") Long userId) {
        return carService.linkCarToDriver(carId, userId);
    }

    @GetMapping("/all/{driverId}")
    @Operation(summary = "Получить список автомобилей конкретного пользователя по его ID")
    public List<CarInfoResp> getAllCarsByDriverId(@PathVariable(name = "driverId") Long userId) {
        return carService.getAllCarsByDriverId(userId);
    }
}
