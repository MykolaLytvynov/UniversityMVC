package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.person.Student;
import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.FacultyService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.StudentService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final FacultyService facultyService;
    private final CourseService courseService;
    private final GroupService groupService;

    @GetMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}")
    public String findById(@PathVariable("idStudent") int idStudent, Model model) {
        model.addAttribute("student", studentService.findById(idStudent));
        return "/students/showOneStudent";
    }


    @GetMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/new")
    public String newStudent(@PathVariable("idGroup") int idGroup, Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("group", groupService.findById(idGroup));
        return "/students/new";
    }

    @PostMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/")
    public String create(@ModelAttribute("student") Student student) {
        Student result = studentService.save(student);
        return "redirect:/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/" + result.getId();
    }

    @GetMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}/edit")
    public String edit(@PathVariable("idStudent") int idStudent, Model model) {
        Student result = studentService.findById(idStudent);
        model.addAttribute("student", result);
        model.addAttribute("groups", groupService.findAll());
        return "/students/edit";
    }

    @PatchMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}")
    public String update(@ModelAttribute("student") Student student,
                         @PathVariable("idStudent") Integer studentId, Model model) {
        student.setId(studentId);
        studentService.update(student);
        model.addAttribute("student", studentService.findById(studentId));
        return "/students/showOneStudent";
    }

    @DeleteMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}")
    public String delete(@PathVariable("idStudent") int idStudent) {
        studentService.deleteById(idStudent);
        return "redirect:/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}";
    }

}
