package ua.com.foxminded.university.dao.mapper.dto;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dto.EmployeeDto;
import ua.com.foxminded.university.entities.person.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeDtoMapper implements RowMapper<EmployeeDto> {
    @Override
    public EmployeeDto mapRow(ResultSet resultSet, int i) throws SQLException {
        EmployeeDto employeeDto = EmployeeDto.builder()
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
