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

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final FacultyService facultyService;
    private final CourseService courseService;
    private final GroupService groupService;

    @GetMapping("/students")
    public String getAll(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "/students/getAll";
    }

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
        return "redirect:/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/"+result.getId();
    }

//    @PostMapping("/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/")
//    public String create(@RequestParam String name,
//                         @RequestParam String lastName,
//                         @RequestParam Integer groupId) {
//        Student result = studentService.save(Student.builder().name(name).lastName(lastName).groupId(groupId).build());
//        return "redirect:/faculties/{idFaculty}/courses/{idCourse}/groups/{idGroup}/student/" + result.getId();
//    }


//    @PostMapping("/create")
//    public String addNew(@RequestParam String firstName,
//                         @RequestParam String lastName) {
//        studentService.save(Student.builder().name(firstName).lastName(lastName).build());
//        return "getAll";
//    }
//
//    @GetMapping("update/{id}")
//    public String edit(@PathVariable Integer id, Model model) {
//        model.addAttribute("student", studentService.findById(id));
//        return "edit/editStudent";
//    }
//
//    @PostMapping("update")
//    public String update(@RequestParam Integer id,
//                         @RequestParam String firstName,
//                         @RequestParam String lastName) {
//        Student student = studentService.findById(id);
//        student.setName(firstName);
//        student.setLastName(lastName);
//        studentService.save(student);
//        return "getAll";
//    }
//
//    @GetMapping("/delete")
//    public String delete(@PathVariable Integer id) {
//        studentService.delete(studentService.findById(id));
//        return "getAll";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteById(@PathVariable Integer id) {
//        studentService.delete(studentService.findById(id));
//        return "getAll";
//    }

}
