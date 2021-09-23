package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Position;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.service.EmployeeService;
import ua.com.foxminded.university.service.PositionService;
import ua.com.foxminded.university.service.SubjectService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final PositionService positionService;
    private final SubjectService subjectService;

    @GetMapping
    public String getAll(Model model) {
        log.info("getAll() called");
        List<Employee> result = employeeService.findAll();
        model.addAttribute("employees", result);
        log.info("Exit: {}", result);
        return "employees/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        log.info("Enter: getById('{}')", id);
        Employee result = employeeService.findById(id);
        model.addAttribute("employee", result);
        List<Subject> subjectsOneEmployee = subjectService.getAllSubjectsOneTeacher(id);
        model.addAttribute("subjectsOneEmployee", subjectsOneEmployee);
        log.info("Exit: {}, {}", result, subjectsOneEmployee);
        return "employees/showOneEmployee";
    }

    @GetMapping("/new")
    public String getPageCreateEmployee(Model model) {
        log.info("getPageCreateEmployee() called");
        model.addAttribute("employee", new Employee());
        List<Position> allPosition = positionService.findAll();
        model.addAttribute("positions", allPosition);
        log.info("Exit: {}", allPosition);
        return "employees/new";
    }

    @PostMapping
    public String create(@ModelAttribute("employee") Employee employee) {
        log.info("Enter: {}", employee);
        Employee result = employeeService.save(employee);
        log.info("Exit: {}", result);
        return "redirect:/employees/" + result.getId();
    }

    @GetMapping("{id}/edit")
    public String getPageEditEmployee(@PathVariable("id") int id, Model model) {
        log.info("Enter: getPageEditEmployee('{}')", id);
        Employee result = employeeService.findById(id);
        model.addAttribute("employee", result);
        List<Position> allPosition = positionService.findAll();
        model.addAttribute("positions", allPosition);
        log.info("Exit: {}; {}", result, allPosition);
        return "employees/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("employee") Employee employee,
                         @PathVariable("id") int id, Model model) {
        log.info("Enter: update('{}', '{}')", employee, id);
        employee.setId(id);
        employeeService.update(employee);
        Employee result = employeeService.findById(employee.getId());
        model.addAttribute("employee", result);
        List<Subject> allSubjectsOneEmployee = subjectService.getAllSubjectsOneTeacher(id);
        model.addAttribute("subjectsOneEmployee", allSubjectsOneEmployee);
        log.info("Exit: {}, {}", result, allSubjectsOneEmployee);
        return "employees/showOneEmployee";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        log.info("Enter: delete('{}')", id);
        employeeService.deleteById(id);
        log.info("delete('{}') was success", id);
        return "redirect:/employees/";
    }
}
