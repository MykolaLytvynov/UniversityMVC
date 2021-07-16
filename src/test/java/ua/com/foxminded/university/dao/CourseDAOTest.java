package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.configuration.ApplicationConfig;
import ua.com.foxminded.university.dao.mapper.CourseMapper;
import ua.com.foxminded.university.dao.mapper.FacultyMapper;
import ua.com.foxminded.university.dao.mapper.GroupMapper;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.entities.Group;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CourseDAOTest {
    private ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private CourseDAO courseDAO = new CourseDAO(jdbcTemplate);

    public static final String DELETE_TABLES = "DROP TABLE IF EXISTS faculties, courses, groups";
    public static final String CREATE_TABLE_COURSES = "CREATE TABLE courses (id SERIAL PRIMARY KEY,\n" +
            "    nummerCourse int NOT NULL,\n" +
            "    facultyId int)";
    public static final String CREATE_TABLE_GROUPS = "CREATE TABLE groups (id SERIAL PRIMARY KEY,\n" +
            "    nummerGroup int NOT NULL,\n" +
            "    courseId int,\n" +
            "    FOREIGN KEY (courseId) REFERENCES courses (id) ON DELETE CASCADE)";
    private static final String CREATE_TABLE_FACULTIES = "CREATE TABLE faculties (id SERIAL PRIMARY KEY,\n" +
            " name CHARACTER (35) NOT NULL,\n" +
            " description CHARACTER (100) NOT NULL)";


    public CourseDAOTest() {
        jdbcTemplate.setDataSource(context.getBean("dataSourceForTest", DataSource.class));
    }



    @BeforeEach
    void addTables () {
        jdbcTemplate.execute(DELETE_TABLES);
        jdbcTemplate.execute(CREATE_TABLE_FACULTIES);
        jdbcTemplate.execute(CREATE_TABLE_COURSES);
        jdbcTemplate.execute(CREATE_TABLE_GROUPS);
    }

    @Test
    @DisplayName("Save course in Bd")
    void saveShouldSaveCourseInBd() {
        Course expected = new Course(1);
        expected.setId(1);

        courseDAO.save(new Course(1));

        Course result = jdbcTemplate.query("SELECT * FROM courses WHERE id = 1", new CourseMapper())
                .stream().findAny().orElse(null);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Save Null instead of faculty in Bd")
    void saveShouldReturnExceptionWhenEnterNull() {
        assertThrows(RuntimeException.class, () -> courseDAO.save(null));
    }

    @Test
    @DisplayName("Find by existing id")
    void findByIdShouldReturnCourseWhenEnterExistingCourse () {
        Course expected = new Course(3);
        expected.setId(3);
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('3', '3')");
        jdbcTemplate.update("INSERT INTO groups (id, nummerGroup) VALUES ('1', '1')");
        jdbcTemplate.update("UPDATE groups SET courseId = 3 WHERE id = 1");
        List<Group> groupList = new ArrayList<>();
        Group group = new Group(1);
        group.setId(1);
        groupList.add(group);
        expected.setGroups(groupList);

        Course result = courseDAO.findById(3).orElse(null);

        assertEquals(result, expected);
    }

    @Test
    @DisplayName("Find by non-existing id")
    void findByIdShouldReturnNullWhenEnterNonExistingCourse() {
        Course expectedResult = null;
        Course realResult = jdbcTemplate.query("SELECT * FROM courses WHERE id = 33", new CourseMapper())
                .stream().findAny().orElse(null);

        Course actualResult = courseDAO.findById(33).orElse(null);

        assertEquals(expectedResult, actualResult);
        assertEquals(realResult, actualResult);
    }


    @Test
    @DisplayName("Exists by id")
    void existsByIdShouldTrueWhenEnterExistsCourse() {
        boolean expectedResult = true;
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('3', '3')");
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM courses WHERE id = 3", Integer.class);
        boolean realResult = count > 0;

        boolean actualResult = courseDAO.existsById(3);

        assertEquals(expectedResult, actualResult);
        assertEquals(realResult, actualResult);
    }


    @Test
    @DisplayName("Does not Exists by id")
    void existsByIdShouldReturnFalseWhenEnterDoesNotExistsCourse() {
        boolean expectedResult = false;
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM courses WHERE id = 33", Integer.class);
        boolean realResult = count > 0;

        boolean actualResult = courseDAO.existsById(33);

        assertEquals(expectedResult, actualResult);
        assertEquals(realResult, actualResult);
    }


    @Test
    @DisplayName("Find All courses")
    void findAllShouldAllCourses() {

        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('1', '1')");
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('2', '2')");
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('3', '3')");
        jdbcTemplate.update("INSERT INTO groups (id, nummerGroup) VALUES ('1', '1')");
        jdbcTemplate.update("UPDATE groups SET courseId = 3 WHERE id = 1");
        List<Course> expected = jdbcTemplate.query("SELECT * FROM courses", new CourseMapper());
            expected.stream()
                    .peek(course -> course.setGroups(jdbcTemplate.query("SELECT * FROM groups WHERE courseId = ?", new Object[]{course.getId()}, new GroupMapper())))
                    .collect(Collectors.toList());

            List<Course> result = courseDAO.findAll();

            assertEquals(expected, result);
    }

    @Test
    @DisplayName("Count courses")
    void countShouldReturnCountCourses() {
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('1', '1')");
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('2', '2')");
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('3', '3')");
        int expected = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM courses", Integer.class);

        int result = (int) courseDAO.count();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete one course by id")
    void deleteByIdShouldDeleteOneCourseById() {
        Course expected = null;
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('3', '3')");

        courseDAO.deleteById(3);

        Course result = jdbcTemplate.query("SELECT * FROM courses WHERE id = 3", new CourseMapper())
                .stream().findAny().orElse(null);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete one course when specifying a course")
    void deleteShouldDeleteOneCourseWhenSpecifyingCourse() {
        Course expected = null;
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('3', '3')");
        Course course = new Course(3);
        course.setId(3);

        courseDAO.delete(course);

        Course result = jdbcTemplate.query("SELECT * FROM courses WHERE id = 3", new CourseMapper())
                .stream().findAny().orElse(null);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete all courses")
    void deleteAllShouldDeleteAllCourses() {
        int expected = 0;
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('1', '1')");
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('2', '2')");
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('3', '3')");

        courseDAO.deleteAll();

        int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM courses", Integer.class);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Add course to faculty")
    void addCourseToFacultyShouldAddCourseToFaculty() {
        Course expected = new Course(2);
        expected.setId(2);
        Course course = new Course(3);
        course.setId(3);
        Faculty faculty = new Faculty("Faculty5", "F5");
        faculty.setId(5);
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('5', 'Faculty5', 'F5')");
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse, facultyId) VALUES ('2', '2', '5')");

        courseDAO.addCourseToFaculty(course, faculty);

        Course result = jdbcTemplate.query("SELECT * FROM courses WHERE facultyId = 5", new CourseMapper())
                .stream().findAny().orElse(null);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Get all groups one course")
    void getGroupsOneCourseShouldGetAllGroupsOneCourse() {
        List<Group> expected = new ArrayList<>();
        Group group1 = new Group(1);
        group1.setId(1);
        Group group2 = new Group(2);
        group2.setId(2);
        expected.add(group1);
        expected.add(group2);
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('5', '5')");
        jdbcTemplate.update("INSERT INTO groups (id, nummerGroup, courseId) VALUES ('1', '1', '5')");
        jdbcTemplate.update("INSERT INTO groups (id, nummerGroup, courseId) VALUES ('2', '2', '5')");


        List<Group> result = courseDAO.getGroupsOneCourse(5);

        assertEquals(expected, result);
    }
}