package ua.com.foxminded.university.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.entities.person.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee employee = Employee.builder()
                .name(resultSet.getString("name"))
                .lastName(resultSet.getString("lastName"))
                .positionId(resultSet.getInt("positionId"))
                .salary(resultSet.getInt("salary"))
                .build();
        employee.setId(resultSet.getInt("id"));

        return employee;
    }
}
