package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.service.EmployeeService;
import ua.com.foxminded.university.service.SubjectService;

@Controller
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    private final EmployeeService employeeService;


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("employees", employeeService.findAll());

        return "subject/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        model.addAttribute("subject", subjectService.findById(id));
        model.addAttribute("employees", employeeService.findAll());
        return "subject/showOneSubject";
    }

    @GetMapping("/new")
    public String newSubject(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("employees", employeeService.findAll());
        return "subject/new";
    }

    @PostMapping
    public String create(@ModelAttribute("subject") Subject subject) {
        Subject result = subjectService.save(subject);
        if (subject.getTeacherId() != null) {
            subjectService.addSubjectToTeacher(subject.getId(), subject.getTeacherId());
        }
        return "redirect:/subjects/" + result.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Subject result = subjectService.findById(id);
        if (result != null) {
            result.setName(result.getName().trim());
            result.setDescription(result.getDescription().trim());
        }
        model.addAttribute("subject", result);
        model.addAttribute("employees", employeeService.findAll());
        return "/subject/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("subject") Subject subject, @PathVariable("id") int id,
                         Model model) {
        subject.setId(id);
        subjectService.update(subject);
        model.addAttribute("subject", subject);
        if (subject.getTeacherId() != null) {
            subjectService.addSubjectToTeacher(subject.getId(), subject.getTeacherId());
        }
        model.addAttribute("employees", employeeService.findAll());
        return "/subject/showOneSubject";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        subjectService.deleteById(id);
        return "redirect:/subjects";
    }
}
