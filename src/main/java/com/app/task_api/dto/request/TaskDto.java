package com.app.task_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotBlank(message = "El campo descripción es obligatorio")
    private String description;
    @Min(value = 1, message = "El campo usuario debe tener un valor diferente a cero")
    private Integer userid;
    @NotBlank(message = "El campo estado es obligatorio")
    @Pattern(regexp = "Pendiente|Proceso|Terminado", message = "El la tarea debe tener un estado de PENDIENTE, PROCESO o TERMINADO")
    private String status;


}
