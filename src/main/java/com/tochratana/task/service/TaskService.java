package com.tochratana.task.service;

import com.tochratana.task.domain.TaskStatus;
import com.tochratana.task.dto.TaskCreateRequestDto;
import com.tochratana.task.dto.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskCreateRequestDto taskCreateRequestDto);
    List<TaskResponse> getAllTasks();
    List<TaskResponse> getTasksByStatus(TaskStatus status);
    TaskResponse getTaskById(Long taskId);
    TaskResponse updateTask(Long taskId, TaskCreateRequestDto taskCreateRequestDto);
    void deleteTask(Long taskId);
}