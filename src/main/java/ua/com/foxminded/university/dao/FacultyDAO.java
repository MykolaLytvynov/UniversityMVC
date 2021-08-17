package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mapper.CourseMapper;
import ua.com.foxminded.university.dao.mapper.FacultyMapper;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Component
@Slf4j
public class FacultyDAO implements CrudOperations<Faculty, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final FacultyMapper facultyMapper;
    private final CourseMapper courseMapper;

    private static final String SAVE_FACULTY = "INSERT INTO faculties (name, description) VALUES (?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM faculties WHERE id = ?";
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM faculties WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM faculties";
    private static final String COUNT = "SELECT COUNT(id) FROM faculties";
    private static final String DELETE_FACULTY = "DELETE FROM faculties WHERE id = ?";
    private static final String DELETE_ALL = "DELETE FROM faculties";
    private static final String UPDATE_FACULTY = "UPDATE faculties SET name = ?, description = ? WHERE id = ?";

    public static final String GET_COURSES_ONE_FACULTY = "SELECT * FROM courses WHERE facultyId = ?";

    @Override
    public Faculty save(Faculty faculty) {
        log.debug("save('{}') called", faculty);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_FACULTY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, faculty.getName());
                ps.setString(2, faculty.getDescription());
                return ps;
            }
        }, keyHolder);

        Integer id = ofNullable(keyHolder.getKeys())
                .map(map -> (Integer) map.get("id"))
                .orElseThrow(() -> new RuntimeException(format("Query '%s' didn't returned it!", SAVE_FACULTY)));
        faculty.setId(id);

        log.debug("save(Faculty) was success. Returned '{}'", faculty);
        return faculty;
    }

    @Override
    public Optional<Faculty> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Faculty result = jdbcTemplate.queryForObject(FIND_BY_ID, facultyMapper, id);
        if (result != null) {
            result.setCourses(getCoursesOneFaculty(result.getId()));
        }
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }

    @Override
    public boolean existsById(Integer id) {
        log.debug("exists('{}') called", id);
        Integer count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        boolean result = count != null && count > 0;
        log.debug("existsById('{}') returned '{}'", id, result);
        return result;
    }

    @Override
    public List<Faculty> findAll() {
        log.debug("findAll() called");
        List<Faculty> result = jdbcTemplate.query(FIND_ALL, facultyMapper)
                .stream()
                .peek(faculty -> faculty.setCourses(getCoursesOneFaculty(faculty.getId())))
                .collect(Collectors.toList());

        log.debug("findAll() returned '{}'");
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
        log.debug("deleteById('{}') called");
        jdbcTemplate.update(DELETE_FACULTY, id);
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Faculty faculty) {
        log.debug("delete('{}') called", faculty);
        jdbcTemplate.update(DELETE_FACULTY, faculty.getId());
        log.debug("delete('{}') was success", faculty);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        jdbcTemplate.update(DELETE_ALL);
        log.debug("deleteAll() was success");
    }

    public List<Course> getCoursesOneFaculty(Integer facultyId) {
        log.debug("getCoursesOneFaculty('{}') called", facultyId);
        List<Course> result = jdbcTemplate.query(GET_COURSES_ONE_FACULTY, courseMapper, facultyId);
        log.debug("getCoursesOneFaculty('{}') returned '{}'", facultyId, result);
        return result;
    }

    @Override
    public void update(Faculty faculty) {
        log.debug("update('{}') called", faculty);
        jdbcTemplate.update(UPDATE_FACULTY, faculty.getName(), faculty.getDescription(), faculty.getId());
        log.debug("update('{}') was success", faculty);
    }
}
