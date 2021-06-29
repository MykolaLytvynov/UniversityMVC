package ua.com.foxminded.university.dao;

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
import java.util.stream.Collectors;

@Component
public class GroupDAO implements CrudOperations<Group, Integer> {
    private JdbcTemplate jdbcTemplate;

    public static final String SAVE_GROUP = "INSERT INTO groups (nummerGroup) VALUES (?)";
    public static final String FIND_BY_ID = "SELECT * FROM groups WHERE id = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM groups WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM groups";
    public static final String COUNT = "SELECT COUNT(*) FROM groups";
    public static final String DELETE_GROUP = "DELETE FROM groups WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM groups";
    public static final String ADD_GROUP_TO_COURSE = "UPDATE groups SET courseId = ? WHERE id = ?";

    public static final String GET_STUDENTS_ONE_GROUP = "SELECT * FROM students WHERE groupId = ?";

    @Autowired
    public GroupDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group save(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_GROUP, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, group.getNummerGroup());
                return ps;
            }
        }, keyHolder);

        group.setId((int)keyHolder.getKeys().get("id"));
        return group;
    }

    @Override
    public Group findById(Integer id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, new GroupMapper())
                .stream().peek(group -> group.setStudents(getStudentsOneGroup(group.getId())))
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean existsById(Integer id) {
        int count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        return count > 0;
    }

    @Override
    public List<Group> findAll() {
        return jdbcTemplate.query(FIND_ALL, new GroupMapper())
                .stream()
                .peek(group -> group.setStudents(getStudentsOneGroup(group.getId())))
                .collect(Collectors.toList());
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
    public void delete(Group group) {
        jdbcTemplate.update(DELETE_GROUP, group.getId());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    public void addGroupToCourse (Course course, Group group) {
        jdbcTemplate.update(ADD_GROUP_TO_COURSE, course.getId(), group.getId());
    }

    public List<Student> getStudentsOneGroup (Integer id) {
        return jdbcTemplate.query(GET_STUDENTS_ONE_GROUP, new Object[]{id}, new StudentMapper())
                .stream()
                .peek(student -> student.setName(student.getName().trim()))
                .peek(student -> student.setLastName(student.getLastName().trim()))
                .collect(Collectors.toList());
    }

}
