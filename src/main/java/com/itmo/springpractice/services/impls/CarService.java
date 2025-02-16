package com.itmo.springpractice.services.impls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmo.springpractice.models.database.entities.Car;
import com.itmo.springpractice.models.database.repositories.CarRepository;
import com.itmo.springpractice.models.dtos.requests.CarInfoReq;
import com.itmo.springpractice.models.dtos.responses.CarInfoResp;
import com.itmo.springpractice.models.enums.CarStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {
    private final ObjectMapper objectMapper;
    private final CarRepository carRepository;

    private Car getCarFromDB(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()) {
            log.error("From getCarFromDB(): sorry, car with id {} is not found:(", id);
        }
        return optionalCar.orElse(new Car());
    }

    public CarInfoResp getCar(Long id) {
        Car car = getCarFromDB(id);
        return objectMapper.convertValue(car, CarInfoResp.class);
    }

    public List<CarInfoResp> getAllCars() {
        return carRepository.findAll().stream()
                .map(car -> objectMapper.convertValue(car, CarInfoResp.class))
                .collect(Collectors.toList());
    }

    public CarInfoResp addCar(CarInfoReq carInfoReq) {
        Car car = objectMapper.convertValue(carInfoReq, Car.class);
        car.setStatus(CarStatus.UNBINDED);

        return objectMapper.convertValue(carRepository.save(car), CarInfoResp.class);
    }

    public CarInfoResp updateCar(Long id, CarInfoReq carInfoReq) {
        Car car = getCarFromDB(id);
        if (car.getId() == null) {
            return objectMapper.convertValue(null, CarInfoResp.class);
        }

        car.setBrand(carInfoReq.getBrand() == null ? car.getBrand() : carInfoReq.getBrand());
        car.setModel(carInfoReq.getModel() == null ? car.getModel() : carInfoReq.getModel());
        car.setType(carInfoReq.getType() == null ? car.getType() : carInfoReq.getType());
        car.setColor(carInfoReq.getColor() == null ? car.getColor() : carInfoReq.getColor());
        car.setYear(carInfoReq.getYear() == null ? car.getYear() : carInfoReq.getYear());
        car.setPrice(carInfoReq.getPrice() == null ? car.getPrice() : carInfoReq.getPrice());
        car.setIsNew(carInfoReq.getIsNew() == null ? car.getIsNew() : carInfoReq.getIsNew());

        return objectMapper.convertValue(carRepository.save(car), CarInfoResp.class);
    }

    public void deleteCar(Long id) {
        Car car = getCarFromDB(id);
        if (car.getId() == null) {
            return;
        }
        car.setStatus(CarStatus.DELETED);
        carRepository.save(car);
    }
}
