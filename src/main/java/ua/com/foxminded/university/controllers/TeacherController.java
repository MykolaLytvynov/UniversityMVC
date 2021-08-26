package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.entities.person.Teacher;
import ua.com.foxminded.university.service.EmployeeService;
import ua.com.foxminded.university.service.PositionService;
import ua.com.foxminded.university.service.SubjectService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
    private final EmployeeService employeeService;
    private final SubjectService subjectService;
    private final PositionService positionService;

    @GetMapping
    public String getAll(Model model) {
        List<Teacher> teacherList = new ArrayList<>();
        for (Employee teacher : employeeService.getAllTeacher()) {
            teacherList.add(Teacher.builder().id(teacher.getId())
                    .name(teacher.getName())
                    .lastName(teacher.getLastName())
                    .positionId(teacher.getPositionId())
                    .salary(teacher.getSalary())
                    .subjectList(subjectService.getAllSubjectsOneTeacher(teacher.getId()))
                    .build());
        }
        model.addAttribute("teachers", teacherList);
        model.addAttribute("positions", positionService.findAll());
        return "/employees/teachers/getAll";
    }

    @GetMapping("/new")
    public String newTeacher(Model model) {
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("positions", positionService.findAll());
        model.addAttribute("subjectsWithoutTeacher", subjectService.getSubjectsWithoutTeacher());
        return "/employees/teachers/new";
    }

    @PostMapping
    public String create(@ModelAttribute("teacher") Teacher teacher,
                         @RequestParam("subjectId") Integer subjectId, Model model) {
        Employee result = employeeService.save(teacher);
        subjectService.addSubjectToTeacher(subjectId, teacher.getId());
        return "redirect:/employees/" + result.getId();
    }

}
