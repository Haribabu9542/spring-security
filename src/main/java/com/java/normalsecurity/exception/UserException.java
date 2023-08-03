package com.java.normalsecurity.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserException extends RuntimeException {
    public UserException(String message) {
    super(message);
    }
}
