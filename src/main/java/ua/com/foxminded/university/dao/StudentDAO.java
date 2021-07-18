package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
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
        return student;
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, studentMapper, id));
    }

    @Override
    public boolean existsById(Integer id) {
        Integer count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL, studentMapper);
    }

    @Override
    public long count() {
        return ofNullable(jdbcTemplate.queryForObject(COUNT, Integer.class))
                .orElseThrow(() -> new RuntimeException(format("Query '%s' returned null!", COUNT)));
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update(DELETE_GROUP, id);
    }

    @Override
    public void delete(Student student) {
        jdbcTemplate.update(DELETE_GROUP, student.getId());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }
}
