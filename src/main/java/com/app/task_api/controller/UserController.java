package com.app.task_api.controller;

import com.app.task_api.dto.request.UserListDto;
import com.app.task_api.dto.response.SuccessResponse;
import com.app.task_api.service.IUserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAll(@PathVariable("id") Long id) {
        UserListDto user = userService.findById(id);
        SuccessResponse successResponse = new SuccessResponse(HttpStatus.OK.value(), "Usuario encontrado", user);
        return ResponseEntity.ok(successResponse);
    }
}
