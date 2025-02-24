package com.itmo.springpractice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {
    private String message;

    /*public ErrorMessage(String message) {
        this.message = message;
    }*/
}
