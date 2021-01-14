CREATE TABLE TASK
(
    id        bigserial    not null,
    task_name varchar(255) not null,
    CONSTRAINT PK_TASKID PRIMARY KEY (id)
);
