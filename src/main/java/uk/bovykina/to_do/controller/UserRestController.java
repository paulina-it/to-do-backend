package uk.bovykina.to_do.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.bovykina.to_do.dto.UserCreateDto;
import uk.bovykina.to_do.dto.UserDto;
import uk.bovykina.to_do.dto.UserUpdateDto;
import uk.bovykina.to_do.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserServiceImpl userService;

//    @PostMapping("/create")
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserDto createUser(@RequestBody @Valid UserCreateDto userCreateDto) {
//        return userService.createUser(userCreateDto);
//    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }


    @PutMapping("/update")
    public UserDto updateUser(@RequestBody UserUpdateDto updateUserDto) {
        return userService.updateUser(updateUserDto);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
