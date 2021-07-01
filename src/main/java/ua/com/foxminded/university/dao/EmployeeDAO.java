package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mapper.EmployeeMapper;
import ua.com.foxminded.university.entities.person.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeDAO implements CrudOperations<Employee, Integer> {
    private JdbcTemplate jdbcTemplate;

    public static final String SAVE_EMPLOYEE = "INSERT INTO employees (name, lastName, positionId, salary) VALUES (?, ?, ?, ?)";
    public static final String FIND_BY_ID = "SELECT * FROM employees WHERE id = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM employees WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM employees";
    public static final String COUNT = "SELECT COUNT(*) FROM employees";
    public static final String DELETE_GROUP = "DELETE FROM employees WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM employees";
    public static final String GET_ALL_EMPLOEES_ONE_POSITION = "SELECT * FROM employees WHERE positionId = ?";


    @Autowired
    public EmployeeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Employee save(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, employee.getName());
                ps.setString(2, employee.getLastName());
                ps.setInt(3, employee.getPositionId());
                ps.setInt(4, employee.getSalary());
                return ps;
            }
        }, keyHolder);
        employee.setId((int) keyHolder.getKeys().get("id"));
        return employee;
    }

    @Override
    public Employee findById(Integer id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, new EmployeeMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean existsById(Integer id) {
        int count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        return count > 0;
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query(FIND_ALL, new EmployeeMapper());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update(DELETE_GROUP, id);
    }

    @Override
    public void delete(Employee employee) {
        jdbcTemplate.update(DELETE_GROUP, employee.getId());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    public List<Employee> getAllEmploeesOnePosition(Integer positionId) {
        return jdbcTemplate.query(GET_ALL_EMPLOEES_ONE_POSITION, new Object[]{positionId}, new EmployeeMapper());
    }
}
