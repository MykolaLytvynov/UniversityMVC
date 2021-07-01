package ua.com.foxminded.university.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.entities.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet resultSet, int i) throws SQLException {
        Course course = new Course(resultSet.getInt("nummerCourse"));
        course.setId(resultSet.getInt("id"));

        return course;
    }
}
