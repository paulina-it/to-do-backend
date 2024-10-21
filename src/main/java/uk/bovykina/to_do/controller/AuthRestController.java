package uk.bovykina.to_do.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uk.bovykina.to_do.dto.UserCreateDto;
import uk.bovykina.to_do.dto.UserDto;
import uk.bovykina.to_do.model.User;
import uk.bovykina.to_do.service.UserServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthRestController {
    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCreateDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDto userDto = userService.getUserByUsername(loginRequest.getUsername());
            return ResponseEntity.ok(userDto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto);
    }
}
