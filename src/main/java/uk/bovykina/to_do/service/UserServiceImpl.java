package uk.bovykina.to_do.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.bovykina.to_do.dto.UserCreateDto;
import uk.bovykina.to_do.dto.UserDto;
import uk.bovykina.to_do.dto.UserUpdateDto;
import uk.bovykina.to_do.exception.UserNotFoundException;
import uk.bovykina.to_do.model.User;
import uk.bovykina.to_do.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return convertEntityToDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return convertEntityToDto(user);
    }


    @Override
    public UserDto createUser(UserCreateDto userCreateDto) {
        if (userRepository.findByUsername(userCreateDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        return convertEntityToDto(user);
    }

    @Override
    public UserDto login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return convertEntityToDto(user.get());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    };

    @Override
    public UserDto updateUser(UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userUpdateDto.getId())); // Consider using a custom exception

        user.setUsername(userUpdateDto.getUsername());
        user.setPassword(userUpdateDto.getPassword());

        userRepository.save(user);
        return convertEntityToDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id); // Consider using a custom exception
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private User convertCreateDtoToEntity(UserCreateDto userCreateDto) {
        return User.builder()
                .username(userCreateDto.getUsername())
                .password(userCreateDto.getPassword()) // Consider encrypting this before storing
                .build();
    }

    private UserDto convertEntityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
