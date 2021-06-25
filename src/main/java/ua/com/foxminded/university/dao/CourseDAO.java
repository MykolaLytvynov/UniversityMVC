package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

@Component
public class CourseDAO implements CrudOperations<Course, Integer> {
    private JdbcTemplate jdbcTemplate;

    private static final String SAVE_COURSE = "INSERT INTO courses (nummerCourse) VALUES (?)";
    public static final String FIND_BY_ID = "SELECT * FROM courses WHERE id = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM courses WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM courses";
    public static final String COUNT = "SELECT COUNT(*) FROM courses";
    public static final String DELETE_COURSE = "DELETE FROM courses WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM courses";
    public static final String ADD_COURSE_TO_FACULTY = "UPDATE courses SET facultyId = ? WHERE id = ?";


    public static final String GET_GROUPS_ONE_COURSE = "SELECT * FROM groups WHERE courseId = ?";


    @Autowired
    public CourseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Course save(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_COURSE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, course.getNummerCourse());
                return ps;
            }
        }, keyHolder);

        course.setId((int) keyHolder.getKeys().get("id"));
        return course;
    }

    @Override
    public Course findById(Integer id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, new CourseMapper())
                .stream()
                .peek(course -> course.setGroups(getGroupsOneCourse(id)))
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean existsById(Integer id) {
        int count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        return count > 0;
    }

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(FIND_ALL, new CourseMapper())
                .stream()
                .peek(course -> course.setGroups(getGroupsOneCourse(course.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update(DELETE_COURSE, id);
    }

    @Override
    public void delete(Course course) {
        jdbcTemplate.update(DELETE_COURSE, course.getId());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    public void addCourseToFaculty(Course course, Faculty faculty) {
        jdbcTemplate.update(ADD_COURSE_TO_FACULTY, faculty.getId(), course.getId());
    }

    public List<Group> getGroupsOneCourse(Integer id) {
        return jdbcTemplate.query(GET_GROUPS_ONE_COURSE, new Object[]{id}, new GroupMapper());
    }
}
