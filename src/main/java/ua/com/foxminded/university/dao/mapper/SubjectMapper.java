package ua.com.foxminded.university.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubjectMapper implements RowMapper<Subject> {
    @Override
    public Subject mapRow(ResultSet resultSet, int i) throws SQLException {
        Subject subject = new Subject(resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("amountLessons"));
        subject.setId(resultSet.getInt("id"));
        subject.setTeacherId(resultSet.getInt("employeeId"));
        return subject;
    }
}
