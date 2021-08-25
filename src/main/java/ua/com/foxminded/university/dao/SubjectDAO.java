package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Component
@Slf4j
@RequiredArgsConstructor
public class SubjectDAO implements CrudOperations<Subject, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final SubjectMapper subjectMapper;

    public static final String SAVE_SUBJECT = "INSERT INTO subjects (name, description, amountLessons) VALUES (?, ?, ?)";
    public static final String FIND_BY_ID = "SELECT * FROM subjects WHERE id = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM subjects WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM subjects";
    public static final String COUNT = "SELECT COUNT(*) FROM subjects";
    public static final String DELETE_SUBJECT = "DELETE FROM subjects WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM subjects";
    public static final String GET_ALL_SUBJECTS_ONE_TEACHER = "SELECT * FROM subjects WHERE employeeId = ?";
    public static final String ADD_TEACHER_TO_SUBJECT = "UPDATE subjects SET employeeId = ? WHERE id = ?";
    public static final String UPDATE_SUBJECT = "UPDATE subjects SET name = ?, description = ?, employeeId = ?, amountLessons = ? WHERE id = ?";
    public static final String GET_SUBJECTS_WITHOUT_TEACHER = "SELECT * FROM subjects WHERE employeeId IS NULL";



    @Override
    public Subject save(Subject subject) {
        log.debug("save('{}') called", subject);
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
        Integer id = ofNullable(keyHolder.getKeys())
                .map(map -> (Integer) map.get("id"))
                .orElseThrow(() -> new RuntimeException(format("Query '%s' didn't returned it!", SAVE_SUBJECT)));

        subject.setId(id);
        log.debug("save(Subject) was success. Returned '{}'", subject);
        return subject;
    }

    @Override
    public Optional<Subject> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Subject result = jdbcTemplate.queryForObject(FIND_BY_ID, subjectMapper, id);
        log.debug("findById('{}') returned '{}'", id, result);
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
    public List<Subject> findAll() {
        log.debug("findAll() called");
        List<Subject> result = jdbcTemplate.query(FIND_ALL, subjectMapper);
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    @Override
    public long count() {
        log.debug("count() called");
        long result = jdbcTemplate.queryForObject(COUNT, Integer.class);
        log.debug("count() returned '{}'", result);
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        jdbcTemplate.update(DELETE_SUBJECT, id);
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Subject subject) {
        log.debug("delete('{}') called", subject);
        jdbcTemplate.update(DELETE_SUBJECT, subject.getId());
        log.debug("delete('{}') was success", subject);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        jdbcTemplate.update(DELETE_ALL);
        log.debug("deleteAll() was success");
    }


    public void addSubjectToTeacher(Integer subjectId, Integer teacherId) {
        log.debug("addSubjecctToTeacher('{}', '{}') called", subjectId, teacherId);
        jdbcTemplate.update(ADD_TEACHER_TO_SUBJECT, teacherId, subjectId);
        log.debug("addSubjecctToTeacher('{}', '{}') was success", subjectId, teacherId);
    }

    public List<Subject> getAllSubjectsOneTeacher(Integer teacherId) {
        log.debug("getAllSubjectsOneTeacher('{}') called", teacherId);
        List<Subject> result = jdbcTemplate.query(GET_ALL_SUBJECTS_ONE_TEACHER, subjectMapper, teacherId);
        log.debug("getAllSubjectsOneTeacher('{}') returned '{}'", teacherId, result);
        return result;
    }

    @Override
    public void update(Subject subject) {
        log.debug("update('{}') called", subject);
        jdbcTemplate.update(UPDATE_SUBJECT, subject.getName(), subject.getDescription(),
                subject.getTeacherId(), subject.getAmountLessons(), subject.getId());
        log.debug("update('{}') was success", subject);
    }

    public List<Subject> getSubjectsWithoutTeacher() {
        log.debug("getSubjectsWithoutTeacher() called");
        List<Subject> result = jdbcTemplate.query(GET_SUBJECTS_WITHOUT_TEACHER, subjectMapper);
        log.debug("getSubjectsWithoutTeacher() returned '{}', result");
        return result;
    }

}
