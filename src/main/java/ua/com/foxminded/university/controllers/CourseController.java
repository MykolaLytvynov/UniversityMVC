package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.FacultyService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faculties/{idFaculty}/courses")
public class CourseController {
    private final CourseService courseService;
    private final FacultyService facultyService;

    @GetMapping("/{id}")
    public String getById(@PathVariable("idFaculty") int idFaculty, @PathVariable("id") int id, Model model) {
        model.addAttribute("course", courseService.findById(id));
        model.addAttribute("faculty", facultyService.findById(idFaculty));
        return "/courses/showOneCourse";
    }


    @GetMapping("/new")
    public String newCourse(@PathVariable("idFaculty") int idFaculty, Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("faculty", facultyService.findById(idFaculty));

//        model.addAttribute("idFaculty", idFaculty);
        return "/courses/new";
    }

    @PostMapping()
    public String createCourse(@ModelAttribute("course") Course course) {
        Course result = courseService.save(course);
        return "redirect:/faculties/{idFaculty}/courses/" + result.getId();
    }
}
