package com.tochratana.task.controller;

import com.tochratana.task.dto.NoteCreateRequestDto;
import com.tochratana.task.dto.NoteResponse;
import com.tochratana.task.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@PathVariable Long taskId,
                                                   @Valid @RequestBody NoteCreateRequestDto noteCreateRequestDto) {
        NoteResponse response = noteService.createNote(taskId, noteCreateRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getNotesByTaskId(@PathVariable Long taskId) {
        List<NoteResponse> notes = noteService.getNotesByTaskId(taskId);
        return ResponseEntity.ok(notes);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable Long taskId,
                                                   @PathVariable Long noteId,
                                                   @Valid @RequestBody NoteCreateRequestDto noteCreateRequestDto) {
        NoteResponse response = noteService.updateNote(noteId, noteCreateRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long taskId, @PathVariable Long noteId) {
        noteService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }
}