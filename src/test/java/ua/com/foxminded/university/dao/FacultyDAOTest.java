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
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FacultyDAOTest {
    private ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private FacultyDAO facultyDAO = new FacultyDAO(jdbcTemplate);

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS faculties, courses, groups";
    private static final String CREATE_TABLE_FACULTIES = "CREATE TABLE faculties (id SERIAL PRIMARY KEY,\n" +
            " name CHARACTER (35) NOT NULL,\n" +
            " description CHARACTER (100) NOT NULL)";
    private static final String CREATE_TABLE_COURSES = "CREATE TABLE courses (id SERIAL PRIMARY KEY,\n" +
            "    nummerCourse int NOT NULL,\n" +
            "    facultyId int,\n" +
            "    FOREIGN KEY (facultyId) REFERENCES faculties (id) ON DELETE CASCADE)";


    public FacultyDAOTest() {
        jdbcTemplate.setDataSource(context.getBean("dataSourceForTest", DataSource.class));
    }

    @BeforeEach
    void addTables() {
        jdbcTemplate.execute(DELETE_TABLE);
        jdbcTemplate.execute(CREATE_TABLE_FACULTIES);
        jdbcTemplate.execute(CREATE_TABLE_COURSES);
    }

    @Test
    @DisplayName("Save faculty in Bd")
    void saveShouldSaveFacultyInBd() {
        Faculty expected = new Faculty("faculty1", "F1");
        expected.setId(1);

        facultyDAO.save(new Faculty("faculty1", "F1"));

        Faculty result = jdbcTemplate.query("SELECT * FROM faculties WHERE id = 1", new FacultyMapper())
                .stream().findAny().orElse(null);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Save Null instead of faculty in Bd")
    void saveShouldReturnExceptionWhenEnterNull() {
        assertThrows(RuntimeException.class, () -> facultyDAO.save(null));
    }

    @Test
    @DisplayName("Find by existing id")
    void findByIdShouldReturnFacultyWhenEnterExistingFaculty() {
        Faculty expected = new Faculty("Faculty3", "F3");
        expected.setId(3);
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('3', 'Faculty3', 'F3')");
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('1', '1')");
        jdbcTemplate.update("UPDATE courses SET facultyId = 3 WHERE id = 1");
        List<Course> courseList = new ArrayList<>();
        Course course = new Course(1);
        course.setId(1);
        courseList.add(course);
        expected.setCourses(courseList);

        Faculty result = facultyDAO.findById(3).orElse(null);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Find by non-existing id")
    void findByIdShouldReturnNullWhenEnterNonExistingFaculty() {
        Faculty expectedResult = null;
        Faculty realResult = jdbcTemplate.query("SELECT * FROM faculties WHERE id = 33", new FacultyMapper())
                .stream().findAny().orElse(null);

        Faculty actualResult = facultyDAO.findById(33).orElse(null);

        assertEquals(expectedResult, actualResult);
        assertEquals(realResult, actualResult);
    }

    @Test
    @DisplayName("Exists by id")
    void existsByIdShouldTrueWhenEnterExistsFaculty() {
        boolean expectedResult = true;
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('3', 'Faculty3', 'F3')");
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM faculties WHERE id = 3", Integer.class);
        boolean realResult = count > 0;

        boolean actualResult = facultyDAO.existsById(3);

        assertEquals(expectedResult, actualResult);
        assertEquals(realResult, actualResult);
    }

    @Test
    @DisplayName("Does not Exists by id")
    void existsByIdShouldReturnFalseWhenEnterDoesNotExistsFaculty() {
        boolean expectedResult = false;
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM faculties WHERE id = 32", Integer.class);
        boolean realResult = count > 0;

        boolean actualResult = facultyDAO.existsById(32);

        assertEquals(expectedResult, actualResult);
        assertEquals(realResult, actualResult);
    }


    @Test
    @DisplayName("Find All faculties")
    void findAllShouldReturnAllFaculties() {
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('3', 'Faculty3', 'F3')");
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('4', 'Faculty4', 'F4')");
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('5', 'Faculty5', 'F5')");
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse) VALUES ('1', '1')");
        jdbcTemplate.update("UPDATE courses SET facultyId = 5 WHERE id = 1");
        List<Faculty> expected = jdbcTemplate.query("SELECT * FROM faculties", new FacultyMapper());
        expected.stream()
                .peek(faculty -> faculty.setCourses(jdbcTemplate.query("SELECT * FROM courses WHERE facultyId = ?",
                        new Object[]{faculty.getId()},
                        new CourseMapper())))
                .collect(Collectors.toList());

        List<Faculty> result = facultyDAO.findAll();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Count faculties")
    void countShouldReturnCountFaculties() {
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('3', 'Faculty3', 'F3')");
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('4', 'Faculty4', 'F4')");
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('5', 'Faculty5', 'F5')");
        int expected = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM faculties", Integer.class);

        int result = (int) facultyDAO.count();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete one faculty by id")
    void deleteByIdShouldDeleteOneFacultyById() {
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('3', 'Faculty3', 'F3')");
        Faculty expected = null;

        facultyDAO.deleteById(3);

        Faculty result = jdbcTemplate.query("SELECT * FROM faculties WHERE id = 3", new FacultyMapper())
                .stream().findAny().orElse(null);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete one faculty when specifying a faculty")
    void deleteShouldDeleteOneFacultyWhenSpecifyingFaculty() {
        Faculty expected = null;
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('3', 'Faculty3', 'F3')");
        Faculty faculty = new Faculty("Faculty3", "F3");
        faculty.setId(3);

        facultyDAO.delete(faculty);

        Faculty result = jdbcTemplate.query("SELECT * FROM faculties WHERE id = 3", new FacultyMapper())
                .stream().findAny().orElse(null);
        assertEquals(expected, result);

    }

    @Test
    @DisplayName("Delete all faculties")
    void deleteAllShouldDEleteAllFaculties() {
        int expected = 0;
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('3', 'Faculty3', 'F3')");
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('4', 'Faculty4', 'F4')");
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('5', 'Faculty5', 'F5')");

        facultyDAO.deleteAll();

        int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM faculties", Integer.class);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Get all courses one faculty")
    void getCoursesOneFaculty() {
        List<Course> expected = new ArrayList<>();
        Course course1 = new Course(1);
        course1.setId(1);
        Course course2 = new Course(2);
        course2.setId(2);
        expected.add(course1);
        expected.add(course2);
        jdbcTemplate.update("INSERT INTO faculties (id, name, description) VALUES ('3', 'Faculty3', 'F3')");
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse, facultyId) VALUES (1, 1, 3)");
        jdbcTemplate.update("INSERT INTO courses (id, nummerCourse, facultyId) VALUES (2, 2, 3)");

        List<Course> result = facultyDAO.getCoursesOneFaculty(3);

        assertEquals(expected, result);
    }
}