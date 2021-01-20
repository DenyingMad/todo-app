CREATE TABLE USERS
(
    id            bigserial    not null,
    login         varchar(255) not null,
    email         varchar(100) not null,
    hash_password varchar      not null,
    PRIMARY KEY (id)
);

CREATE TABLE TASK_LIST
(
    id                    bigserial    not null,
    task_list_name        varchar(255) not null default 'New task list',
    task_list_description varchar,
    owner_id              bigint       not null,
    PRIMARY KEY (id),
    CONSTRAINT FK_OWNER FOREIGN KEY (owner_id) REFERENCES USERS (id)
);

CREATE TABLE USER_TASKLIST
(
    user_id      bigint not null,
    task_list_id bigint not null,
    PRIMARY KEY (user_id, task_list_id),
    CONSTRAINT FK_USER FOREIGN KEY (user_id) REFERENCES USERS (id),
    CONSTRAINT FK_TASKLIST FOREIGN KEY (task_list_id) REFERENCES TASK_LIST (id)
);

CREATE TABLE TASK
(
    id             bigserial    not null,
    task_name      varchar(255) not null DEFAULT 'New task',
    description    varchar,
    start_time     timestamp,
    require_time   bigint,
    parent_task_id bigint,
    task_list_id   bigint       not null,
    PRIMARY KEY (id),
    CONSTRAINT FK_TASKLIST_ID FOREIGN KEY (task_list_id) REFERENCES TASK_LIST (id)
);

CREATE TABLE ROLE
(
    id   bigserial   not null,
    name varchar(20) not null,
    PRIMARY KEY (id)
);

CREATE TABLE USER_ROLE
(
    user_id bigint not null,
    role_id bigint not null,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id),
    CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id) REFERENCES ROLE (id)
);

INSERT INTO ROLE(name)
VALUES ('ROLE_ADMIN');
INSERT INTO ROLE(name)
VALUES ('ROLE_USER');