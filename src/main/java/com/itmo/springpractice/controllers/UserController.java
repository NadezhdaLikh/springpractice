package com.itmo.springpractice.controllers;

import com.itmo.springpractice.models.dtos.requests.UserInfoReq;
import com.itmo.springpractice.models.dtos.responses.UserInfoResp;
import com.itmo.springpractice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по его ID")
    public UserInfoResp getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    /*@GetMapping("/all")
    public List<UserInfoResp> getAllUsers() {
        return userService.getAllUsers();
    }*/

    @GetMapping("/all")
    @Operation(summary = "Получить список пользователей (с пагинацией)")
    public Page<UserInfoResp> getAllUsers(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam(defaultValue = "lastName") String sortParam,
                                          @RequestParam(defaultValue = "ASC") Sort.Direction sortDirect,
                                          @RequestParam(required = false) String filter) {
        return userService.getAllUsersWithPagination(page, pageSize, sortParam, sortDirect, filter);
    }

    @PostMapping
    @Operation(summary = "Добавить пользователя")
    public UserInfoResp addUser(@RequestBody @Valid UserInfoReq userInfoReq) {
        return userService.addUser(userInfoReq);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя по его ID")
    public UserInfoResp updateUser(@PathVariable Long id, @RequestBody UserInfoReq userInfoReq) {
        return userService.updateUser(id, userInfoReq);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по его ID")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
