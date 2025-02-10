package com.itmo.springpractice.services.impls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmo.springpractice.models.dtos.requests.UserInfoReq;
import com.itmo.springpractice.models.dtos.responses.UserInfoResp;
import com.itmo.springpractice.models.enums.Gender;
import com.itmo.springpractice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ObjectMapper objectMapper;

    @Override
    public UserInfoResp getUser(Long id) {
        if (id != 1L) {
            log.error("From getUser(): User with id {} is not found:(", id);
            return null;
        }

        return UserInfoResp.builder()
                .id(1L)
                .firstName("Ivan")
                .middleName("Ivanovich")
                .lastName("Ivanov")
                .gender(Gender.MALE)
                .age(34)
                .email("IvanovIvan@gmail.com")
                .password("00112233")
                .build();
    }

    @Override
    public List<UserInfoResp> getAllUsers() {
        return List.of(UserInfoResp.builder()
                .id(1L)
                .firstName("Ivan")
                .middleName("Ivanovich")
                .lastName("Ivanov")
                .gender(Gender.MALE)
                .age(34)
                .email("IvanovIvan@gmail.com")
                .password("00112233")
                .build());
    }

    @Override
    public UserInfoResp getUserByLastNameAndEmail(String lastName, String email) {
        return getUser(1L);
    }

    @Override
    public UserInfoResp addUser(UserInfoReq userInfoReq) {
        UserInfoResp userInfoResp = objectMapper.convertValue(userInfoReq, UserInfoResp.class);
        userInfoResp.setId(1L);
        return userInfoResp;
    }

    @Override
    public UserInfoResp updateUser(Long id, UserInfoReq userInfoReq) {
        if (id != 1L) {
            log.error("From updateUser(): User with id {} is not found:(", id);
            return null;
        }

        return UserInfoResp.builder()
                .id(1L)
                .firstName("Petr")
                .middleName("Petrovich")
                .lastName("Petrov")
                .gender(Gender.MALE)
                .age(26)
                .email("PetrovPetr@gmail.com")
                .password("00112244")
                .build();
    }

    @Override
    public void deleteUser(Long id) {
        if (id != 1L) {
            log.error("From deleteUser(): User with id {} is not found:(", id);
        } else log.info("User with id {} was successfully deleted!", id);
    }
}
