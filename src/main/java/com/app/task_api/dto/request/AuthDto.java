package com.app.task_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthDto {
    @NotBlank(message = "El campo usuario es obligatorio")
    private String username;
    @NotBlank(message = "El campo password es obligatorio")
    private String password;
}
