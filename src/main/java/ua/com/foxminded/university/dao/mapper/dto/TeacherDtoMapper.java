package ua.com.foxminded.university.dao.mapper.dto;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dto.TeacherDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TeacherDtoMapper implements RowMapper<TeacherDto> {

    @Override
    public TeacherDto mapRow(ResultSet resultSet, int i) throws SQLException {
        TeacherDto employeeDto = TeacherDto.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .lastName(resultSet.getString("lastName"))
                .positionId(resultSet.getInt("positionId"))
                .salary(resultSet.getInt("salary"))
                .positionName(resultSet.getString("position_name"))
                .build();

        return employeeDto;
    }
}
