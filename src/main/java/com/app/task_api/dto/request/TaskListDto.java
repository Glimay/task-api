package com.app.task_api.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskListDto {

    private Long id;
    private String description;
    private String status;
    private int userid;
    private String filename;
    private String filepath;
}
