package ua.com.foxminded.university.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
        Group group = new Group(resultSet.getInt("nummerGroup"),
                resultSet.getInt("course_id"),
                resultSet.getInt("faculty_id"));
        group.setCourseNumber(resultSet.getInt("nummercourse"));
        group.setFacultyName(resultSet.getString("name"));
        group.setId(resultSet.getInt("id"));
        return group;
    }
}
