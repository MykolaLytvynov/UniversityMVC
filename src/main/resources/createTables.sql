DROP TABLE IF EXISTS classRoom, faculties, courses, groups, students;

CREATE TABLE classRoom (
    id SERIAL PRIMARY KEY,
    name CHARACTER (25) NOT NULL UNIQUE,
    description CHARACTER (100)
);

CREATE TABLE faculties (
    id SERIAL PRIMARY KEY,
    name CHARACTER (35) NOT NULL,
    description CHARACTER (100) NOT NULL
);

CREATE TABLE courses (
    id SERIAL PRIMARY KEY,
    nummerCourse int NOT NULL,
    facultyId int,
    FOREIGN KEY (facultyId) REFERENCES faculties (id) ON DELETE CASCADE
);

CREATE TABLE groups (
    id SERIAL PRIMARY KEY,
    nummerGroup int NOT NULL,
    courseId int,
    FOREIGN KEY (courseId) REFERENCES courses (id) ON DELETE CASCADE
);

CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name CHARACTER (25) NOT NULL,
    lastName CHARACTER (35) NOT NULL,
    groupId int,
    FOREIGN KEY (groupId) REFERENCES groups (id) ON DELETE SET NULL
);

CREATE TABLE positions (
    id SERIAL PRIMARY KEY,
    name CHARACTER (35) NOT NULL UNIQUE
);

CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    name CHARACTER (25) NOT NULL,
    lastName CHARACTER (35) NOT NULL,
    positionId int,
    salary int,
    FOREIGN KEY (positionId) REFERENCES positions (id) ON DELETE CASCADE
);

CREATE TABLE subjects (
    id SERIAL PRIMARY KEY,
    name CHARACTER (35) NOT NULL,
    description CHARACTER (55) NOT NULL,
    amountLessons int NOT NULL,
    employeeId int,
    FOREIGN KEY (employeeId) REFERENCES employees (id) ON DELETE SET NULL
);

CREATE TABLE lessons (
    id SERIAL PRIMARY KEY,
    subjectId int NOT NULL,
    dateTime TIMESTAMP NOT NULL,
    duration int NOT NULL,
    classRoomId int NOT NULL,
    FOREIGN KEY (subjectId) REFERENCES subjects (id) ON DELETE SET NULL,
    FOREIGN KEY (classRoomId) REFERENCES classRoom (id) ON DELETE SET NULL
);

CREATE TABLE groupsLessons (
    idGroup int NOT NULL,
    idLesson int NOT NULL,
    FOREIGN KEY (idGroup) REFERENCES groups (id) ON DELETE CASCADE,
    FOREIGN KEY (idLesson) REFERENCES lessons (id) ON DELETE CASCADE
);