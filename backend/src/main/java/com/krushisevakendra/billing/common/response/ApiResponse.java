package com.krushisevakendra.billing.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private  int status;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponse<T> success(String message, T data) {
        return success(message, HttpStatus.OK.value(), data);
    }

    public static <T> ApiResponse<T> success(String message, int status, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .status(status)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, int status){

        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .status(status)
                .build();
    }


}
