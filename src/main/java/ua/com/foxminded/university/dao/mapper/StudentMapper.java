package ua.com.foxminded.university.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.person.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = Student.builder()
                .name(resultSet.getString("name"))
                .lastName(resultSet.getString("lastName"))
                .groupId(resultSet.getInt("groupId"))
                .build();
        student.setId(resultSet.getInt("id"));
        return student;
    }
}
