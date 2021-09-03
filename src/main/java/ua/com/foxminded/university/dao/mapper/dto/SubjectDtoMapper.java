package ua.com.foxminded.university.dao.mapper.dto;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dto.SubjectDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubjectDtoMapper implements RowMapper<SubjectDto> {
    @Override
    public SubjectDto mapRow(ResultSet resultSet, int i) throws SQLException {
        SubjectDto subject = new SubjectDto(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("amountLessons"));
        subject.setTeacherId(resultSet.getInt("employeeId"));
        subject.setTeacherName(resultSet.getString("teacher_name"));
        subject.setTeacherLastName(resultSet.getString("lastName"));
        return subject;
    }

}
