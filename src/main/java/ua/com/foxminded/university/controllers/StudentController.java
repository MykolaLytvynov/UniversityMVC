package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String findById(@PathVariable("idCourse") int idCourse,
                           @PathVariable("idFaculty") int idFaculty,
                           @PathVariable("idGroup") int idGroup,
                           @PathVariable("idStudent") int idStudent, Model model) {
        model.addAttribute("student", studentService.findById(idStudent));
        model.addAttribute("faculty", facultyService.findById(idFaculty));
        model.addAttribute("course", courseService.findById(idCourse));
        model.addAttribute("group", groupService.findById(idGroup));
        return "/students/showOneStudent";
    }


    @GetMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/new")
    public String newStudent(@PathVariable("idCourse") int idCourse,
                             @PathVariable("idFaculty") int idFaculty,
                             @PathVariable("idGroup") int idGroup, Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("faculty", facultyService.findById(idFaculty));
        model.addAttribute("course", courseService.findById(idCourse));
        model.addAttribute("group", groupService.findById(idGroup));
        return "/students/new";
    }

    @PostMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/")
    public String create(@ModelAttribute("student") Student student) {
        Student result = studentService.save(student);
        return "redirect:/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/" + result.getId();
    }

    @GetMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}/edit")
    public String edit(@PathVariable("idCourse") int idCourse,
                       @PathVariable("idFaculty") int idFaculty,
                       @PathVariable("idGroup") int idGroup,
                       @PathVariable("idStudent") int idStudent, Model model) {
        Student result = studentService.findById(idStudent);
        if (result != null) {
            result.setName(result.getName().trim());
            result.setLastName(result.getLastName().trim());
        }
        model.addAttribute("student", result);
        Faculty faculty = facultyService.findById(idFaculty);
        model.addAttribute("faculty", faculty);
        model.addAttribute("course", courseService.findById(idCourse));
        model.addAttribute("group", groupService.findById(idGroup));

        Map<Integer, String> mapGroupAndCourseOneFaculty = new HashMap<>();
        faculty.getCourses().stream()
                .peek(course -> course.setGroups(courseService.getGroupsOneCourse(course.getId())))
                .collect(Collectors.toList())
                .stream()
                .forEach(course -> course.getGroups()
                        .stream()
                        .forEach(group -> mapGroupAndCourseOneFaculty.put(group.getId(), course.getNumberCourse() + " course, " + group.getNumberGroup() + " group")));

        model.addAttribute("mapGroupAndCourseOneFaculty", mapGroupAndCourseOneFaculty);
        return "/students/edit";
    }

    @PatchMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}")
    public String update(@ModelAttribute("student") Student student,
                         @PathVariable("idStudent") int idStudent,
                         @PathVariable("idCourse") int idCourse,
                         @PathVariable("idFaculty") int idFaculty,
                         @PathVariable("idGroup") int idGroup, Model model) {
        student.setId(idStudent);
        studentService.update(student);
        model.addAttribute("student", student);
        model.addAttribute("faculty", facultyService.findById(idFaculty));
        model.addAttribute("course", courseService.findById(idCourse));
        model.addAttribute("group", groupService.findById(idGroup));
        return "redirect:/faculties/{idFaculty}/courses/" + groupService.findById(student.getGroupId()).getCourseId()
                + "/groups/" + student.getGroupId() + "/student/" + student.getId();
    }

    @DeleteMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/{idStudent}")
    public String delete(@PathVariable("idStudent") int idStudent) {
        studentService.deleteById(idStudent);
        return "redirect:/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}";
    }

}
