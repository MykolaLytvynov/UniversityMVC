package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mapper.GroupMapper;
import ua.com.foxminded.university.dao.mapper.StudentMapper;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.person.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.lang.String.format;

@Component
@Slf4j
@RequiredArgsConstructor
public class GroupDAO implements CrudOperations<Group, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final GroupMapper groupMapper;
    private final StudentMapper studentMapper;

    public static final String SAVE_GROUP = "INSERT INTO groups (nummerGroup) VALUES (?)";
    public static final String FIND_BY_ID = "SELECT * FROM groups WHERE id = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM groups WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM groups";
    public static final String COUNT = "SELECT COUNT(*) FROM groups";
    public static final String DELETE_GROUP = "DELETE FROM groups WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM groups";
    public static final String ADD_GROUP_TO_COURSE = "UPDATE groups SET courseId = ? WHERE id = ?";

    public static final String GET_STUDENTS_ONE_GROUP = "SELECT * FROM students WHERE groupId = ?";

    @Override
    public Group save(Group group) {
        log.debug("save('{}') called", group);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_GROUP, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, group.getNummerGroup());
                return ps;
            }
        }, keyHolder);

        Integer id = ofNullable(keyHolder.getKeys())
                .map(map -> (Integer) map.get("id"))
                .orElseThrow(() -> new RuntimeException(format("Query '%s' didn't returned it!", SAVE_GROUP)));
        group.setId(id);
        log.debug("save(Group) was success. Returned '{}'", group);
        return group;
    }

    @Override
    public Optional<Group> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Group result = jdbcTemplate.queryForObject(FIND_BY_ID, groupMapper, id);
        if (result != null) {
            result.setStudents(getStudentsOneGroup(id));
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
    public List<Group> findAll() {
        log.debug("findAll() called");
        List<Group> result = jdbcTemplate.query(FIND_ALL, new GroupMapper())
                .stream()
                .peek(group -> group.setStudents(getStudentsOneGroup(group.getId())))
                .collect(Collectors.toList());

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
        jdbcTemplate.update(DELETE_GROUP, id);
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Group group) {
        log.debug("delete('{}') called", group);
        jdbcTemplate.update(DELETE_GROUP, group.getId());
        log.debug("delete('{}') was success", group);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        jdbcTemplate.update(DELETE_ALL);
        log.debug("deleteAll() was success");
    }

    public void addGroupToCourse(Course course, Group group) {
        log.debug("addGroupToCourse('{}','{}') called", course, group);
        jdbcTemplate.update(ADD_GROUP_TO_COURSE, course.getId(), group.getId());
        log.debug("addGroupToCourse('{}','{}') was success", course, group);
    }

    public List<Student> getStudentsOneGroup(Integer groupId) {
        log.debug("getStudentsOneGroup('{}') called", groupId);
        List<Student> result = jdbcTemplate.query(GET_STUDENTS_ONE_GROUP, studentMapper, groupId);
        log.debug("getStudentsOneGroup('{}') returned '{}'", groupId, result);
        return result;
    }
}
