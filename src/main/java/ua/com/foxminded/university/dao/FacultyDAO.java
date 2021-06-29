package ua.com.foxminded.university.dao;

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
import java.util.stream.Collectors;

@Component
public class FacultyDAO implements CrudOperations<Faculty, Integer> {
    private JdbcTemplate jdbcTemplate;

    private static final String SAVE_FACULTY = "INSERT INTO faculties (name, description) VALUES (?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM faculties WHERE id = ?";
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM faculties WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM faculties";
    private static final String COUNT = "SELECT COUNT(id) FROM faculties";
    private static final String DELETE_FACULTY = "DELETE FROM faculties WHERE id = ?";
    private static final String DELETE_ALL = "DELETE FROM faculties";

    public static final String GET_COURSES_ONE_FACULTY = "SELECT * FROM courses WHERE facultyId = ?";


    @Autowired
    public FacultyDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Faculty save(Faculty faculty) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_FACULTY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, faculty.getName().trim());
                ps.setString(2, faculty.getDescription().trim());
                return ps;
            }
        }, keyHolder);

        faculty.setId((int) keyHolder.getKeys().get("id"));
        return faculty;
    }

    @Override
    public Faculty findById(Integer id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, new FacultyMapper())
                .stream()
                .peek(faculty -> faculty.setCourses(getCoursesOneFaculty(id)))
                .peek(faculty -> faculty.setName(faculty.getName().trim()))
                .peek(faculty -> faculty.setDescription(faculty.getDescription().trim()))
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean existsById(Integer id) {
        int count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        return count > 0;
    }

    @Override
    public List<Faculty> findAll() {
        return jdbcTemplate.query(FIND_ALL, new FacultyMapper())
                .stream()
                .peek(faculty -> faculty.setCourses(getCoursesOneFaculty(faculty.getId())))
                .peek(faculty -> faculty.setName(faculty.getName().trim()))
                .peek(faculty -> faculty.setDescription(faculty.getDescription().trim()))
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update(DELETE_FACULTY, id);
    }

    @Override
    public void delete(Faculty faculty) {
        jdbcTemplate.update(DELETE_FACULTY, faculty.getId());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    public List<Course> getCoursesOneFaculty(Integer id) {
        return jdbcTemplate.query(GET_COURSES_ONE_FACULTY, new Object[]{id}, new CourseMapper());
    }
}
