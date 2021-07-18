package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mapper.StudentMapper;
import ua.com.foxminded.university.entities.person.Student;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudentDAO implements CrudOperations<Student, Integer> {
    private JdbcTemplate jdbcTemplate;
    private StudentMapper studentMapper;

    public static final String SAVE_STUDENT = "INSERT INTO students(name, lastName) VALUES (?, ?)";
    public static final String FIND_BY_ID = "SELECT * FROM students WHERE id = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM students WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM students";
    public static final String COUNT = "SELECT COUNT(*) FROM students";
    public static final String DELETE_GROUP = "DELETE FROM students WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM students";

    @Override
    public Student save(Student student) {
        log.debug("save('{}') called", student);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SAVE_STUDENT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getName());
            ps.setString(2, student.getLastName());
            return ps;
        }, keyHolder);

        Integer id = ofNullable(keyHolder.getKeys())
                .map(map -> (Integer) map.get("id"))
                .orElseThrow(() -> new RuntimeException(format("Query '%s' didn't returned id!", SAVE_STUDENT)));
        student.setId(id);

        log.debug("save(Student) was success. Returned '{}'", student);
        return student;
    }

    @Override
    public Optional<Student> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Student result = jdbcTemplate.queryForObject(FIND_BY_ID, studentMapper, id);
        log.debug("method findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }

    @Override
    public boolean existsById(Integer id) {
        log.debug("existsById('{}') called", id);
        Integer count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        boolean result = count != null && count > 0;
        log.debug("existsById('{}') returned '{}'", id, result);
        return result;
    }

    @Override
    public List<Student> findAll() {
        log.debug("findAll() called");
        List<Student> result = jdbcTemplate.query(FIND_ALL, studentMapper);
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    @Override
    public long count() {
        log.debug("count() called");
        Integer result = jdbcTemplate.queryForObject(COUNT, Integer.class);
        log.debug("count() returned '{}'", result);
        return ofNullable(result)
                .orElseThrow(() -> new RuntimeException(format("Query '%s' returned null!", COUNT)));
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById({}) called", id);
        jdbcTemplate.update(DELETE_GROUP, id);
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Student student) {
        log.debug("delete({}) called", student);
        jdbcTemplate.update(DELETE_GROUP, student.getId());
        log.debug("deleteById('{}') was success", student);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        jdbcTemplate.update(DELETE_ALL);
        log.debug("deleteAll() was success");

    }
}
