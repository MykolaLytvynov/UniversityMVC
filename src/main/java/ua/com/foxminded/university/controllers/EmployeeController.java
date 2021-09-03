package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.service.EmployeeService;
import ua.com.foxminded.university.service.PositionService;
import ua.com.foxminded.university.service.SubjectService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final PositionService positionService;
    private final SubjectService subjectService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("employeesDto", employeeService.getAllEmployeesDto());
        return "employees/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        model.addAttribute("employeeDto", employeeService.getEmployeeDtoByID(id));
        model.addAttribute("subjectsOneEmployee", subjectService.getAllSubjectsOneTeacher(id));
        return "employees/showOneEmployee";
    }

    @GetMapping("/new")
    public String newEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("positions", positionService.findAll());
        return "employees/new";
    }

    @PostMapping
    public String create(@ModelAttribute("employee") Employee employee) {
        Employee result = employeeService.save(employee);
        return "redirect:/employees/" + result.getId();
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            employee.setName(employee.getName().trim());
            employee.setLastName(employee.getLastName().trim());
        }
        model.addAttribute("employee", employee);
        model.addAttribute("positions", positionService.findAll());
        return "employees/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("employee") Employee employee,
                         @PathVariable("id") int id, Model model) {
        employee.setId(id);
        employeeService.update(employee);
        model.addAttribute("employeeDto", employeeService.getEmployeeDtoByID(employee.getId()));
        model.addAttribute("subjectsOneEmployee", subjectService.getAllSubjectsOneTeacher(id));
        return "employees/showOneEmployee";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        employeeService.deleteById(id);
        return "redirect:/employees/";
    }
}
