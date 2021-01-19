package com.devilpanda.todoapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TASK_LIST")
public @Getter
@Setter
class TaskList {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name = "task_list_name")
    private String taskListName;

    @Column(name = "task_list_description")
    private String taskListDescription;

    @Column(name = "owner_id")
    private long ownerId;

//    todo fix managed/back reference error

//    @ManyToMany(mappedBy = "taskLists")
//    @JsonBackReference
//    private Set<User> users;

    @OneToMany(mappedBy = "taskList")
    @JsonManagedReference
    private Set<Task> tasks;
}
