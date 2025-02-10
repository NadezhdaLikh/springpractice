package com.itmo.springpractice.models.dtos.responses;

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
public class CarInfoResp extends CarInfoReq {
    Long id;
}