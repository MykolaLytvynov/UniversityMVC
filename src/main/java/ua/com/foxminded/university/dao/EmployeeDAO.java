package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmployeeDAO implements CrudOperations<Employee, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final EmployeeMapper employeeMapper;

    private static final String SAVE_EMPLOYEE = "INSERT INTO employees (name, lastName, positionId, salary) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT employees.id, employees.name, lastName, positionId, salary, positions.name position_name " +
            "FROM employees INNER JOIN positions ON employees.positionId = positions.id WHERE employees.id = ?";
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM employees WHERE id = ?";
    private static final String FIND_ALL = "SELECT employees.id, employees.name, lastName, positionId, " +
            "salary, positions.name position_name FROM employees INNER JOIN positions ON employees.positionId = positions.id";
    private static final String COUNT = "SELECT COUNT(*) FROM employees";
    private static final String DELETE_GROUP = "DELETE FROM employees WHERE id = ?";
    private static final String DELETE_ALL = "DELETE FROM employees";
    private static final String GET_ALL_EMPLOYEES_ONE_POSITION = "SELECT employees.id, employees.name, lastName, positionId, salary, positions.name position_name " +
            "FROM employees INNER JOIN positions ON employees.positionId = positions.id WHERE positionId = ?";
    private static final String UPDATE = "UPDATE employees SET name = ?, lastName = ?, positionId = ?, salary = ? WHERE id = ?";

    //For Dto
    private static final String GET_ALL_TEACHER = "SELECT DISTINCT employees.id, employees.name, lastname, positionId, salary, positions.name position_name " +
            "FROM employees INNER JOIN subjects ON employees.id = subjects.employeeid INNER JOIN positions ON employees.positionId = positions.id";

    @Override
    public Employee save(Employee employee) {
        log.debug("save('{}') called", employee);
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
        Integer id = ofNullable(keyHolder.getKeys())
                .map(map -> (Integer) map.get("id"))
                .orElseThrow(() -> new RuntimeException(format("Query '%s' didn't returned id!", SAVE_EMPLOYEE)));
        employee.setId(id);

        log.debug("save(Employee) was success. Returned '{}'", employee);
        return employee;
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Employee result = jdbcTemplate.queryForObject(FIND_BY_ID, employeeMapper, id);
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }

    @Override
    public boolean existsById(Integer id) {
        log.debug("exists('{}') called", id);
        Integer count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        boolean result = count != null && count > 0;
        log.debug("exists('{}') returned '{}'", id, result);
        return result;
    }

    @Override
    public List<Employee> findAll() {
        log.debug("findAll() called");
        List<Employee> result = jdbcTemplate.query(FIND_ALL, employeeMapper);
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    @Override
    public long count() {
        log.debug("count() called");
        long result = jdbcTemplate.queryForObject(COUNT, Integer.class);
        log.debug("count() returned '{}'", result);
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        jdbcTemplate.update(DELETE_GROUP, id);
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Employee employee) {
        log.debug("delete('{}') called", employee);
        jdbcTemplate.update(DELETE_GROUP, employee.getId());
        log.debug("delete('{}') was success");
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        jdbcTemplate.update(DELETE_ALL);
        log.debug("deleteAll() was success");
    }

    public List<Employee> getAllEmploeesOnePosition(Integer positionId) {
        log.debug("getAllEmploeesOnePosition('{}') called", positionId);
        List<Employee> result = jdbcTemplate.query(GET_ALL_EMPLOYEES_ONE_POSITION, employeeMapper, positionId);
        log.debug("getAllEmploeesOnePosition('{}') returned '{}'", positionId, result);
        return result;
    }

    @Override
    public void update(Employee employee) {
        log.debug("update('{}') called", employee);
        jdbcTemplate.update(UPDATE, employee.getName(), employee.getLastName(),
                employee.getPositionId(), employee.getSalary(), employee.getId());
        log.debug("update('{}') was success");
    }

    public List<Employee> getAllTeacher() {
        log.debug("getAllTeacher() called");
        List<Employee> teachers = jdbcTemplate.query(GET_ALL_TEACHER, employeeMapper);
        log.debug("getAllTeacher() returned '{}'", teachers);
        return teachers;
    }
}
