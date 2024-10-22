package uk.bovykina.to_do.service;

import uk.bovykina.to_do.dto.UserCreateDto;
import uk.bovykina.to_do.dto.UserDto;
import uk.bovykina.to_do.dto.UserUpdateDto;
import uk.bovykina.to_do.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto getUserById(Long id);
    UserDto getUserByUsername(String username);

    UserDto createUser(UserCreateDto userCreateDto);

    UserDto updateUser(UserUpdateDto userUpdateDto);

    void deleteUser(Long id);

    List<UserDto> getAllUsers();

    UserDto login(String username, String password);
}
