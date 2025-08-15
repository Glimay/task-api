package com.app.task_api.controller;

import com.app.task_api.dto.request.AuthDto;
import com.app.task_api.dto.request.UserDto;
import com.app.task_api.dto.response.AuthResponseDto;
import com.app.task_api.dto.response.SuccessResponse;
import com.app.task_api.dto.response.UserResponseDto;
import com.app.task_api.dto.response.exceptions.InternalServerErrorException;
import com.app.task_api.entity.User;
import com.app.task_api.service.IUserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@Valid @RequestBody AuthDto authDto, HttpSession session) {
        AuthResponseDto authResponseDto = userService.getUserByUsernameAndPassword(authDto);
        session.setAttribute("CSRF_TOKEN", authResponseDto.getToken());
        SuccessResponse successResponse = new SuccessResponse(HttpStatus.ACCEPTED.value(),"Inicio exitoso",authResponseDto);
        return ResponseEntity.accepted().body(successResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {

        UserResponseDto user = userService.register(userDto);
        SuccessResponse successResponse = new SuccessResponse(HttpStatus.CREATED.value(),"Usuario creado exitosamente",user);
        return ResponseEntity.ok(successResponse);
    }
}
