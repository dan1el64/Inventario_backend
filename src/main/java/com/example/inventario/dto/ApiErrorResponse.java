package com.example.inventario.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErrorResponse {
    private boolean success;
    private String message;
    private HttpStatus status;
    private int statusCode;
    private LocalDateTime timestamp;
}
