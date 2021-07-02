package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.EmployeeDAO;
import ua.com.foxminded.university.dao.mapper.EmployeeMapper;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeService {
    @Autowired
    private EmployeeDAO employeeDAO;

    public Employee save(Employee employee) {
        return employeeDAO.save(employee);
    }

    public Employee findById(Integer id) {
        return Optional.ofNullable(employeeDAO.findById(id)).orElseThrow(() -> new NotFoundException("Employee not found by id = " + id));
    }

    public boolean existsById(Integer id) {
        return employeeDAO.existsById(id);
    }

    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    public long count() {
        return employeeDAO.count();
    }

    public void deleteById(Integer id) {
        employeeDAO.deleteById(id);
    }

    public void delete(Employee employee) {
        employeeDAO.delete(employee);
    }

    public void deleteAll() {
        employeeDAO.deleteAll();
    }

    public List<Employee> getAllEmploeesOnePosition(Integer positionId) {
        return employeeDAO.getAllEmploeesOnePosition(positionId);
    }
}
