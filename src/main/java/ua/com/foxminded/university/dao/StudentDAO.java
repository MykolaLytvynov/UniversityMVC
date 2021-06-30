package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mapper.GroupMapper;
import ua.com.foxminded.university.dao.mapper.StudentMapper;
import ua.com.foxminded.university.entities.person.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentDAO implements CrudOperations<Student, Integer>{
    private JdbcTemplate jdbcTemplate;

    public static final String SAVE_STUDENT = "INSERT INTO students(name, lastName) VALUES (?, ?)";
    public static final String FIND_BY_ID = "SELECT * FROM students WHERE id = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM students WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM students";
    public static final String COUNT = "SELECT COUNT(*) FROM students";
    public static final String DELETE_GROUP = "DELETE FROM students WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM students";



    @Autowired
    public StudentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Student save(Student student) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_STUDENT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, student.getName());
                ps.setString(2, student.getLastName());
                return ps;
            }
        }, keyHolder);
        student.setId((int)keyHolder.getKeys().get("id"));

        return student;
    }

    @Override
    public Student findById(Integer id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, new StudentMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean existsById(Integer id) {
        int count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        return count > 0;
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL, new StudentMapper());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
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
