package com.itmo.springpractice.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itmo.springpractice.models.dtos.requests.CarInfoReq;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarInfoResp extends CarInfoReq {
    Long id;
    UserInfoResp user;
}