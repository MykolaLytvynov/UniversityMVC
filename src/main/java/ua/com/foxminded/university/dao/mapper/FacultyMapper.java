package ua.com.foxminded.university.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.entities.Faculty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyMapper implements RowMapper<Faculty> {
    @Override
    public Faculty mapRow(ResultSet resultSet, int i) throws SQLException {
        Faculty faculty = new Faculty(resultSet.getString("name"),
                resultSet.getString("description"));
        faculty.setId(resultSet.getInt("id"));

        return faculty;
    }
}
