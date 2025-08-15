package com.app.task_api.service;

import com.app.task_api.dto.request.TaskDto;
import com.app.task_api.dto.request.TaskListDto;
import com.app.task_api.dto.request.TaskUpdateDto;
import com.app.task_api.entity.Task;

import java.util.List;

public interface ITaskService {
    List<TaskListDto> findByUserId(int userid);
    Task updateStatus(Long id, TaskUpdateDto taskDto);
    Task save(TaskDto taskDto);
    Task updateFilename(Long id, String filenameOriginal, String pathFile);
    Task findById(Long id);
}
