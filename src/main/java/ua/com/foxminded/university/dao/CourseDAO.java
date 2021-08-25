package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mapper.CourseMapper;
import ua.com.foxminded.university.dao.mapper.GroupMapper;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.entities.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseDAO implements CrudOperations<Course, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;
    private final GroupMapper groupMapper;

    private static final String SAVE_COURSE = "INSERT INTO courses (nummerCourse, facultyId) VALUES (?, ?)";
    public static final String FIND_BY_ID = "SELECT * FROM courses WHERE id = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM courses WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM courses";
    public static final String COUNT = "SELECT COUNT(*) FROM courses";
    public static final String DELETE_COURSE = "DELETE FROM courses WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM courses";

    public static final String GET_GROUPS_ONE_COURSE = "SELECT * FROM groups WHERE courseId = ?";

    @Override
    public Course save(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        log.debug("save('{}') called", course);

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_COURSE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, course.getNumberCourse());
                ps.setInt(2, course.getFacultyId());
                return ps;
            }
        }, keyHolder);

        Integer id = ofNullable(keyHolder.getKeys())
                .map(map -> (Integer) map.get("id"))
                .orElseThrow(() -> new RuntimeException(format("Query '%s' didn't returned id!", SAVE_COURSE)));
        course.setId(id);

        log.debug("save(Course) was success. Returned '{}'", course);
        return course;
    }

    @Override
    public Optional<Course> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Course result = jdbcTemplate.queryForObject(FIND_BY_ID, courseMapper, id);
        if (result != null) {
            result.setGroups(getGroupsOneCourse(id));
        }

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
    public List<Course> findAll() {
        log.debug("findAll() called");
        List<Course> result = jdbcTemplate.query(FIND_ALL, courseMapper)
                .stream()
                .peek(course -> course.setGroups(getGroupsOneCourse(course.getId())))
                .collect(Collectors.toList());

        log.debug("findAll() returned '{}'", result);
        return result;
    }

    @Override
    public long count() {
        log.debug("count() called");
        Integer result = jdbcTemplate.queryForObject(COUNT, Integer.class);
        log.debug("count() returned '{}'", result);
        return ofNullable(result)
                .orElseThrow(() -> new RuntimeException(format("Query '%s' returned null", COUNT)));
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        jdbcTemplate.update(DELETE_COURSE, id);
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Course course) {
        log.debug("delete('{}') called", course);
        jdbcTemplate.update(DELETE_COURSE, course.getId());
        log.debug("delete('{}') was success", course);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        jdbcTemplate.update(DELETE_ALL);
        log.debug("deleteAll() was success");
    }

    public List<Group> getGroupsOneCourse(Integer courseId) {
        log.debug("getGroupsOneCourse('{}') called", courseId);
        List<Group> result = jdbcTemplate.query(GET_GROUPS_ONE_COURSE, groupMapper, courseId);
        log.debug("getGroupsOneCourse('{}') returned '{}'", courseId, result);
        return result;
    }

    @Override
    public void update(Course course) {
    }
}
