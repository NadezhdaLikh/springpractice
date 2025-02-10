package com.itmo.springpractice.models.dtos.requests;

import com.itmo.springpractice.models.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoReq {
    String firstName;
    String middleName;
    String lastName;
    Gender gender;
    Integer age;
    String email;
    String password;
}
