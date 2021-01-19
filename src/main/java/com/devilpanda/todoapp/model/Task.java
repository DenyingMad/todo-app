package com.devilpanda.todoapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TASK")
public @Getter
@Setter
class Task {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String taskDescription;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "require_time")
    private int requireTime;

    @ManyToOne
    @JoinColumn(name = "task_list_id")
    @JsonBackReference
    private TaskList taskList;
}
