package com.app.task_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "El campo username es obligatorio")
    private String username;
    @NotBlank(message = "El campo teléfono es obligatorio")
    @Pattern(regexp = "\\d{4}-\\d{4}", message = "El teléfono debe tener el formato 0000-0000")
    private String phone;
    @NotBlank(message = "El campo edad es obligatorio")
    private String age;
    @NotBlank(message = "El campo sexo es obligatorio")
    @Pattern(regexp = "Masculino|Femenino", message = "El campo sexo debe ser Masculino o Femenino")
    private String gender;
    @NotBlank(message = "El campo contraseña es obligatorio")
    @Size(min = 8, max = 12, message = "La contraseña debe tener entre 8 y 12 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,12}$",
            message = "La contraseña debe contener letras mayúsculas, minúsculas y números, sin caracteres especiales"
    )
    private String password;

}
