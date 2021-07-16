package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mapper.SubjectMapper;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.entities.person.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SubjectDAO implements CrudOperations<Subject, Integer>{
    private JdbcTemplate jdbcTemplate;

    public static final String SAVE_SUBJECT = "INSERT INTO subjects (name, description, amountLessons) VALUES (?, ?, ?)";
    public static final String FIND_BY_ID = "SELECT * FROM subjects WHERE id = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM subjects WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM subjects";
    public static final String COUNT = "SELECT COUNT(*) FROM subjects";
    public static final String DELETE_POSITION = "DELETE FROM subjects WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM subjects";
    public static final String ADD_TEACHER_TO_SUBJECT = "UPDATE subjects SET employeeId = ? WHERE id = ?";
    public static final String GET_ALL_SUBJECTS_ONE_TEACHER = "SELECT * FROM subjects WHERE employeeId = ?";


    @Autowired
    public SubjectDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Subject save(Subject subject) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_SUBJECT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, subject.getName());
                ps.setString(2, subject.getDescription());
                ps.setInt(3, subject.getAmountLessons());
                return ps;
            }
        }, keyHolder);
        subject.setId((int)keyHolder.getKeys().get("id"));

        return subject;
    }

    @Override
    public Optional<Subject> findById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, new SubjectMapper())
                .stream()
                .findAny()
                .orElse(null));

    }

    @Override
    public boolean existsById(Integer id) {
        int count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        return count > 0;
    }

    @Override
    public List<Subject> findAll() {
        return jdbcTemplate.query(FIND_ALL, new SubjectMapper());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update(DELETE_POSITION, id);
    }

    @Override
    public void delete(Subject subject) {
        jdbcTemplate.update(DELETE_POSITION, subject.getId());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }


    public void addSubjecctToTeacher (Subject subject, Employee employee) {
        jdbcTemplate.update(ADD_TEACHER_TO_SUBJECT, employee.getId(), subject.getId());
    }

    public List<Subject> getAllSubjectsOneTeacher(Integer teacherId) {
        return jdbcTemplate.query(GET_ALL_SUBJECTS_ONE_TEACHER, new Object[]{teacherId}, new SubjectMapper());
    }
}
