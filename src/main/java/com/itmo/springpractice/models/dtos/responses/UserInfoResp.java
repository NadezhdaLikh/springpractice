package com.itmo.springpractice.models.dtos.responses;

import com.itmo.springpractice.models.dtos.requests.UserInfoReq;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoResp extends UserInfoReq {
    Long id;
}
