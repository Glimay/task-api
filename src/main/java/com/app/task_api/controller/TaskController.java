package com.app.task_api.controller;

import com.app.task_api.dto.request.TaskDto;
import com.app.task_api.dto.request.TaskListDto;
import com.app.task_api.dto.request.TaskUpdateDto;
import com.app.task_api.dto.response.SuccessResponse;
import com.app.task_api.dto.response.TaskResponseDto;
import com.app.task_api.dto.response.exceptions.BadRequestException;
import com.app.task_api.dto.response.exceptions.InternalServerErrorException;
import com.app.task_api.entity.Task;
import com.app.task_api.service.ITaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @GetMapping("/{userid}")
    public ResponseEntity<?> getAllTasksByUserId(@PathVariable("userid") Integer userId){
        List<TaskListDto> list = taskService.findByUserId(userId);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK.value(), "Tareas halladas",list);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody TaskDto taskDto) {
        Task task = taskService.save(taskDto);
        SuccessResponse response = new SuccessResponse(HttpStatus.CREATED.value(), "Tarea creada con éxito",task);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long taskId,@Valid @RequestBody TaskUpdateDto taskDto) {
        Task task = taskService.updateStatus(taskId, taskDto);
        SuccessResponse response = new SuccessResponse(HttpStatus.CREATED.value(), "Tarea actualizada con éxito",task);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadFile(@PathVariable("id") Long taskId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()){
            throw new BadRequestException("El archivo esta vacío");
        }

        try {
            Files.createDirectories(Paths.get(System.getProperty("user.home").concat("/task_files/")));
            String filenameOriginal = file.getOriginalFilename();
            String extension = filenameOriginal.substring(filenameOriginal.lastIndexOf("."));
            String filenameUUID = UUID.randomUUID().toString() + extension;

            Path filePath = Paths.get(System.getProperty("user.home").concat("/task_files/"), filenameUUID);
            file.transferTo(filePath.toFile());
            Task task = taskService.updateFilename(taskId, filenameOriginal, "task_files/".concat(filenameUUID));

            TaskResponseDto taskResponseDto = new TaskResponseDto(task.getDescription(),
                        task.getFilename(),
                        "/task_files/".concat(filenameUUID)
                    );

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new SuccessResponse(
                            HttpStatus.CREATED.value(),
                    "Imagen Cargada Correctamente",taskResponseDto));

        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping("image/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) throws IOException {
        Task task = taskService.findById(id);
        Path imagePath = Paths.get(System.getProperty("user.home")).resolve(task.getFilepath()).normalize();
        Resource imageResource = new UrlResource(imagePath.toUri());

        if (!imageResource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(imagePath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(imageResource);
    }
}
