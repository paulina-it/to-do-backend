package uk.bovykina.to_do.service;

import org.springframework.security.core.userdetails.UserDetails;
import uk.bovykina.to_do.dto.LoginResponse;
import uk.bovykina.to_do.dto.UserCreateDto;
import uk.bovykina.to_do.dto.UserDto;
import uk.bovykina.to_do.dto.UserUpdateDto;

import java.util.List;

public interface UserService {

    UserDto getUserById(Long id);
    UserDto getUserByUsername(String username);

    UserDto createUser(UserCreateDto userCreateDto);

    UserDto updateUser(UserUpdateDto userUpdateDto);

    void deleteUser(Long id);

    List<UserDto> getAllUsers();

    LoginResponse login(String username, String password);

    UserDetails loadUserByUsername(String username);
}
