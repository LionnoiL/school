CREATE TABLE school (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name varchar(100) NOT NULL,
	description varchar(500) NULL,
	address varchar(250) NULL,
	CONSTRAINT school_pkey PRIMARY KEY (id)
);

CREATE TABLE academic_year (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name varchar(50) NOT NULL,
	start_year date NOT NULL,
	end_year date NOT NULL,
	school_id BIGINT NOT NULL,
	CONSTRAINT academic_year_pkey PRIMARY KEY (id),
	CONSTRAINT year_fk_school FOREIGN KEY (school_id) REFERENCES school(id)
);

CREATE TABLE academic_term (
	id BIGINT NOT NULL AUTO_INCREMENT,
	academic_year_id BIGINT NOT NULL,
	start_term date NOT NULL,
	end_term date NOT NULL,
	name varchar(50) NOT NULL,
	CONSTRAINT academic_term_pkey PRIMARY KEY (id),
	CONSTRAINT academic_term_fk_academic_year FOREIGN KEY (academic_year_id) REFERENCES academic_year(id)
);

CREATE TABLE users (
	id BIGINT NOT NULL AUTO_INCREMENT,
	username varchar(45) NOT NULL,
	password varchar(64) NOT NULL,
	role varchar(45) NOT NULL,
	enabled bool NULL,
  is_parent bool NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE grades (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name varchar(3) NOT NULL,
	rate numeric(5,2) NOT NULL,
	CONSTRAINT grades_pkey PRIMARY KEY (id)
);

CREATE TABLE courses (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name varchar(100) NOT NULL,
	active bool NULL,
	description varchar(500) NULL,
	CONSTRAINT courses_pkey PRIMARY KEY (id)
);

CREATE TABLE school_class (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name varchar(50) NULL,
	description varchar(500) NULL,
	school_id BIGINT NULL,
	CONSTRAINT school_class_pkey PRIMARY KEY (id),
	CONSTRAINT school_clas_fk_school FOREIGN KEY (school_id) REFERENCES school(id)
);

CREATE TABLE students (
	id BIGINT NOT NULL AUTO_INCREMENT,
	first_name varchar(45) NOT NULL,
	last_name varchar(45) NOT NULL,
	school_class_id BIGINT NOT NULL,
	CONSTRAINT pupils_pkey PRIMARY KEY (id),
	CONSTRAINT pupils_fk_school_class FOREIGN KEY (school_class_id) REFERENCES school_class(id)
);

CREATE TABLE class_courses (
	class_id BIGINT NOT NULL,
	courses_id BIGINT NOT NULL,
	CONSTRAINT class_courses_pkey PRIMARY KEY (class_id, courses_id),
	CONSTRAINT class_courses_fk_class FOREIGN KEY (class_id) REFERENCES school_class(id),
	CONSTRAINT class_courses_fk_corses FOREIGN KEY (courses_id) REFERENCES courses(id)
);

CREATE TABLE students_grades (
	id BIGINT NOT NULL AUTO_INCREMENT,
	student_id BIGINT NOT NULL,
	course_id BIGINT NOT NULL,
	grade BIGINT NOT NULL,
	grade_date date NOT NULL,
	CONSTRAINT students_grades_pkey PRIMARY KEY (id),
	CONSTRAINT grades_student_id_fk_students FOREIGN KEY (student_id) REFERENCES students(id),
	CONSTRAINT students_grades_course_id_fkey FOREIGN KEY (course_id) REFERENCES courses(id)
);

