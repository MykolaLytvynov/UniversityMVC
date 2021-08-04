package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.person.Student;
import ua.com.foxminded.university.service.StudentService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public String getAll(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "students";
    }

    @PostMapping("/create")
    public String addNew(@RequestParam String firstName,
                         @RequestParam String lastName) {
        studentService.save(Student.builder().name(firstName).lastName(lastName).build());
        return "redirect:/students";
    }

    @GetMapping("update/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "edit/editStudent";
    }

    @PostMapping("update")
    public String update(@RequestParam Integer id,
                         @RequestParam String firstName,
                         @RequestParam String lastName) {
        Student student = studentService.findById(id);
        student.setName(firstName);
        student.setLastName(lastName);
        studentService.save(student);
        return "redirect:/students";
    }

    @GetMapping("/delete")
    public String delete(@PathVariable Integer id) {
        studentService.delete(studentService.findById(id));
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        studentService.delete(studentService.findById(id));
        return "redirect:/students";
    }

}
