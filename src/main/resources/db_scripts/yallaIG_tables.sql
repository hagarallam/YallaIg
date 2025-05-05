drop database if exists `yallaig`;
create database yallaig;

use yallaig ;

-- -------------------------------- LOCKUP TABLE ----------------------------
drop table if exists `roles`;
create table `roles`(
	role_id int primary key auto_increment,
    role_name varchar(50) not null unique
);

drop table if exists `exam_sessions`;
create table `exam_sessions`(
	exam_session_id int primary key auto_increment,
    exam_session_value varchar(100) not null unique
);

drop table if exists `statuses`;
create table `statuses`(
	status_id int primary key auto_increment,
    status_name varchar(100) not null unique
);


drop table if exists `approval_statuses`;
create table `approval_statuses`(
	status_id int primary key auto_increment,
    status_name varchar(100) not null unique
);

drop table if exists `subjects`;
CREATE TABLE `subjects` (
    subject_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(100) NOT NULL UNIQUE
);

drop table if exists `levels`;
CREATE TABLE `levels` (
    level_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    level_name VARCHAR(100) NOT NULL UNIQUE
);


drop table if exists `resource_categories`;
CREATE TABLE `resource_categories` (
    category_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE
);



-- -----------------------------------------------------------------
drop table if exists `files`;
create table `files`(
    file_id INT not null AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) not null,
    type VARCHAR(255) not null,
	size BIGINT not null,
    data LONGBLOB not null
);


drop table if exists `users`;
create table  `users`(
	user_id int primary key auto_increment ,
    full_name varchar(255) not null ,
	email varchar(255) not null unique  ,
    password varchar(255) not null ,
    user_status INT not null ,
	mail_verified tinyint(1) not null ,
    file_id int ,
    creation_date DATETIME not null,
    user_points int default 0 ,
    FOREIGN KEY (user_status) REFERENCES statuses(status_id),
    FOREIGN KEY ( file_id) REFERENCES files(file_id)
);

drop table if exists `countries`;
create table `countries`(
    country_id int not null AUTO_INCREMENT PRIMARY KEY,
    country_name VARCHAR(255) NOT NULL
);

drop table if exists `phone_codes`;
create table `phone_codes`(
    phone_code_id int not null AUTO_INCREMENT PRIMARY KEY,
    phone_code VARCHAR(10) NOT NULL,
    country_id int not null ,
    FOREIGN KEY (country_id) REFERENCES countries(country_id)
);

drop table if exists `users_contact_info`;
create table  `users_contact_info`(
	contact_info_id int primary key auto_increment ,
    user_id int not null ,
	phone varchar(255) not null ,
    phone_code_id int not null ,
    country_id int not null  ,
	FOREIGN KEY (user_id) REFERENCES users(user_id),
	FOREIGN KEY (phone_code_id) REFERENCES phone_codes(phone_code_id),
	FOREIGN KEY (country_id) REFERENCES countries(country_id)
);

drop table if exists `users_roles`;
create table `users_roles`(
	user_id int not null,
    role_id INT not null ,
	FOREIGN KEY (user_id) REFERENCES users(user_id),
	FOREIGN KEY (role_id) REFERENCES roles(role_id)
);
drop table if exists `instructors`;
create table `instructors`(
    instructor_id INT not null AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) not null,
	last_name VARCHAR(255) not null,
    bio TEXT,
    file_id INT,
	creation_date DATETIME not null ,
    created_by int not null ,
    last_modified_by int not null ,
    last_modification_date DATETIME not null ,
	FOREIGN KEY (created_by) REFERENCES users(user_id),
	FOREIGN KEY (last_modified_by) REFERENCES users(user_id),
    FOREIGN KEY ( file_id) REFERENCES files(file_id)
);

drop table if exists `courses`;
CREATE TABLE `courses` (
    course_id INT not null AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) not null,
    price DECIMAL(10, 2) not null,
    link VARCHAR(255) ,
    start_date DATETIME ,
    end_date DATETIME,
    description TEXT,
    exam_session_id INT NOT NULL ,
    subject_id INT NOT NULL ,
	level_id INT NOT NULL ,
    file_id INT,
	creation_date DATETIME not null ,
    created_by int not null ,
    last_modified_by int not null ,
    last_modification_date DATETIME not null ,
	FOREIGN KEY (created_by) REFERENCES users(user_id),
	FOREIGN KEY (last_modified_by) REFERENCES users(user_id),
    FOREIGN KEY (exam_session_id) REFERENCES exam_sessions(exam_session_id),
	FOREIGN KEY (subject_id) REFERENCES subjects(subject_id),
	FOREIGN KEY (level_id) REFERENCES levels(level_id),
    FOREIGN KEY ( file_id) REFERENCES files( file_id)
);
drop table if exists `lessons`;
CREATE TABLE `lessons` (
    lesson_id INT not null AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) not null ,
	price DECIMAL(10, 2) not null ,
	link VARCHAR(255) ,
    description TEXT,
    instructor_id int not null,
    course_id INT not null ,
	lesson_date DATETIME not null,
	file_id INT,
	creation_date DATETIME not null ,
    created_by int not null ,
    last_modified_by int not null ,
    last_modification_date DATETIME not null ,
	FOREIGN KEY (created_by) REFERENCES users(user_id),
	FOREIGN KEY (last_modified_by) REFERENCES users(user_id),
	foreign key(instructor_id) REFERENCES instructors(instructor_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ,
	FOREIGN KEY ( file_id) REFERENCES files( file_id)
    );

drop table if exists `instructors_courses`;
create table `instructors_courses`(
	instructor_id int not null,
    course_id int not null ,
	PRIMARY KEY (instructor_id, course_id),
    foreign key(instructor_id) REFERENCES instructors(instructor_id),
	foreign key(course_id) REFERENCES courses(course_id)
);

drop table if exists `posts`;
create table `posts`(
	post_id int not null AUTO_INCREMENT primary key ,
	title varchar(255) not null ,
    content TEXT not null ,
	file_id INT,
	approval_status INT not null ,
	creation_date DATETIME not null ,
    created_by int not null ,
    last_modified_by int not null ,
    last_modification_date DATETIME not null ,
	FOREIGN KEY (created_by) REFERENCES users(user_id),
	FOREIGN KEY (last_modified_by) REFERENCES users(user_id),
	FOREIGN KEY (file_id) REFERENCES files( file_id),
	FOREIGN KEY (approval_status) REFERENCES approval_statuses(status_id)
    );


drop table if exists `comments`;
create table `comments`(
	comment_id int not null AUTO_INCREMENT primary key ,
    content TEXT not null ,
	file_id INT,
    post_id int not null ,
    approval_status INT not null ,
	creation_date DATETIME not null ,
    created_by int not null ,
    last_modified_by int not null ,
    last_modification_date DATETIME not null ,
	FOREIGN KEY (created_by) REFERENCES users(user_id),
	FOREIGN KEY (last_modified_by) REFERENCES users(user_id),
    foreign key(post_id) REFERENCES posts(post_id),
	FOREIGN KEY (file_id) REFERENCES files( file_id),
    FOREIGN KEY (approval_status) REFERENCES approval_statuses(status_id)
);

drop table if exists `reactions`;
create table `reactions`(
	reaction_id int not null AUTO_INCREMENT primary key ,
	user_id INT not null ,
    reactable_id int not null ,
    reactable_type varchar(50) not null ,
    reaction_type varchar(50) not null ,
    foreign key(user_id) REFERENCES users(user_id),
    Unique KEY unique_reaction (reaction_id,user_id,reactable_id)
);

drop table if exists `orders`;
create table `orders`(
	order_id INT not null AUTO_INCREMENT PRIMARY KEY,
    total_price decimal(10,2) not null,
	creation_date DATETIME not null ,
    created_by int not null ,
    last_modified_by int not null ,
    last_modification_date DATETIME not null ,
	FOREIGN KEY (created_by) REFERENCES users(user_id),
	FOREIGN KEY (last_modified_by) REFERENCES users(user_id)
    );
drop table if exists `students_courses`;
create table `students_courses`(
	student_id int not null,
    course_id int not null ,
    order_id int not null ,
    student_grade int not null check (student_grade between 1 and 13) ,
    school VARCHAR(255) not null ,
    parent_email VARCHAR(255) not null ,
    parent_number VARCHAR(255) not null ,
	creation_date DATETIME not null ,
	PRIMARY KEY (student_id, course_id),
    foreign key(student_id) REFERENCES users(user_id),
	foreign key(course_id) REFERENCES courses(course_id),
    foreign key(order_id) REFERENCES orders(order_id)
);

drop table if exists `students_lessons`;
create table `students_lessons`(
	student_id int not null,
    lesson_id int not null ,
    order_id int not null ,
	creation_date DATETIME not null ,
	PRIMARY KEY (student_id, lesson_id),
    foreign key(student_id) REFERENCES users(user_id),
	foreign key(lesson_id) REFERENCES lessons(lesson_id),
	foreign key(order_id) REFERENCES orders(order_id)
);

drop table if exists `users_wallet`;
create table `users_wallet`(
	wallet_id int not null primary key auto_increment,
    balance DECIMAL(12, 2) not null DEFAULT '0.00',
	creation_date DATETIME not null ,
    created_by int not null ,
    last_modified_by int not null ,
    last_modification_date DATETIME not null ,
	FOREIGN KEY (created_by) REFERENCES users(user_id),
	FOREIGN KEY (last_modified_by) REFERENCES users(user_id)
    );

drop table if exists `wallet_transactions`;
create table `wallet_transactions`(
	id int not null primary key auto_increment,
    transaction_id VARCHAR(255) not null unique ,
	wallet_id int not null,
    amount DECIMAL(10, 2) not null DEFAULT '0.00',
    payment_method varchar(100) not null ,
	creation_date DATETIME not null ,
    created_by int not null ,
    last_modified_by int not null ,
    last_modification_date DATETIME not null ,
	FOREIGN KEY (created_by) REFERENCES users(user_id),
	FOREIGN KEY (last_modified_by) REFERENCES users(user_id),
    foreign key(wallet_id) REFERENCES users_wallet(wallet_id)
    );

drop table if exists `password_reset_tokens`;
create table `password_reset_tokens`(
	id int not null primary key auto_increment,
    token varchar(255) not null ,
    user_id int not null ,
    expiry_date DATETIME not null ,
    foreign key(user_id) REFERENCES users(user_id)
);

drop table if exists `teacher_applications`;
create table `teacher_applications`(
	application_id int not null primary key auto_increment,
    name varchar(255) not null ,
	email varchar(255) not null ,
    phone varchar(255) not null ,
    specialization varchar(255) not null ,
    file_id int ,
	creation_date DATETIME not null,
	FOREIGN KEY (file_id) REFERENCES files( file_id)
);


drop table if exists `blogs`;
create table `blogs`(
	blog_id int not null primary key auto_increment,
    title varchar(255) not null ,
	content TEXT not null ,
    file_id int ,
    blog_category INT NOT NULL ,
    creation_date DATETIME not null ,
    created_by int not null ,
    last_modified_by int not null ,
    last_modification_date DATETIME not null ,
	FOREIGN KEY (created_by) REFERENCES users(user_id),
	FOREIGN KEY (last_modified_by) REFERENCES users(user_id),
	FOREIGN KEY (file_id) REFERENCES files( file_id),
    FOREIGN KEY (blog_category) REFERENCES subjects(subject_id)
);


drop table if exists `resources`;
create table `resources`(
	resource_id int not null primary key auto_increment,
    title varchar(255) not null ,
	description TEXT not null ,
	price DECIMAL(10, 2) not null,
    file_id int ,
	image_id int ,
    resource_category INT NOT NULL ,
	creation_date DATETIME not null ,
    created_by int not null ,
    last_modified_by int not null ,
    last_modification_date DATETIME not null ,
	FOREIGN KEY (created_by) REFERENCES users(user_id),
	FOREIGN KEY (last_modified_by) REFERENCES users(user_id),
	FOREIGN KEY (file_id) REFERENCES files( file_id),
	FOREIGN KEY (image_id) REFERENCES files( file_id),
    FOREIGN KEY (resource_category) REFERENCES resource_categories(category_id)
    );


drop table if exists `students_resources`;
create table `students_resources`(
	student_id int not null,
    resource_id int not null ,
    order_id int not null ,
	creation_date DATETIME not null ,
	PRIMARY KEY (student_id, resource_id),
    foreign key(student_id) REFERENCES users(user_id),
	foreign key(resource_id) REFERENCES resources(resource_id),
    foreign key(order_id) REFERENCES orders(order_id)
);

drop table if exists `points_settings`;
create table `points_settings`(
	setting_name varchar(255) not null Primary key ,
    point_count int not null
);

drop table if exists `emails_verification_tokens`;
create table `emails_verification_tokens`(
	id int not null primary key auto_increment,
    token varchar(255) not null ,
    user_id int not null ,
    expiry_date DATETIME not null ,
    foreign key(user_id) REFERENCES users(user_id)
);