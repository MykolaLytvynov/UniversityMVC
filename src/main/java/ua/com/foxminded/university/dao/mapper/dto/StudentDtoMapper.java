package ua.com.foxminded.university.dao.mapper.dto;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dto.StudentDto;
import ua.com.foxminded.university.entities.person.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentDtoMapper implements RowMapper<StudentDto> {

    @Override
    public StudentDto mapRow(ResultSet resultSet, int i) throws SQLException {
        StudentDto studentDto = StudentDto.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .lastName(resultSet.getString("lastName"))
                .groupId(resultSet.getInt("groupId"))
                .build();
        studentDto.setNumberGroup(resultSet.getInt("nummergroup"));
        studentDto.setNumberCourse(resultSet.getInt("nummercourse"));
        studentDto.setFacultyName(resultSet.getString("faculty_name"));
        studentDto.setFacultyId(resultSet.getInt("faculty_id"));
        studentDto.setCourseId(resultSet.getInt("course_id"));
        return studentDto;
    }
}
