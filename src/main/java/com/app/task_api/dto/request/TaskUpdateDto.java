package com.app.task_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateDto {
    @NotBlank(message = "El campo estado es obligatorio")
    @Pattern(regexp = "Pendiente|Proceso|Terminado", message = "El la tarea debe tener un estado de PENDIENTE, PROCESO o TERMINADO")
    private String status;
}
