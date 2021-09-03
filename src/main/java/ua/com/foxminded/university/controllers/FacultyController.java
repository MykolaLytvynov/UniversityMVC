package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.service.FacultyService;
import ua.com.foxminded.university.service.CourseService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService facultyService;
    private final CourseService courseService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("faculty", facultyService.findAll());
        return "/faculties/getAll";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") int id, Model model) {
        Faculty faculty = facultyService.findById(id);
        faculty.getCourses()
                .stream()
                .forEach(course -> course.setGroups(courseService.findById(course.getId()).getGroups()));
        model.addAttribute("faculty", faculty);
        return "/faculties/showOneFaculty";
    }

    @GetMapping("/new")
    public String newFaculty(Model model) {
        model.addAttribute("faculty", new Faculty());
        return "/faculties/new";
    }

    @PostMapping()
    public String createFaculty(@ModelAttribute("faculty") Faculty faculty) {
        Faculty result = facultyService.save(faculty);
        return "redirect:/faculties/" + result.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        Faculty result = facultyService.findById(id);
        if (result != null) {
            result.setName(result.getName().trim());
            result.setDescription(result.getDescription().trim());
        }
        model.addAttribute("faculty", result);
        return "/faculties/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("faculty") Faculty faculty,
                         @PathVariable("id") int id, Model model) {
        faculty.setId(id);
        facultyService.update(faculty);
        return "redirect:/faculties/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        facultyService.deleteById(id);
        return "redirect:/faculties";
    }

}
