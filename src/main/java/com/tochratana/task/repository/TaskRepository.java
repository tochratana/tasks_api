package com.tochratana.task.repository;

import com.tochratana.task.domain.Task;
import com.tochratana.task.domain.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Task> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, TaskStatus status);
    Optional<Task> findByIdAndUserId(Long taskId, Long userId);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND " +
            "(:status IS NULL OR t.status = :status)")
    List<Task> findTasksByUserAndStatus(@Param("userId") Long userId,
                                        @Param("status") TaskStatus status);
}