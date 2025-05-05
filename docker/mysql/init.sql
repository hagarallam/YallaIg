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



-- -------------------------------- LOCKUP TABLE DATA----------------------------
INSERT INTO roles (role_name) VALUES ('ADMIN');
INSERT INTO roles (role_name) VALUES ('STUDENT');

INSERT INTO exam_sessions (exam_session_value) VALUES ('MAY_JUNE');
INSERT INTO exam_sessions (exam_session_value) VALUES ('OCT_NOV');

INSERT INTO statuses (status_name) VALUES ('IN_ACTIVE');
INSERT INTO statuses (status_name) VALUES ('ACTIVE');

INSERT INTO approval_statuses (status_name) VALUES ('PENDING');
INSERT INTO approval_statuses (status_name) VALUES ('APPROVED');
INSERT INTO approval_statuses (status_name) VALUES ('REJECTED');


INSERT INTO subjects (subject_name) VALUES ('ARABIC');
INSERT INTO subjects (subject_name) VALUES ('ENGLISH');
INSERT INTO subjects (subject_name) VALUES ('MATH');
INSERT INTO subjects (subject_name) VALUES ('PHYSICS');
INSERT INTO subjects (subject_name) VALUES ('CHEMISTRY');
INSERT INTO subjects (subject_name) VALUES ('BIOLOGY');
INSERT INTO subjects (subject_name) VALUES ('GENERAL');


INSERT INTO levels (level_name) VALUES ('LEVEL_1');
INSERT INTO levels (level_name) VALUES ('LEVEL_2');
INSERT INTO levels (level_name) VALUES ('LEVEL_3');


INSERT INTO resource_categories (category_name) VALUES ('BOOK');
INSERT INTO resource_categories (category_name) VALUES ('NOTE');
INSERT INTO resource_categories (category_name) VALUES ('SYLLABUS');
INSERT INTO resource_categories (category_name) VALUES ('PASS_PAPER');


-- ----------------------------Insert a sample user into the users table-------------------
INSERT INTO users (full_name, email, password, user_status, creation_date,mail_verified)
VALUES ('admin admin','admin@test.com', '$2a$10$d13yG8BIXEprI.t8AlJXPuhvGVZpfdlUGtljIDA22zIz836d9v4v2',2, NOW(),1);

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);

-- -------------------------------------------- User Points Configuration -----------------------------------------
INSERT INTO points_settings VALUES ('LIKE_COMMENT',5);
INSERT INTO points_settings VALUES ('LIKE_POST',10);

-- ----------------------------Insert a Countries data-------------------

INSERT INTO countries (country_name) VALUES
('Afghanistan'),
('Albania'),
('Algeria'),
('Andorra'),
('Angola'),
('Antigua and Barbuda'),
('Argentina'),
('Armenia'),
('Australia'),
('Austria'),
('Azerbaijan'),
('Bahamas'),
('Bahrain'),
('Bangladesh'),
('Barbados'),
('Belarus'),
('Belgium'),
('Belize'),
('Benin'),
('Bhutan'),
('Bolivia'),
('Bosnia and Herzegovina'),
('Botswana'),
('Brazil'),
('Brunei'),
('Bulgaria'),
('Burkina Faso'),
('Burundi'),
('Cabo Verde'),
('Cambodia'),
('Cameroon'),
('Canada'),
('Central African Republic'),
('Chad'),
('Chile'),
('China'),
('Colombia'),
('Comoros'),
('Congo'),
('Costa Rica'),
('Croatia'),
('Cuba'),
('Cyprus'),
('Czech Republic'),
('Denmark'),
('Djibouti'),
('Dominica'),
('Dominican Republic'),
('East Timor'),
('Ecuador'),
('Egypt'),
('El Salvador'),
('Equatorial Guinea'),
('Eritrea'),
('Estonia'),
('Eswatini'),
('Ethiopia'),
('Fiji'),
('Finland'),
('France'),
('Gabon'),
('Gambia'),
('Georgia'),
('Germany'),
('Ghana'),
('Greece'),
('Grenada'),
('Guatemala'),
('Guinea'),
('Guinea-Bissau'),
('Guyana'),
('Haiti'),
('Honduras'),
('Hungary'),
('Iceland'),
('India'),
('Indonesia'),
('Iran'),
('Iraq'),
('Ireland'),
('Israel'),
('Italy'),
('Jamaica'),
('Japan'),
('Jordan'),
('Kazakhstan'),
('Kenya'),
('Kiribati'),
('Kuwait'),
('Kyrgyzstan'),
('Laos'),
('Latvia'),
('Lebanon'),
('Lesotho'),
('Liberia'),
('Libya'),
('Liechtenstein'),
('Lithuania'),
('Luxembourg'),
('Madagascar'),
('Malawi'),
('Malaysia'),
('Maldives'),
('Mali'),
('Malta'),
('Marshall Islands'),
('Mauritania'),
('Mauritius'),
('Mexico'),
('Micronesia'),
('Moldova'),
('Monaco'),
('Mongolia'),
('Montenegro'),
('Morocco'),
('Mozambique'),
('Myanmar'),
('Namibia'),
('Nauru'),
('Nepal'),
('Netherlands'),
('New Zealand'),
('Nicaragua'),
('Niger'),
('Nigeria'),
('North Korea'),
('North Macedonia'),
('Norway'),
('Oman'),
('Pakistan'),
('Palau'),
('Panama'),
('Papua New Guinea'),
('Paraguay'),
('Peru'),
('Philippines'),
('Poland'),
('Portugal'),
('Qatar'),
('Romania'),
('Russia'),
('Rwanda'),
('Saint Kitts and Nevis'),
('Saint Lucia'),
('Saint Vincent and the Grenadines'),
('Samoa'),
('San Marino'),
('Sao Tome and Principe'),
('Saudi Arabia'),
('Senegal'),
('Serbia'),
('Seychelles'),
('Sierra Leone'),
('Singapore'),
('Slovakia'),
('Slovenia'),
('Solomon Islands'),
('Somalia'),
('South Africa'),
('South Korea'),
('South Sudan'),
('Spain'),
('Sri Lanka'),
('Sudan'),
('Suriname'),
('Sweden'),
('Switzerland'),
('Syria'),
('Taiwan'),
('Tajikistan'),
('Tanzania'),
('Thailand'),
('Togo'),
('Tonga'),
('Trinidad and Tobago'),
('Tunisia'),
('Turkey'),
('Turkmenistan'),
('Tuvalu'),
('Uganda'),
('Ukraine'),
('United Arab Emirates'),
('United Kingdom'),
('United States'),
('Uruguay'),
('Uzbekistan'),
('Vanuatu'),
('Vatican City'),
('Venezuela'),
('Vietnam'),
('Yemen'),
('Zambia'),
('Zimbabwe');

-- Insert data into PhoneCode table with corresponding country_id
INSERT INTO phone_codes (phone_code, country_id) VALUES
('+93', (SELECT country_id FROM countries WHERE  country_name = 'Afghanistan')),
('+355', (SELECT country_id FROM countries WHERE  country_name = 'Albania')),
('+213', (SELECT country_id FROM countries WHERE  country_name = 'Algeria')),
('+376', (SELECT country_id FROM countries WHERE  country_name = 'Andorra')),
('+244', (SELECT country_id FROM countries WHERE  country_name = 'Angola')),
('+1-268', (SELECT country_id FROM countries WHERE  country_name = 'Antigua and Barbuda')),
('+54', (SELECT country_id FROM countries WHERE  country_name = 'Argentina')),
('+374', (SELECT country_id FROM countries WHERE  country_name = 'Armenia')),
('+61', (SELECT country_id FROM countries WHERE  country_name = 'Australia')),
('+43', (SELECT country_id FROM countries WHERE  country_name = 'Austria')),
('+994', (SELECT country_id FROM countries WHERE  country_name = 'Azerbaijan')),
('+1-242', (SELECT country_id FROM countries WHERE  country_name = 'Bahamas')),
('+973', (SELECT country_id FROM countries WHERE  country_name = 'Bahrain')),
('+880', (SELECT country_id FROM countries WHERE  country_name = 'Bangladesh')),
('+1-246', (SELECT country_id FROM countries WHERE  country_name = 'Barbados')),
('+375', (SELECT country_id FROM countries WHERE  country_name = 'Belarus')),
('+32', (SELECT country_id FROM countries WHERE  country_name = 'Belgium')),
('+501', (SELECT country_id FROM countries WHERE  country_name = 'Belize')),
('+229', (SELECT country_id FROM countries WHERE  country_name = 'Benin')),
('+975', (SELECT country_id FROM countries WHERE  country_name = 'Bhutan')),
('+591', (SELECT country_id FROM countries WHERE  country_name = 'Bolivia')),
('+387', (SELECT country_id FROM countries WHERE  country_name = 'Bosnia and Herzegovina')),
('+267', (SELECT country_id FROM countries WHERE  country_name = 'Botswana')),
('+55', (SELECT country_id FROM countries WHERE  country_name = 'Brazil')),
('+673', (SELECT country_id FROM countries WHERE  country_name = 'Brunei')),
('+359', (SELECT country_id FROM countries WHERE  country_name = 'Bulgaria')),
('+226', (SELECT country_id FROM countries WHERE  country_name = 'Burkina Faso')),
('+257', (SELECT country_id FROM countries WHERE  country_name = 'Burundi')),
('+238', (SELECT country_id FROM countries WHERE  country_name = 'Cabo Verde')),
('+855', (SELECT country_id FROM countries WHERE  country_name = 'Cambodia')),
('+237', (SELECT country_id FROM countries WHERE  country_name = 'Cameroon')),
('+1', (SELECT country_id FROM countries WHERE  country_name = 'Canada')),
('+236', (SELECT country_id FROM countries WHERE  country_name = 'Central African Republic')),
('+235', (SELECT country_id FROM countries WHERE  country_name = 'Chad')),
('+56', (SELECT country_id FROM countries WHERE  country_name = 'Chile')),
('+86', (SELECT country_id FROM countries WHERE  country_name = 'China')),
('+57', (SELECT country_id FROM countries WHERE  country_name = 'Colombia')),
('+269', (SELECT country_id FROM countries WHERE  country_name = 'Comoros')),
('+242', (SELECT country_id FROM countries WHERE  country_name = 'Congo')),
('+506', (SELECT country_id FROM countries WHERE  country_name = 'Costa Rica')),
('+385', (SELECT country_id FROM countries WHERE  country_name = 'Croatia')),
('+53', (SELECT country_id FROM countries WHERE  country_name = 'Cuba')),
('+357', (SELECT country_id FROM countries WHERE  country_name = 'Cyprus')),
('+420', (SELECT country_id FROM countries WHERE  country_name = 'Czech Republic')),
('+45', (SELECT country_id FROM countries WHERE  country_name = 'Denmark')),
('+253', (SELECT country_id FROM countries WHERE  country_name = 'Djibouti')),
('+1-767', (SELECT country_id FROM countries WHERE  country_name = 'Dominica')),
('+1-809', (SELECT country_id FROM countries WHERE  country_name = 'Dominican Republic')),
('+670', (SELECT country_id FROM countries WHERE  country_name = 'East Timor')),
('+593', (SELECT country_id FROM countries WHERE  country_name = 'Ecuador')),
('+20', (SELECT country_id FROM countries WHERE  country_name = 'Egypt')),
('+503', (SELECT country_id FROM countries WHERE  country_name = 'El Salvador')),
('+240', (SELECT country_id FROM countries WHERE  country_name = 'Equatorial Guinea')),
('+291', (SELECT country_id FROM countries WHERE  country_name = 'Eritrea')),
('+372', (SELECT country_id FROM countries WHERE  country_name = 'Estonia')),
('+268', (SELECT country_id FROM countries WHERE  country_name = 'Eswatini')),
('+251', (SELECT country_id FROM countries WHERE  country_name = 'Ethiopia')),
('+679', (SELECT country_id FROM countries WHERE  country_name = 'Fiji')),
('+358', (SELECT country_id FROM countries WHERE  country_name = 'Finland')),
('+33', (SELECT country_id FROM countries WHERE  country_name = 'France')),
('+241', (SELECT country_id FROM countries WHERE  country_name = 'Gabon')),
('+220', (SELECT country_id FROM countries WHERE  country_name = 'Gambia')),
('+995', (SELECT country_id FROM countries WHERE  country_name = 'Georgia')),
('+49', (SELECT country_id FROM countries WHERE  country_name = 'Germany')),
('+233', (SELECT country_id FROM countries WHERE  country_name = 'Ghana')),
('+30', (SELECT country_id FROM countries WHERE  country_name = 'Greece')),
('+1-473', (SELECT country_id FROM countries WHERE  country_name = 'Grenada')),
('+502', (SELECT country_id FROM countries WHERE  country_name = 'Guatemala')),
('+224', (SELECT country_id FROM countries WHERE  country_name = 'Guinea')),
('+245', (SELECT country_id FROM countries WHERE  country_name = 'Guinea-Bissau')),
('+592', (SELECT country_id FROM countries WHERE  country_name = 'Guyana')),
('+509', (SELECT country_id FROM countries WHERE  country_name = 'Haiti')),
('+504', (SELECT country_id FROM countries WHERE  country_name = 'Honduras')),
('+36', (SELECT country_id FROM countries WHERE  country_name = 'Hungary')),
('+354', (SELECT country_id FROM countries WHERE  country_name = 'Iceland')),
('+91', (SELECT country_id FROM countries WHERE  country_name = 'India')),
('+62', (SELECT country_id FROM countries WHERE  country_name = 'Indonesia')),
('+98', (SELECT country_id FROM countries WHERE  country_name = 'Iran')),
('+964', (SELECT country_id FROM countries WHERE  country_name = 'Iraq')),
('+353', (SELECT country_id FROM countries WHERE  country_name = 'Ireland')),
('+972', (SELECT country_id FROM countries WHERE  country_name = 'Israel')),
('+39', (SELECT country_id FROM countries WHERE  country_name = 'Italy')),
('+1-876', (SELECT country_id FROM countries WHERE  country_name = 'Jamaica')),
('+81', (SELECT country_id FROM countries WHERE  country_name = 'Japan')),
('+962', (SELECT country_id FROM countries WHERE  country_name = 'Jordan')),
('+7', (SELECT country_id FROM countries WHERE  country_name = 'Kazakhstan')),
('+254', (SELECT country_id FROM countries WHERE  country_name = 'Kenya')),
('+686', (SELECT country_id FROM countries WHERE  country_name = 'Kiribati')),
('+965', (SELECT country_id FROM countries WHERE  country_name = 'Kuwait')),
('+996', (SELECT country_id FROM countries WHERE  country_name = 'Kyrgyzstan')),
('+856', (SELECT country_id FROM countries WHERE  country_name = 'Laos')),
('+371', (SELECT country_id FROM countries WHERE  country_name = 'Latvia')),
('+961', (SELECT country_id FROM countries WHERE  country_name = 'Lebanon')),
('+266', (SELECT country_id FROM countries WHERE  country_name = 'Lesotho')),
('+231', (SELECT country_id FROM countries WHERE  country_name = 'Liberia')),
('+218', (SELECT country_id FROM countries WHERE  country_name = 'Libya')),
('+423', (SELECT country_id FROM countries WHERE  country_name = 'Liechtenstein')),
('+370', (SELECT country_id FROM countries WHERE  country_name = 'Lithuania')),
('+352', (SELECT country_id FROM countries WHERE  country_name = 'Luxembourg')),
('+261', (SELECT country_id FROM countries WHERE  country_name = 'Madagascar')),
('+265', (SELECT country_id FROM countries WHERE  country_name = 'Malawi')),
('+60', (SELECT country_id FROM countries WHERE  country_name = 'Malaysia')),
('+960', (SELECT country_id FROM countries WHERE  country_name = 'Maldives')),
('+223', (SELECT country_id FROM countries WHERE  country_name = 'Mali')),
('+356', (SELECT country_id FROM countries WHERE  country_name = 'Malta')),
('+692', (SELECT country_id FROM countries WHERE  country_name = 'Marshall Islands')),
('+222', (SELECT country_id FROM countries WHERE  country_name = 'Mauritania')),
('+230', (SELECT country_id FROM countries WHERE  country_name = 'Mauritius')),
('+52', (SELECT country_id FROM countries WHERE  country_name = 'Mexico')),
('+691', (SELECT country_id FROM countries WHERE  country_name = 'Micronesia')),
('+373', (SELECT country_id FROM countries WHERE  country_name = 'Moldova')),
('+377', (SELECT country_id FROM countries WHERE  country_name = 'Monaco')),
('+976', (SELECT country_id FROM countries WHERE  country_name = 'Mongolia')),
('+382', (SELECT country_id FROM countries WHERE  country_name = 'Montenegro')),
('+212', (SELECT country_id FROM countries WHERE  country_name = 'Morocco')),
('+258', (SELECT country_id FROM countries WHERE  country_name = 'Mozambique')),
('+95', (SELECT country_id FROM countries WHERE  country_name = 'Myanmar')),
('+264', (SELECT country_id FROM countries WHERE  country_name = 'Namibia')),
('+674', (SELECT country_id FROM countries WHERE  country_name = 'Nauru')),
('+977', (SELECT country_id FROM countries WHERE  country_name = 'Nepal')),
('+31', (SELECT country_id FROM countries WHERE  country_name = 'Netherlands')),
('+64', (SELECT country_id FROM countries WHERE  country_name = 'New Zealand')),
('+505', (SELECT country_id FROM countries WHERE  country_name = 'Nicaragua')),
('+227', (SELECT country_id FROM countries WHERE  country_name = 'Niger')),
('+234', (SELECT country_id FROM countries WHERE  country_name = 'Nigeria')),
('+850', (SELECT country_id FROM countries WHERE  country_name = 'North Korea')),
('+389', (SELECT country_id FROM countries WHERE  country_name = 'North Macedonia')),
('+47', (SELECT country_id FROM countries WHERE  country_name = 'Norway')),
('+968', (SELECT country_id FROM countries WHERE  country_name = 'Oman')),
('+92', (SELECT country_id FROM countries WHERE  country_name = 'Pakistan')),
('+680', (SELECT country_id FROM countries WHERE  country_name = 'Palau')),
('+507', (SELECT country_id FROM countries WHERE  country_name = 'Panama')),
('+675', (SELECT country_id FROM countries WHERE  country_name = 'Papua New Guinea')),
('+595', (SELECT country_id FROM countries WHERE  country_name = 'Paraguay')),
('+51', (SELECT country_id FROM countries WHERE  country_name = 'Peru')),
('+63', (SELECT country_id FROM countries WHERE  country_name = 'Philippines')),
('+48', (SELECT country_id FROM countries WHERE  country_name = 'Poland')),
('+351', (SELECT country_id FROM countries WHERE  country_name = 'Portugal')),
('+974', (SELECT country_id FROM countries WHERE  country_name = 'Qatar')),
('+40', (SELECT country_id FROM countries WHERE  country_name = 'Romania')),
('+7', (SELECT country_id FROM countries WHERE  country_name = 'Russia')),
('+250', (SELECT country_id FROM countries WHERE  country_name = 'Rwanda')),
('+1-869', (SELECT country_id FROM countries WHERE  country_name = 'Saint Kitts and Nevis')),
('+1-758', (SELECT country_id FROM countries WHERE  country_name = 'Saint Lucia')),
('+1-784', (SELECT country_id FROM countries WHERE  country_name = 'Saint Vincent and the Grenadines')),
('+685', (SELECT country_id FROM countries WHERE  country_name = 'Samoa')),
('+378', (SELECT country_id FROM countries WHERE  country_name = 'San Marino')),
('+239', (SELECT country_id FROM countries WHERE  country_name = 'Sao Tome and Principe')),
('+966', (SELECT country_id FROM countries WHERE  country_name = 'Saudi Arabia')),
('+221', (SELECT country_id FROM countries WHERE  country_name = 'Senegal')),
('+381', (SELECT country_id FROM countries WHERE  country_name = 'Serbia')),
('+248', (SELECT country_id FROM countries WHERE  country_name = 'Seychelles')),
('+232', (SELECT country_id FROM countries WHERE  country_name = 'Sierra Leone')),
('+65', (SELECT country_id FROM countries WHERE  country_name = 'Singapore')),
('+421', (SELECT country_id FROM countries WHERE  country_name = 'Slovakia')),
('+386', (SELECT country_id FROM countries WHERE  country_name = 'Slovenia')),
('+677', (SELECT country_id FROM countries WHERE  country_name = 'Solomon Islands')),
('+252', (SELECT country_id FROM countries WHERE  country_name = 'Somalia')),
('+27', (SELECT country_id FROM countries WHERE  country_name = 'South Africa')),
('+82', (SELECT country_id FROM countries WHERE  country_name = 'South Korea')),
('+211', (SELECT country_id FROM countries WHERE  country_name = 'South Sudan')),
('+34', (SELECT country_id FROM countries WHERE  country_name = 'Spain')),
('+94', (SELECT country_id FROM countries WHERE  country_name = 'Sri Lanka')),
('+249', (SELECT country_id FROM countries WHERE  country_name = 'Sudan')),
('+597', (SELECT country_id FROM countries WHERE  country_name = 'Suriname')),
('+46', (SELECT country_id FROM countries WHERE  country_name = 'Sweden')),
('+41', (SELECT country_id FROM countries WHERE  country_name = 'Switzerland')),
('+963', (SELECT country_id FROM countries WHERE  country_name = 'Syria')),
('+886', (SELECT country_id FROM countries WHERE  country_name = 'Taiwan')),
('+992', (SELECT country_id FROM countries WHERE  country_name = 'Tajikistan')),
('+255', (SELECT country_id FROM countries WHERE  country_name = 'Tanzania')),
('+66', (SELECT country_id FROM countries WHERE  country_name = 'Thailand')),
('+228', (SELECT country_id FROM countries WHERE  country_name = 'Togo')),
('+676', (SELECT country_id FROM countries WHERE  country_name = 'Tonga')),
('+1-868', (SELECT country_id FROM countries WHERE  country_name = 'Trinidad and Tobago')),
('+216', (SELECT country_id FROM countries WHERE  country_name = 'Tunisia')),
('+90', (SELECT country_id FROM countries WHERE  country_name = 'Turkey')),
('+993', (SELECT country_id FROM countries WHERE  country_name = 'Turkmenistan')),
('+688', (SELECT country_id FROM countries WHERE  country_name = 'Tuvalu')),
('+256', (SELECT country_id FROM countries WHERE  country_name = 'Uganda')),
('+380', (SELECT country_id FROM countries WHERE  country_name = 'Ukraine')),
('+971', (SELECT country_id FROM countries WHERE  country_name = 'United Arab Emirates')),
('+44', (SELECT country_id FROM countries WHERE  country_name = 'United Kingdom')),
('+1', (SELECT country_id FROM countries WHERE  country_name = 'United States')),
('+598', (SELECT country_id FROM countries WHERE  country_name = 'Uruguay')),
('+998', (SELECT country_id FROM countries WHERE  country_name = 'Uzbekistan')),
('+678', (SELECT country_id FROM countries WHERE  country_name = 'Vanuatu')),
('+39', (SELECT country_id FROM countries WHERE  country_name = 'Vatican City')),
('+58', (SELECT country_id FROM countries WHERE  country_name = 'Venezuela')),
('+84', (SELECT country_id FROM countries WHERE  country_name = 'Vietnam')),
('+967', (SELECT country_id FROM countries WHERE  country_name = 'Yemen')),
('+260', (SELECT country_id FROM countries WHERE  country_name = 'Zambia')),
('+263', (SELECT country_id FROM countries WHERE  country_name = 'Zimbabwe'));

COMMIT;