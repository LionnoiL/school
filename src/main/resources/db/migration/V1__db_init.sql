CREATE TABLE public.school (
	id long NOT NULL GENERATED ALWAYS AS IDENTITY,
	name varchar(100) NOT NULL,
	description varchar(500) NULL,
	address varchar(250) NULL,
	CONSTRAINT school_pkey PRIMARY KEY (id)
);

CREATE TABLE public.academic_year (
	id long NOT NULL GENERATED ALWAYS AS IDENTITY,
	name varchar(50) NOT NULL,
	start_year date NOT NULL,
	end_year date NOT NULL,
	school_id long NOT NULL,
	CONSTRAINT academic_year_pkey PRIMARY KEY (id),
	CONSTRAINT year_fk_school FOREIGN KEY (school_id) REFERENCES public.school(id)
);

CREATE TABLE public.academic_term (
	id long NOT NULL GENERATED ALWAYS AS IDENTITY,
	academic_year_id long NOT NULL,
	start_term date NOT NULL,
	end_term date NOT NULL,
	name varchar(50) NOT NULL,
	CONSTRAINT academic_term_pkey PRIMARY KEY (id),
	CONSTRAINT academic_term_fk_academic_year FOREIGN KEY (academic_year_id) REFERENCES public.academic_year(id)
);

CREATE TABLE public.users (
	id long NOT NULL GENERATED ALWAYS AS IDENTITY,
	username varchar(45) NOT NULL,
	password varchar(64) NOT NULL,
	role varchar(45) NOT NULL,
	enabled bool NULL,
  is_parent bool NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);
CREATE UNIQUE INDEX users_username_idx ON public.users USING btree (username);

CREATE TABLE public.grades (
	id long NOT NULL GENERATED ALWAYS AS IDENTITY,
	name varchar(3) NOT NULL,
	rate numeric(5,2) NOT NULL,
	CONSTRAINT grades_pkey PRIMARY KEY (id)
);

CREATE TABLE public.courses (
	id long NOT NULL GENERATED ALWAYS AS IDENTITY,
	name varchar(100) NOT NULL,
	active bool NULL,
	description varchar(500) NULL,
	CONSTRAINT courses_pkey PRIMARY KEY (id)
);

CREATE TABLE public.school_class (
	id long NOT NULL GENERATED ALWAYS AS IDENTITY,
	name varchar(50) NULL,
	description varchar(500) NULL,
	school_id long NULL,
	CONSTRAINT school_class_pkey PRIMARY KEY (id),
	CONSTRAINT school_clas_fk_school FOREIGN KEY (school_id) REFERENCES public.school(id)
);

CREATE TABLE public.students (
	id long NOT NULL GENERATED ALWAYS AS IDENTITY,
	first_name varchar(45) NOT NULL,
	last_name varchar(45) NOT NULL,
	school_class_id long NOT NULL,
	CONSTRAINT pupils_pkey PRIMARY KEY (id),
	CONSTRAINT pupils_fk_school_class FOREIGN KEY (school_class_id) REFERENCES public.school_class(id)
);

CREATE TABLE public.class_courses (
	class_id long NOT NULL,
	courses_id long NOT NULL,
	CONSTRAINT class_courses_pkey PRIMARY KEY (class_id, courses_id),
	CONSTRAINT class_courses_fk_class FOREIGN KEY (class_id) REFERENCES public.school_class(id),
	CONSTRAINT class_courses_fk_corses FOREIGN KEY (courses_id) REFERENCES public.courses(id)
);

CREATE TABLE public.students_grades (
	id long NOT NULL GENERATED ALWAYS AS IDENTITY,
	student_id long NOT NULL,
	course_id long NOT NULL,
	grade long NOT NULL,
	grade_date date NOT NULL,
	CONSTRAINT students_grades_pkey PRIMARY KEY (id),
	CONSTRAINT grades_student_id_fk_students FOREIGN KEY (student_id) REFERENCES public.students(id),
	CONSTRAINT students_grades_course_id_fkey FOREIGN KEY (course_id) REFERENCES public.courses(id)
);

