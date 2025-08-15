package com.app.task_api.service;

import com.app.task_api.dto.request.AuthDto;
import com.app.task_api.dto.request.UserDto;
import com.app.task_api.dto.request.UserListDto;
import com.app.task_api.dto.response.AuthResponseDto;
import com.app.task_api.dto.response.UserResponseDto;
import com.app.task_api.entity.User;

public interface IUserService {

    UserResponseDto register(UserDto userDto);
    AuthResponseDto getUserByUsernameAndPassword(AuthDto authDto);
    UserListDto findById(Long id);
}
