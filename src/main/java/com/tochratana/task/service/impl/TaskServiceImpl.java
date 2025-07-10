package com.tochratana.task.service.impl;

import com.tochratana.task.domain.Note;
import com.tochratana.task.domain.Task;
import com.tochratana.task.domain.TaskStatus;
import com.tochratana.task.domain.User;
import com.tochratana.task.dto.NoteResponse;
import com.tochratana.task.dto.TaskCreateRequestDto;
import com.tochratana.task.dto.TaskResponse;
import com.tochratana.task.repository.TaskRepository;
import com.tochratana.task.service.TaskService;
import com.tochratana.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public TaskResponse createTask(TaskCreateRequestDto taskCreateRequestDto) {
        User currentUser = userService.getCurrentUserEntity();

        Task task = new Task();
        task.setTitle(taskCreateRequestDto.title());
        task.setDescription(taskCreateRequestDto.description());
        task.setStatus(taskCreateRequestDto.status() != null ? taskCreateRequestDto.status() : TaskStatus.PENDING);
        task.setDueDate(taskCreateRequestDto.dueDate());
        task.setPriority(taskCreateRequestDto.priority());
        task.setUser(currentUser);

        // Add notes if provided
        if (taskCreateRequestDto.noteContents() != null) {
            List<Note> notes = taskCreateRequestDto.noteContents().stream()
                    .map(content -> {
                        Note note = new Note();
                        note.setContent(content);
                        note.setTask(task);
                        return note;
                    })
                    .collect(Collectors.toList());
            task.setNotes(notes);
        }

        task = taskRepository.save(task);

        return mapToTaskResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        User currentUser = userService.getCurrentUserEntity();
        List<Task> tasks = taskRepository.findByUserIdOrderByCreatedAtDesc(currentUser.getId());
        return tasks.stream()
                .map(this::mapToTaskResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByStatus(TaskStatus status) {
        User currentUser = userService.getCurrentUserEntity();
        List<Task> tasks = taskRepository.findByUserIdAndStatusOrderByCreatedAtDesc(currentUser.getId(), status);
        return tasks.stream()
                .map(this::mapToTaskResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long taskId) {
        User currentUser = userService.getCurrentUserEntity();
        Task task = taskRepository.findByIdAndUserId(taskId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return mapToTaskResponse(task);
    }

    @Override
    public TaskResponse updateTask(Long taskId, TaskCreateRequestDto taskCreateRequestDto) {
        User currentUser = userService.getCurrentUserEntity();
        Task task = taskRepository.findByIdAndUserId(taskId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(taskCreateRequestDto.title());
        task.setDescription(taskCreateRequestDto.description());
        task.setStatus(taskCreateRequestDto.status() != null ? taskCreateRequestDto.status() : task.getStatus());
        task.setDueDate(taskCreateRequestDto.dueDate());
        task.setPriority(taskCreateRequestDto.priority());

        task = taskRepository.save(task);

        return mapToTaskResponse(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        User currentUser = userService.getCurrentUserEntity();
        Task task = taskRepository.findByIdAndUserId(taskId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
    }

    private TaskResponse mapToTaskResponse(Task task) {
        List<NoteResponse> noteResponses = task.getNotes().stream()
                .map(note -> new NoteResponse(
                        note.getId(),
                        note.getContent(),
                        note.getCreatedAt(),
                        note.getUpdatedAt()
                ))
                .toList();

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getPriority(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                noteResponses
        );
    }
}