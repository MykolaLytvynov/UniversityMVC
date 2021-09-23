package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Position;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.dto.TeacherDto;
import ua.com.foxminded.university.entities.person.Teacher;
import ua.com.foxminded.university.service.EmployeeService;
import ua.com.foxminded.university.service.PositionService;
import ua.com.foxminded.university.service.SubjectService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
    private final EmployeeService employeeService;
    private final SubjectService subjectService;
    private final PositionService positionService;

    @GetMapping
    public String getAll(Model model) {
        log.info("getAll() called");
        List<TeacherDto> result = employeeService.getAllTeacher();
        model.addAttribute("teachers", result);
        log.info("Exit: {}", result);
        return "/employees/teachers/getAll";
    }

    @GetMapping("/new")
    public String getPageCreateTeacher(Model model) {
        log.info("getPageCreateTeacher() called");
        model.addAttribute("teacher", new Teacher());
        List<Position> allPosition = positionService.findAll();
        model.addAttribute("positions", allPosition);
        List<Subject> subjectsWithoutTeacher = subjectService.getSubjectsWithoutTeacher();
        model.addAttribute("subjectsWithoutTeacher", subjectsWithoutTeacher);
        log.info("Exit: {}, {}", allPosition, subjectsWithoutTeacher);
        return "/employees/teachers/new";
    }

    @PostMapping
    public String create(@ModelAttribute("teacher") Teacher teacher,
                         @RequestParam("subjectId") Integer subjectId, Model model) {
        log.info("Enter: create('{}', '{}')", teacher, subjectId);
        Employee result = employeeService.save(teacher);
        subjectService.addSubjectToTeacher(subjectId, teacher.getId());
        log.info("Exit: {}", result);
        return "redirect:/employees/" + result.getId();
    }

}
