package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.EmployeeDAO;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    public Employee save(Employee employee) {
        log.debug("save('{}') called", employee);
        Employee result = employeeDAO.save(employee);
        log.debug("save('{}') returned '{}'", employee, result);
        return result;
    }

    public Employee findById(Integer id) {
        log.debug("findById('{}') called");
        Employee result = employeeDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found by id = " + id));
        log.debug("findById('{}') returned '{}'", id, result);
        return result;
    }

    public boolean existsById(Integer id) {
        log.debug("existsById('{}') called", id);
        boolean result = employeeDAO.existsById(id);
        log.debug("existsById('{}') returned '{}'", id, result);
        return result;
    }

    public List<Employee> findAll() {
        log.debug("findAll() called");
        List<Employee> result = employeeDAO.findAll();
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    public long count() {
        log.debug("count() called");
        long result = employeeDAO.count();
        log.debug("count() returned '{}'", result);
        return result;
    }

    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        employeeDAO.deleteById(id);
        log.debug("deleteById('{}') was success", id);
    }

    public void delete(Employee employee) {
        log.debug("delete('{}') called", employee);
        employeeDAO.delete(employee);
        log.debug("delete('{}') was success", employee);
    }

    public void deleteAll() {
        log.debug("deleteAll() called");
        employeeDAO.deleteAll();
        log.debug("deleteAll() was success");
    }

    public List<Employee> getAllEmploeesOnePosition(Integer positionId) {
        log.debug("getAllEmploeesOnePosition('{}') called", positionId);
        List<Employee> result = employeeDAO.getAllEmploeesOnePosition(positionId);
        log.debug("getAllEmploeesOnePosition('{}') returned '{}'", positionId, result);
        return result;
    }
}
