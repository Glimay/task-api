package com.app.task_api.service;

import com.app.task_api.dto.request.TaskDto;
import com.app.task_api.dto.request.TaskListDto;
import com.app.task_api.dto.request.TaskUpdateDto;
import com.app.task_api.dto.response.exceptions.ConflictException;
import com.app.task_api.dto.response.exceptions.InternalServerErrorException;
import com.app.task_api.entity.Task;
import com.app.task_api.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private ITaskRepository repository;

    @Override
    public List<TaskListDto> findByUserId(int userid) {
        List<Task> list = repository.findByUserId(userid);
        return list.stream().map(
                task -> new TaskListDto(task.getId(),task.getDescription(),task.getStatus(),task.getUserId(),task.getFilename(), task.getFilepath())
        ).collect(Collectors.toList());
    }

    @Override
    public Task updateStatus(Long idTask, TaskUpdateDto taskDto) {
        Optional<Task> taskOptional = repository.findById(idTask);

        if (taskOptional.isPresent()){
            Task task = taskOptional.get();
            task.setStatus(taskDto.getStatus());
            return repository.save(task);
        }

        throw new RuntimeException("La tarea no fue actualizada");
    }



    @Override
    public Task save(TaskDto taskDto) {
        Task task = new Task();
        task.setDescription(taskDto.getDescription());
        task.setUserId(taskDto.getUserid());
        task.setStatus(taskDto.getStatus());

        return repository.save(task);
    }

    @Override
    public Task updateFilename(Long id, String filenameOriginal, String pathFile) {
        Optional<Task> taskOptional = repository.findById(id);
        if (taskOptional.isPresent()){
            Task task = taskOptional.get();
            task.setFilename(filenameOriginal);
            task.setFilepath(pathFile);
            return repository.save(task);
        }

        throw new ConflictException("La tarea no existe");
    }

    @Override
    public Task findById(Long id) {
        return repository.findById(id).get();
    }
}
