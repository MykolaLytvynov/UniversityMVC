package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.configuration.ApplicationConfig;
import ua.com.foxminded.university.dao.mapper.ClassRoomMapper;
import ua.com.foxminded.university.entities.ClassRoom;


import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClassRoomDAOTest {
    private ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);


    private JdbcTemplate jdbcTemplate = new JdbcTemplate();
//    private JdbcTemplate jdbcTemplate;


    private ClassRoomDAO classRoomDAO = new ClassRoomDAO(jdbcTemplate);

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS classRoom";
    private static final String CREATE_TABLE = "CREATE TABLE classRoom (id SERIAL PRIMARY KEY, name CHARACTER(25) NOT NULL UNIQUE, description CHARACTER (100))";


    public ClassRoomDAOTest() {
        jdbcTemplate.setDataSource((DataSource) context.getBean("dataSourceForTest"));
//        jdbcTemplate = context.getBean("jdbcTemplateForTest", JdbcTemplate.class);
    }


    @BeforeEach
    void addTables() {
        jdbcTemplate.execute(DELETE_TABLE);
        jdbcTemplate.execute(CREATE_TABLE);
    }


    @Test
    @DisplayName("Save class room in Bd")
    void saveShouldSaveClassRoomInBd() {
        ClassRoom expected = new ClassRoom("one", "oneDescription");
        expected.setId(1);

        classRoomDAO.save(new ClassRoom("one", "oneDescription"));

        ClassRoom result = jdbcTemplate.query("SELECT * FROM classRoom WHERE id = 1", new ClassRoomMapper())
                .stream().findAny().orElse(null);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Save Null instead of class room in Bd")
    void saveShouldReturnExceptionWhenEnterNull() {
        assertThrows(RuntimeException.class, () -> classRoomDAO.save(null));
    }

    @Test
    @DisplayName("Find by existing id")
    void findByIdShouldReturnClassRoomWhenEnterExistingClassRoom() {
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (12, 'ClassRoom1', 'AboutClassRoom1')");
        ClassRoom expected = jdbcTemplate.query("SELECT * FROM classRoom WHERE id = 12", new ClassRoomMapper())
                .stream().findAny().orElse(null);

        ClassRoom result = classRoomDAO.findById(12).orElse(null);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Find by non-existing id")
    void findByIdShouldReturnNullWhenEnterNonExistingClassRoom() {
        ClassRoom expectedResult = null;
        ClassRoom realResult = jdbcTemplate.query("SELECT * FROM classRoom WHERE id = 123", new ClassRoomMapper())
                .stream().findAny().orElse(null);

        ClassRoom actualResult = classRoomDAO.findById(123).orElse(null);

        assertEquals(expectedResult, actualResult);
        assertEquals(realResult, actualResult);
    }


    @Test
    @DisplayName("Exists by id")
    void existsByIdShouldReturnTrueWhenEnterExistsClassRoom() {
        boolean expectedResult = true;
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES ('3', 'Class3', 'Class33')");
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM classRoom WHERE id = 3", Integer.class);
        boolean realResult = count > 0;

        boolean actualResult = classRoomDAO.existsById(3);

        assertEquals(expectedResult, actualResult);
        assertEquals(realResult, actualResult);
    }

    @Test
    @DisplayName("Does not Exists by id")
    void existsByIdShouldReturnFalseWhenEnterDoesNotExistsClassRoom() {
        boolean expectedResult = false;
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM classRoom WHERE id = 32", Integer.class);
        boolean realResult = count > 0;

        boolean actualResult = classRoomDAO.existsById(32);

        assertEquals(expectedResult, actualResult);
        assertEquals(realResult, actualResult);
    }

    @Test
    @DisplayName("Find All Class rooms")
    void findAllShouldReturnAllClassRooms() {
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (1, 'ClassRoom1', 'AboutClassRoom1')");
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (2, 'ClassRoom2', 'AboutClassRoom2')");
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (3, 'ClassRoom3', 'AboutClassRoom3')");
        List<ClassRoom> expected = jdbcTemplate.query("SELECT * FROM classRoom", new ClassRoomMapper());

        List<ClassRoom> result = classRoomDAO.findAll();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Count Class rooms")
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
    @DisplayName("Delete one class room by id")
    void deleteByIdShouldDeleteOneClassRoomById() {
        jdbcTemplate.update("INSERT INTO classRoom (id, name, description) VALUES (3, 'ClassRoom3', 'AboutClassRoom3')");
        ClassRoom expected = null;

        classRoomDAO.deleteById(3);

        ClassRoom result = jdbcTemplate.query("SELECT * FROM classRoom WHERE id = 3", new ClassRoomMapper())
                .stream().findAny().orElse(null);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete one class room when specifying a class room")
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
    @DisplayName("Delete all class rooms")
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