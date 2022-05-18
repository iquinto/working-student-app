DROP SCHEMA if exists public cascade;
CREATE SCHEMA public;

CREATE TABLE job_position
(
    ID   serial,
    NAME varchar(100) NOT NULL,
    CONSTRAINT job_position_pkey PRIMARY KEY (id),
    CONSTRAINT uk_job_position UNIQUE (name)
);

CREATE TABLE area
(
    ID   serial,
    NAME varchar(100) NOT NULL,
    CONSTRAINT area_pkey PRIMARY KEY (id),
    CONSTRAINT uk_area UNIQUE (name)
);

CREATE TABLE university
(
    id serial,
    name varchar(150)  NOT NULL,
    CONSTRAINT university_pkey PRIMARY KEY (id),
    CONSTRAINT uk_university UNIQUE (name)
);

CREATE TABLE address
(
    id serial,
    street varchar (100),
    city varchar(75) NOT NULL,
    zipcode varchar(5) NOT NULL,
    province varchar(75) NOT NULL,
    country varchar(50) NOT NULL,
    CONSTRAINT address_pk PRIMARY KEY(id)
);

CREATE TABLE photo
(
    id varchar(100) NOT NULL,
    content oid NOT NULL,
    name varchar(50) NOT NULL,
    type varchar(20) NOT NULL,
    CONSTRAINT photo_pkey PRIMARY KEY (id)
);

CREATE TABLE users
(
    id serial,
    username varchar(30) NOT NULL,
    password varchar(150) NOT NULL,
    email varchar(255)  NOT NULL,
    name varchar(50)  NOT NULL,
    surname varchar(50),
    phone varchar(50) NOT NULL,
    rol varchar(25) NOT NULL,
    address_id bigint,
    description text,
    photo_id varchar(100),
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username),
    CONSTRAINT fk_users_address FOREIGN KEY (address_id)
        REFERENCES address (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_users_photo FOREIGN KEY (photo_id)
        REFERENCES photo (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE employer
(
    id serial NOT NULL,
    is_company boolean,
    website varchar(50),
    area_id bigint,
    CONSTRAINT employer_pkey PRIMARY KEY (id),
    CONSTRAINT  fk_employer_users FOREIGN KEY (id)
        REFERENCES users (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_employer_area FOREIGN KEY (area_id)
        REFERENCES area (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE student
(
    id bigint NOT NULL,
    birthday date NOT NULL,
    has_car boolean NOT NULL,
    sex varchar(25)  NOT NULL,
    student_id varchar(30) NOT NULL,
    university_id bigint,
    CONSTRAINT student_pkey PRIMARY KEY (id),
    CONSTRAINT fk_student_university FOREIGN KEY (university_id)
        REFERENCES university (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_student_users FOREIGN KEY (id)
        REFERENCES users (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE student_job_position
(
    student_id bigint NOT NULL,
    job_position_id bigint NOT NULL,
    CONSTRAINT student_job_position_pkey PRIMARY KEY (student_id, job_position_id),
    CONSTRAINT fk_student_job_position_student FOREIGN KEY (student_id)
        REFERENCES student (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_student_job_position_job_position FOREIGN KEY (job_position_id)
        REFERENCES job_position (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE slot
(
    id serial NOT NULL,
    day varchar(255) NOT NULL,
    end_time double precision NOT NULL,
    start_time double precision NOT NULL,
    time_equivalent double precision,
    CONSTRAINT slot_pkey PRIMARY KEY (id)
);

CREATE TABLE schedule
(
    id serial NOT NULL,
    reserve boolean NOT NULL,
    slot_id bigint,
    student_id bigint NOT NULL,
    CONSTRAINT schedule_pkey PRIMARY KEY (id),
    CONSTRAINT fk_schedule_slot FOREIGN KEY (slot_id)
        REFERENCES slot (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_schedule_student FOREIGN KEY (student_id)
        REFERENCES student(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE reservation
(
    id serial NOT NULL,
    accepted boolean,
    end_date date,
    number_of_weeks integer,
    start_date date,
    total_hours double precision,
    employer_id bigint NOT NULL,
    student_id bigint NOT NULL,
    CONSTRAINT reservation_pkey PRIMARY KEY (id),
    CONSTRAINT fk_reservation_student FOREIGN KEY (student_id)
        REFERENCES student (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_reservation_employer FOREIGN KEY (employer_id)
        REFERENCES employer (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE reservation_schedule
(
    reservation_id bigint NOT NULL,
    schedule_id bigint NOT NULL,
    CONSTRAINT reservation_schedule_pkey PRIMARY KEY (reservation_id, schedule_id),
    CONSTRAINT fk_reservation_schedule_reservation FOREIGN KEY (reservation_id)
        REFERENCES public.reservation (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_reservation_schedule_schedule FOREIGN KEY (schedule_id)
        REFERENCES schedule (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE history
(
    id serial NOT NULL,
    date_created date NOT NULL,
    month integer NOT NULL,
    reservation_id bigint NOT NULL,
    username varchar(30)  NOT NULL,
    year integer NOT NULL,
    CONSTRAINT history_pkey PRIMARY KEY (id)
);

CREATE TABLE rating
(
    id serial NOT NULL,
    comment varchar(255),
    rate integer NOT NULL,
    reservation_id bigint,
    student_id bigint NOT NULL,
    CONSTRAINT rating_pkey PRIMARY KEY (id),
    CONSTRAINT fk_rating_student FOREIGN KEY (student_id)
        REFERENCES student (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE job_post
(
    id serial NOT NULL,
    description text NOT NULL,
    expiration date,
    publication_date date NOT NULL,
    requirements text  NOT NULL,
    start_date date NOT NULL,
    title varchar(50) NOT NULL,
    type varchar(20) NOT NULL,
    year_salary double precision  NOT NULL,
    employer_id bigint NOT NULL,
    CONSTRAINT job_post_pkey PRIMARY KEY (id),
    CONSTRAINT fk_job_post_employer FOREIGN KEY (employer_id)
        REFERENCES employer(id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);


CREATE TABLE job_post_area
(
    area_id bigint,
    job_post_id bigint NOT NULL,
    CONSTRAINT job_post_area_pkey PRIMARY KEY (job_post_id),
    CONSTRAINT fk_job_post_area_area FOREIGN KEY (area_id)
        REFERENCES area (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_job_post_area_job_post FOREIGN KEY (job_post_id)
        REFERENCES job_post (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE job_post_student
(
    job_post_id bigint NOT NULL,
    student_id bigint NOT NULL,
    CONSTRAINT job_post_student_pkey PRIMARY KEY (job_post_id, student_id),
    CONSTRAINT fk_job_post_student_student_id FOREIGN KEY (student_id)
    REFERENCES student (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_job_post_student_job_post_id FOREIGN KEY (job_post_id)
    REFERENCES job_post (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE resume
(
    id  varchar(100) NOT NULL,
    alias varchar(50),
    content oid NOT NULL,
    description varchar(255),
    name varchar(50) NOT NULL,
    type varchar(20) NOT NULL,
    student_id bigint NOT NULL,
    CONSTRAINT resume_pkey PRIMARY KEY (id),
    CONSTRAINT fk_resume_student FOREIGN KEY (student_id)
        REFERENCES student(id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE notification
(
    id serial NOT NULL,
    color varchar(20),
    created date NOT NULL,
    icon varchar(20),
    message varchar(255) NOT NULL,
    read boolean NOT NULL,
    subject varchar(50) NOT NULL,
    destination_id bigint,
    origin_id bigint,
    CONSTRAINT notification_pkey PRIMARY KEY (id),
    CONSTRAINT fk_notification_origin FOREIGN KEY (origin_id)
        REFERENCES users(id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_notification_destination FOREIGN KEY (destination_id)
        REFERENCES users(id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE notification_slot
(
    notification_id bigint NOT NULL,
    slot_id bigint NOT NULL,
    CONSTRAINT notification_slot_pkey PRIMARY KEY (notification_id, slot_id),
    CONSTRAINT fk_notification_slot_slot FOREIGN KEY (slot_id)
        REFERENCES slot (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_notification_slot_notification FOREIGN KEY (notification_id)
        REFERENCES notification (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);