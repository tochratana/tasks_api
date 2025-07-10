package com.tochratana.task.service.impl;

import com.tochratana.task.domain.Note;
import com.tochratana.task.domain.Task;
import com.tochratana.task.domain.User;
import com.tochratana.task.dto.NoteCreateRequestDto;
import com.tochratana.task.dto.NoteResponse;
import com.tochratana.task.repository.NoteRepository;
import com.tochratana.task.repository.TaskRepository;
import com.tochratana.task.service.NoteService;
import com.tochratana.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public NoteResponse createNote(Long taskId, NoteCreateRequestDto noteCreateRequestDto) {
        User currentUser = userService.getCurrentUserEntity();
        Task task = taskRepository.findByIdAndUserId(taskId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Note note = new Note();
        note.setContent(noteCreateRequestDto.content());
        note.setTask(task);

        note = noteRepository.save(note);

        return mapToNoteResponse(note);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteResponse> getNotesByTaskId(Long taskId) {
        User currentUser = userService.getCurrentUserEntity();
        // Verify task belongs to current user
        taskRepository.findByIdAndUserId(taskId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        List<Note> notes = noteRepository.findByTaskIdOrderByCreatedAtDesc(taskId);
        return notes.stream()
                .map(this::mapToNoteResponse)
                .toList();
    }

    @Override
    public NoteResponse updateNote(Long noteId, NoteCreateRequestDto noteCreateRequestDto) {
        User currentUser = userService.getCurrentUserEntity();
        Note note = noteRepository.findByIdAndTaskUserId(noteId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Note not found"));

        note.setContent(noteCreateRequestDto.content());
        note = noteRepository.save(note);

        return mapToNoteResponse(note);
    }

    @Override
    public void deleteNote(Long noteId) {
        User currentUser = userService.getCurrentUserEntity();
        Note note = noteRepository.findByIdAndTaskUserId(noteId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Note not found"));
        noteRepository.delete(note);
    }

    private NoteResponse mapToNoteResponse(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getContent(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }
}