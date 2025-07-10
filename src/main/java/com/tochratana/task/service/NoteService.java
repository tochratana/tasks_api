package com.tochratana.task.service;

import com.tochratana.task.dto.NoteCreateRequestDto;
import com.tochratana.task.dto.NoteResponse;

import java.util.List;

public interface NoteService {
    NoteResponse createNote(Long taskId, NoteCreateRequestDto noteCreateRequestDto);
    List<NoteResponse> getNotesByTaskId(Long taskId);
    NoteResponse updateNote(Long noteId, NoteCreateRequestDto noteCreateRequestDto);
    void deleteNote(Long noteId);
}