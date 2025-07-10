package com.tochratana.task.repository;

import com.tochratana.task.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByTaskIdOrderByCreatedAtDesc(Long taskId);
    Optional<Note> findByIdAndTaskId(Long noteId, Long taskId);
    Optional<Note> findByIdAndTaskUserId(Long noteId, Long userId);
}