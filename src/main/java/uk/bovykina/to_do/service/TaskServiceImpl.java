package uk.bovykina.to_do.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.bovykina.to_do.dto.TaskCreateDto;
import uk.bovykina.to_do.dto.TaskDto;
import uk.bovykina.to_do.dto.TaskUpdateDto;
import uk.bovykina.to_do.dto.UserDto;
import uk.bovykina.to_do.exception.TaskNotFoundException;
import uk.bovykina.to_do.exception.UserNotFoundException;
import uk.bovykina.to_do.model.Task;
import uk.bovykina.to_do.model.User;
import uk.bovykina.to_do.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService; // Injected UserService

    @Override
    public TaskDto getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + taskId));
        return convertEntityToDto(task);
    }

    @Override
    public TaskDto createTask(TaskCreateDto taskCreateDto) {
        Task task = taskRepository.save(convertDtoToEntity(taskCreateDto));
        return convertEntityToDto(task);
    }

    @Override
    public TaskDto updateTask(TaskUpdateDto taskUpdateDto) {
        Task task = taskRepository.findById(taskUpdateDto.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + taskUpdateDto.getId()));

        // Update fields from DTO
        task.setText(taskUpdateDto.getText());
        task.setDone(taskUpdateDto.isDone());

        // Save and return the updated task
        return convertEntityToDto(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDto> getAllTasks(Long userId) {
        return taskRepository.findByUser_Id(userId).stream()
                .map(task -> new TaskDto(task.getId(), task.getText(), task.isDone()))
                .collect(Collectors.toList());
    }


    private Task convertDtoToEntity(TaskCreateDto taskCreateDto) {
        // Retrieve the user entity using userService
        UserDto userDto = userService.getUserById(taskCreateDto.getUserId()); // Assuming this returns a User entity

        User user = convertDtoToUser(userDto);

        return Task.builder()
                .text(taskCreateDto.getText())
                .isDone(false) // New tasks should default to not done
                .user(user)
                .build();
    }
    private User convertDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId()) // Ensure to map the ID and other relevant fields
                .username(userDto.getUsername())
                .build();
    }
    private TaskDto convertEntityToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .text(task.getText())
                .isDone(task.isDone()) // Include the isDone status in the DTO
//                .userId(task.getUser().getId())
                .build();
    }
}
