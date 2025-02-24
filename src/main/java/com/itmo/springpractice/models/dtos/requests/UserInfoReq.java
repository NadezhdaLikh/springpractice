package com.itmo.springpractice.models.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itmo.springpractice.models.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@FieldNameConstants
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoReq {
    @Schema(description = "Имя пользователя")
    String firstName;

    @Schema(description = "Отчество пользователя")
    String middleName;

    @Schema(description = "Фамилия пользователя")
    String lastName;

    @Schema(description = "Пол пользователя")
    Gender gender;

    @Schema(description = "Возраст пользователя")
    @NotNull
    Integer age;

    @Schema(description = "Адрес электронной почты пользователя")
    @NotEmpty(message = "Email must not be empty!")
    String email;

    @Schema(description = "Пароль пользователя")
    String password;
}
