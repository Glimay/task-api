package com.app.task_api.service;

import com.app.task_api.Utils.CsrfUtil;
import com.app.task_api.dto.request.AuthDto;
import com.app.task_api.dto.request.UserDto;
import com.app.task_api.dto.request.UserListDto;
import com.app.task_api.dto.response.AuthResponseDto;
import com.app.task_api.dto.response.UserResponseDto;
import com.app.task_api.dto.response.exceptions.UnauthorizedException;
import com.app.task_api.entity.User;
import com.app.task_api.dto.response.exceptions.ConflictException;
import com.app.task_api.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository repository;
    private SecurityService securityService;

    @Override
    public UserResponseDto register(UserDto userDto) {
        boolean isPresent = repository.findByUsername(userDto.getUsername()).isPresent();
        if (isPresent) {
            throw new ConflictException("El usuario ya existe");
        }

        try {

            String password = null;

            password = SecurityService.encrypt(userDto.getPassword());

            User user = new User();
            user.setAge(userDto.getAge());
            user.setGender(userDto.getGender());
            user.setPhone(userDto.getPhone());
            user.setUsername(userDto.getUsername());
            user.setPassword(password);
            repository.save(user);
            return new UserResponseDto(
                    user.getUsername(),
                    user.getPhone(),
                    user.getAge(),
                    user.getGender()
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public AuthResponseDto getUserByUsernameAndPassword(AuthDto authDto) {

        Optional<User> userOptional = repository.findByUsername(authDto.getUsername());
        if (userOptional.isPresent()) {
            String password = null;
            try {
                password = SecurityService.decrypt(userOptional.get().getPassword());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

            if (password.equals(authDto.getPassword())){
                String csrfToken = CsrfUtil.generateCsrfToken();

                return new AuthResponseDto(csrfToken,
                        authDto.getUsername(),
                        userOptional.get().getId());
            }

        }
        throw new UnauthorizedException("Usuario o Password incorrectos");
    }

    @Override
    public UserListDto findById(Long id) {
        User user = repository.findById(id).get();
        return new UserListDto(user.getUsername(), user.getPhone(),
                user.getAge(),user.getGender());

    }


}
