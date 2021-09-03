package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.dto.StudentDto;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.entities.person.Student;
import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.FacultyService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

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
        model.addAttribute("studentDto", studentService.getStudentDtoById(idStudent));
        return "/students/showOneStudent";
    }


    @GetMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/new")
    public String newStudent(@PathVariable("idGroup") int idGroup, Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("groupDto", groupService.getGroupDto(idGroup));
        return "/students/new";
    }

    @PostMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/")
    public String create(@ModelAttribute("student") Student student) {
        Student result = studentService.save(student);
        return "redirect:/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/" + result.getId();
    }

    @GetMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}/edit")
    public String edit(@PathVariable("idStudent") int idStudent, Model model) {
        StudentDto result = studentService.getStudentDtoById(idStudent);
        if (result != null) {
            result.setName(result.getName().trim());
            result.setLastName(result.getLastName().trim());
            result.setFacultyName(result.getFacultyName().trim());
        }
        model.addAttribute("studentDto", result);
        model.addAttribute("groupsDto", groupService.getAllGroupsDto());
        return "/students/edit";
    }

    @PatchMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}")
    public String update(@ModelAttribute("studentDto") StudentDto studentDto,
                         @PathVariable("idStudent") Integer studentId, Model model) {
        Student student = Student.builder()
                .id(studentId)
                .name(studentDto.getName())
                .lastName(studentDto.getLastName())
                .groupId(studentDto.getGroupId())
                .build();
        studentService.update(student);
        model.addAttribute("studentDto", studentService.getStudentDtoById(studentId));
        return "/students/showOneStudent";
    }

    @DeleteMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}")
    public String delete(@PathVariable("idStudent") int idStudent) {
        studentService.deleteById(idStudent);
        return "redirect:/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}";
    }

}
