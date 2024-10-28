package uk.bovykina.to_do.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.bovykina.to_do.dto.TaskCreateDto;
import uk.bovykina.to_do.dto.TaskDto;
import uk.bovykina.to_do.dto.TaskUpdateDto;
import uk.bovykina.to_do.service.TaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskRestController {
    private final TaskService taskService;


    @GetMapping()
    public List<TaskDto> getAllTasks(@RequestParam Long userId) {
        return taskService.getAllTasks(userId);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody @Valid TaskCreateDto taskCreateDto) {
        System.out.println("Received Task Create Request: " + taskCreateDto);
        return taskService.createTask(taskCreateDto);
    }

    @PutMapping("/update")
    public TaskDto updateTask(@RequestBody TaskUpdateDto taskUpdateDto) {
        return taskService.updateTask(taskUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
