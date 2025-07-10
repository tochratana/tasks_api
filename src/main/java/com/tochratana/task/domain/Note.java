package com.tochratana.task.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
