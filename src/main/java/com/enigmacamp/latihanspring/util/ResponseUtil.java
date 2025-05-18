package com.enigmacamp.latihanspring.util;

import com.enigmacamp.latihanspring.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T>ResponseEntity<CommonResponse<T>> createResponse(HttpStatus status, String message, T data) {
        CommonResponse<T> response = new CommonResponse<>();
        response.setStatus(status.value());
        response.setMessage(message);
        response.setData(data);

        return ResponseEntity.status(status.value()).body(response);
    }
}
