package com.itmo.springpractice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmo.springpractice.models.database.entities.Car;
import com.itmo.springpractice.models.database.entities.User;
import com.itmo.springpractice.models.database.repositories.CarRepository;
import com.itmo.springpractice.models.dtos.requests.CarInfoReq;
import com.itmo.springpractice.models.dtos.responses.CarInfoResp;
import com.itmo.springpractice.models.dtos.responses.UserInfoResp;
import com.itmo.springpractice.models.enums.CarStatus;
import com.itmo.springpractice.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {
    private final ObjectMapper objectMapper;
    private final CarRepository carRepository;
    private final UserService userService;

    private Car getCarFromDB(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()) {
            log.error("From getCarFromDB(): sorry, car with id {} is not found.", id);
        }
        return optionalCar.orElse(new Car());
    }

    public CarInfoResp getCar(Long id) {
        Car car = getCarFromDB(id);

        return objectMapper.convertValue(car, CarInfoResp.class);
    }

    /*public List<CarInfoResp> getAllCars() {
        return carRepository.findAll().stream()
                .map(car -> objectMapper.convertValue(car, CarInfoResp.class))
                .collect(Collectors.toList());
    }*/

    public Page<CarInfoResp> getAllCarsWithPagination(Integer page, Integer pageSize, String sortParam, Sort.Direction sortDirect, String filter) {
        Page<Car> cars;

        Pageable pageRequest = PaginationUtils.makePageRequest(page, pageSize, sortParam, sortDirect);

        if (StringUtils.hasText(filter)) {
            cars = carRepository.findAllFiltered(pageRequest, filter);
        } else cars = carRepository.findAll(pageRequest);

        List<CarInfoResp> content = cars.getContent().stream()
                .map(c -> objectMapper.convertValue(c, CarInfoResp.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageRequest, cars.getTotalElements());
    }

    public CarInfoResp addCar(CarInfoReq carInfoReq) {
        Car car = objectMapper.convertValue(carInfoReq, Car.class);
        car.setStatus(CarStatus.UNLINKED);

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

    public CarInfoResp linkCarToDriver(Long carId, Long userId) {
        Car car = getCarFromDB(carId);
        User user = userService.getUserFromDB(userId);

        if (car.getId() == null || user.getId() == null) {
            log.error("From linkCarToDriver(): sorry, can't link car with id {} to driver with id {}.", carId, userId);
            return CarInfoResp.builder().build();
        }

        // Check from car's perspective
        if (car.getStatus() == CarStatus.LINKED & car.getUser() != null) {
            log.error("From linkCarToDriver(): sorry, car with id {} is already linked to some other driver with id {}.", carId, userId);
            return CarInfoResp.builder().build();
        }

        // Check from user's perspective
        List<Car> userCars = user.getCars();
        boolean isLinked = userCars.stream().anyMatch(c -> c.getId().equals(carId));

        if (isLinked) {
            log.error("From linkCarToDriver(): car with id {} is already linked to driver with id {}.", carId, userId);

            CarInfoResp carInfoResp = objectMapper.convertValue(car, CarInfoResp.class);
            UserInfoResp userInfoResp = objectMapper.convertValue(user, UserInfoResp.class);
            carInfoResp.setUser(userInfoResp);

            return carInfoResp;
        }

        userCars.add(car);
        User updatedUser = userService.updateUserCars(user);

        car.setUser(updatedUser);
        car.setStatus(CarStatus.LINKED);
        carRepository.save(car);

        CarInfoResp carInfoResp = objectMapper.convertValue(car, CarInfoResp.class);
        UserInfoResp userInfoResp = objectMapper.convertValue(user, UserInfoResp.class);
        carInfoResp.setUser(userInfoResp);

        return carInfoResp;
    }

    public List<CarInfoResp> getAllCarsByDriverId(Long userId) {
        User user = userService.getUserFromDB(userId);
        if (user.getId() == null) {
            log.error("From getCarsByDriverId(): sorry, user with id {} is not found.", userId);
            return List.of();
        }
        return carRepository.getAllCarsByUserId(userId).stream()
                .map(car -> objectMapper.convertValue(car, CarInfoResp.class))
                .collect(Collectors.toList());
    }
}
