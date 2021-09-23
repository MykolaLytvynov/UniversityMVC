package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.person.Student;
import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.FacultyService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.StudentService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final GroupService groupService;

    @GetMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}")
    public String findById(@PathVariable("idStudent") int idStudent, Model model) {
        log.info("Enter: findById('{}')", idStudent);
        Student result = studentService.findById(idStudent);
        model.addAttribute("student", result);
        return "/students/showOneStudent";
    }


    @GetMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/new")
    public String getPageCreateStudent(@PathVariable("idGroup") int idGroup, Model model) {
        log.info("Enter: findById('{}')", idGroup);
        model.addAttribute("student", new Student());
        Group group = groupService.findById(idGroup);
        model.addAttribute("group", group);
        log.info("Exit: {}", group);
        return "/students/new";
    }

    @PostMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/")
    public String create(@ModelAttribute("student") Student student, Model model) {
        log.info("Enter: create('{}')", student);
        Student result = studentService.save(student);
        model.addAttribute("student", result);
        log.info("Exit: {}", result);
        return "/students/showOneStudent";
    }

    @GetMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}/edit")
    public String getPageEdit(@PathVariable("idStudent") int idStudent, Model model) {
        log.info("Enter: getPageEdit('{}')", idStudent);
        Student result = studentService.findById(idStudent);
        model.addAttribute("student", result);
        List<Group> allGroups = groupService.findAll();
        model.addAttribute("groups", allGroups);
        log.info("Exit: {}, {}", result, allGroups);
        return "/students/edit";
    }

    @PatchMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}")
    public String update(@ModelAttribute("student") Student student,
                         @PathVariable("idStudent") Integer studentId, Model model) {
        log.info("Enter: update('{}', '{}')", student, studentId);
        student.setId(studentId);
        studentService.update(student);
        Student result = studentService.findById(studentId);
        model.addAttribute("student", result);
        log.info("Exit: {}", result);
        return "/students/showOneStudent";
    }

    @DeleteMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}")
    public String delete(@PathVariable("idStudent") int idStudent) {
        log.info("Enter: delete('{}')", idStudent);
        studentService.deleteById(idStudent);
        log.info("delete('{}') was success", idStudent);
        return "redirect:/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}";
    }

}
