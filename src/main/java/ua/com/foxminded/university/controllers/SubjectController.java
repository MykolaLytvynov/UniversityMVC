package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.service.EmployeeService;
import ua.com.foxminded.university.service.SubjectService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    private final EmployeeService employeeService;


    @GetMapping
    public String getAll(Model model) {
        log.info("getAll() called");
        List<Subject> result = subjectService.findAll();
        model.addAttribute("subjects", result);
        log.info("Exit: {}", result);
        return "subject/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        log.info("Enter: getById('{}')", id);
        Subject result = subjectService.findById(id);
        model.addAttribute("subject", result);
        log.info("Exit: {}", result);
        return "subject/showOneSubject";
    }

    @GetMapping("/new")
    public String getPageCreateSubject(Model model) {
        log.info("getPageCreatePosition() called");
        model.addAttribute("subject", new Subject());
        List<Employee> allEmployees = employeeService.findAll();
        model.addAttribute("employees", allEmployees);
        log.info("Exit: {}", allEmployees);
        return "subject/new";
    }

    @PostMapping
    public String create(@ModelAttribute("subject") Subject subject, Model model) {
        log.info("Enter: create('{}')", subject);
        Subject result = subjectService.save(subject);
        model.addAttribute("subject", result);
        log.info("Exit: {}", result);
        return "subject/showOneSubject";
    }

    @GetMapping("/{id}/edit")
    public String getPageEdit(@PathVariable("id") int id, Model model) {
        log.info("Enter: getPageEdit('{}')", id);
        Subject result = subjectService.findById(id);
        model.addAttribute("subject", result);
        List<Employee> allEmployees = employeeService.findAll();
        model.addAttribute("employees", allEmployees);
        log.info("Exit: {}, {}", result, allEmployees);
        return "/subject/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("subject") Subject subject, @PathVariable("id") int id,
                         Model model) {
        log.info("Enter: update('{}', '{}')", subject, id);
        subject.setId(id);
        subjectService.update(subject);
        Subject result = subjectService.findById(id);
        model.addAttribute("subject", result);
        log.info("Exit: {}", result);
        return "/subject/showOneSubject";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        log.info("Enter: delete('{}')", id);
        subjectService.deleteById(id);
        log.info("delete('{}') was success", id);
        return "redirect:/subjects";
    }
}
