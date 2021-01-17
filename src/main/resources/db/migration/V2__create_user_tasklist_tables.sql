CREATE TABLE "USER"
(
    id            bigserial    not null,
    login         varchar(255) not null,
    hash_password varchar      not null,
    PRIMARY KEY (id)
);

CREATE TABLE "TASK_LIST"
(
    id                    bigserial    not null,
    task_list_name        varchar(255) not null default 'New task list',
    task_list_description varchar,
    owner_id              bigint       not null,
    PRIMARY KEY (id),
    CONSTRAINT FK_OWNER FOREIGN KEY (id) REFERENCES "USER" (id)
);

CREATE TABLE "USER_TASKLIST"
(
    user_id      bigint not null,
    task_list_id bigint not null,
    PRIMARY KEY (user_id, task_list_id),
    CONSTRAINT FK_USER FOREIGN KEY (user_id) REFERENCES "USER" (id),
    CONSTRAINT FK_TASKLIST FOREIGN KEY (task_list_id) REFERENCES "TASK_LIST" (id)
);

ALTER TABLE TASK
    ALTER task_name SET DEFAULT 'New task',
    ADD COLUMN description    varchar,
    ADD COLUMN start_time     timestamp,
    ADD COLUMN require_time   int,
    ADD COLUMN parent_task_id bigint,
    ADD COLUMN task_list_id   bigint not null,
    ADD CONSTRAINT FK_TASKlIST_ID FOREIGN KEY (id) REFERENCES "TASK_LIST" (id)
;