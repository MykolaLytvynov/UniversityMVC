package ua.com.foxminded.university.dao;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.com.foxminded.university.entities.ClassRoom;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassRoomDAOTest {

    private final String driver = "org.h2.Driver";
    private final String urlDbForTest = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private final String userNameDbForTest = "sa";
    private final String password = "";
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private ClassRoomDAO classRoomDAO = new ClassRoomDAO(jdbcTemplate);


    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS classRoom";
    private static final String CREATE_TABLE = "CREATE TABLE classRoom (id SERIAL PRIMARY KEY, name CHARACTER(25) NOT NULL UNIQUE, description CHARACTER (100))";


    public ClassRoomDAOTest() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(driver);
        dataSource.setUrl(urlDbForTest);
        dataSource.setUsername(userNameDbForTest);
        dataSource.setPassword(password);

        jdbcTemplate.setDataSource(dataSource);
    }


    @BeforeEach
    void addTables() {
        jdbcTemplate.execute(DELETE_TABLE);
        jdbcTemplate.execute(CREATE_TABLE);
    }


    @Test
    @DisplayName("Save classroom in Bd")
    void saveShouldSaveClassRoomInBd() {
        ClassRoom expected = new ClassRoom("one", "oneDescription");
        expected.setId(1);

        classRoomDAO.save(new ClassRoom("one", "oneDescription"));

        ClassRoom result = jdbcTemplate.query("SELECT * FROM classRoom WHERE id = 1", new ClassRoomMapper())
                .stream().findAny().orElse(null);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Save Null instead of classroom in Bd")
    void saveShouldReturnExceptionWhenEnterNull() {
        assertThrows(RuntimeException.class, () -> classRoomDAO.save(null));
    }

    @Test
    @DisplayName("Find by existing id")
    void findByIdShouldReturnClassRoomWhenEnterExistingClassRoom() {
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (12, 'ClassRoom1', 'AboutClassRoom1')");
        ClassRoom expected = jdbcTemplate.query("SELECT * FROM classRoom WHERE id = 12", new ClassRoomMapper())
                .stream().findAny().orElse(null);

        ClassRoom result = classRoomDAO.findById(12);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Find by non-existing id")
    void findByIdShouldReturnNullWhenEnterNonExistingClassRoom() {
        ClassRoom expected = jdbcTemplate.query("SELECT * FROM classRoom WHERE id = 123", new ClassRoomMapper())
                .stream().findAny().orElse(null);

        ClassRoom result = classRoomDAO.findById(123);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Exists by id")
    void existsByIdShouldTrueWhenEnterExistsClassRoom() {
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (1, 'classroom1', 'about1')");
        ClassRoom classRoom = jdbcTemplate.query("SELECT * FROM classRoom WHERE id = 1", new ClassRoomMapper())
                .stream().findAny().orElse(null);
        boolean expected = true;
        if (classRoom == null) {
            expected = false;
        }

        boolean result = classRoomDAO.existsById(1);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Does not Exists by id")
    void existsByIdShouldTrueWhenEnterDoesNotExistsClassRoom() {
        ClassRoom classRoom = jdbcTemplate.query("SELECT * FROM classRoom WHERE id = 123", new ClassRoomMapper())
                .stream().findAny().orElse(null);
        boolean expected = true;
        if (classRoom == null) {
            expected = false;
        }

        boolean result = classRoomDAO.existsById(1);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Find All Classrooms")
    void findAllShouldReturnAllClassRooms() {
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (1, 'ClassRoom1', 'AboutClassRoom1')");
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (2, 'ClassRoom2', 'AboutClassRoom2')");
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (3, 'ClassRoom3', 'AboutClassRoom3')");
        List<ClassRoom> expected = jdbcTemplate.query("SELECT * FROM classRoom", new ClassRoomMapper());

        List<ClassRoom> result = classRoomDAO.findAll();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Count Classrooms")
    void countShouldReturnCountClassRooms() {
        int expectedResult = 3;
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (1, 'ClassRoom1', 'AboutClassRoom1')");
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (2, 'ClassRoom2', 'AboutClassRoom2')");
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (3, 'ClassRoom3', 'AboutClassRoom3')");
        int realResult = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM classRoom", Integer.class);

        int actualResult = (int) classRoomDAO.count();

        assertEquals(expectedResult, actualResult);
        assertEquals(realResult, actualResult);
    }

    @Test
    @DisplayName("Delete one classroom by id")
    void deleteByIdShouldDeleteOneClassRoomById() {
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (3, 'ClassRoom3', 'AboutClassRoom3')");
        ClassRoom expected = null;

        classRoomDAO.deleteById(3);

        ClassRoom result = jdbcTemplate.query("SELECT * FROM classRoom WHERE id = 3", new ClassRoomMapper())
                .stream().findAny().orElse(null);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete one classroom when specifying a class room")
    void deleteShouldDeleteOneClassRoomWhenSpecifyingClassRoom() {
        ClassRoom expected = null;
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (3, 'ClassRoom3', 'AboutClassRoom3')");
        ClassRoom classRoomForTest = new ClassRoom("ClassRoom3", "AboutClassRoom3");
        classRoomForTest.setId(3);

        classRoomDAO.delete(classRoomForTest);

        ClassRoom result = jdbcTemplate.query("SELECT * FROM classRoom WHERE id = 3", new ClassRoomMapper())
                .stream().findAny().orElse(null);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete all classrooms")
    void deleteAllShouldDeleteAllClassRooms() {
        int expected = 0;
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (1, 'ClassRoom1', 'AboutClassRoom1')");
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (2, 'ClassRoom2', 'AboutClassRoom2')");
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (3, 'ClassRoom3', 'AboutClassRoom3')");

        classRoomDAO.deleteAll();

        int result = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM classRoom", Integer.class);
        assertEquals(expected, result);
    }
}