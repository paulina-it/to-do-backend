package uk.bovykina.to_do.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.bovykina.to_do.dto.LoginRequest;
import uk.bovykina.to_do.dto.UserCreateDto;
import uk.bovykina.to_do.dto.UserDto;
import uk.bovykina.to_do.service.UserServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;

//    @PostMapping("/login")
//    public ResponseEntity<UserDto> login(@RequestBody @Valid UserCreateDto loginRequest) {
//        try {
//            // Authenticate the user
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//            // Set the authentication in the SecurityContext
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // Retrieve the authenticated user's details
//            String username = authentication.getName();
//            UserDto userDto = userService.getUserByUsername(username);
//
//            return ResponseEntity.ok(userDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try {
            UserDto user = userService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //    @PostMapping("/signup")
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserDto createUser(@RequestBody @Valid UserCreateDto userCreateDto) {
//        return userService.createUser(userCreateDto);
//    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserCreateDto userCreateDto) {
        try {
            UserDto newUser = userService.createUser(userCreateDto);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
