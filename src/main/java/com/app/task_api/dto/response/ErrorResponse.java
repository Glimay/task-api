package com.app.task_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private int code;
    private String message;
    private Map<String, String> errors;

}
