package uk.bovykina.to_do.service;

import uk.bovykina.to_do.dto.TaskCreateDto;
import uk.bovykina.to_do.dto.TaskDto;
import uk.bovykina.to_do.dto.TaskUpdateDto;

import java.util.List;

public interface TaskService {
    TaskDto getTaskById(Long taskId);
    TaskDto createTask(TaskCreateDto taskCreateDto);
    TaskDto updateTask(TaskUpdateDto taskUpdateDto);
    void deleteTask(Long id);
    List<TaskDto> getAllTasks(Long userId);
}
