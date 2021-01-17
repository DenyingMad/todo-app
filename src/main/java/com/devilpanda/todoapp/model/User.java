package com.devilpanda.todoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "USER")
public @Getter
@Setter
class User {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "hash_password")
    @JsonIgnore
    private String hashPassword;

    @ManyToMany
    @JoinTable(
            name = "USER_TASKLIST",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_list_id")
    )
    @JsonManagedReference
    private Set<TaskList> taskLists;
}