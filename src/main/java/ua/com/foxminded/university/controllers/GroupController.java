package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.FacultyService;
import ua.com.foxminded.university.service.GroupService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faculties/{idFaculty}/courses/{idCourse}/groups")
public class GroupController {
    private final GroupService groupService;
    private final CourseService courseService;
    private final FacultyService facultyService;


    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        model.addAttribute("group", groupService.findById(id));
        return "/groups/showOneGroup";
    }

    @GetMapping("/new")
    public String newGroup(@PathVariable("idCourse") int idCourse,
                           @PathVariable("idFaculty") int idFaculty, Model model) {
        model.addAttribute("group", new Group());
        model.addAttribute("faculty", facultyService.findById(idFaculty));
        model.addAttribute("course", courseService.findById(idCourse));
        return "groups/new";
    }

    @PostMapping()
    public String createGroup(@ModelAttribute("group") Group group) {
        Group result = groupService.save(group);
        return "redirect:/faculties/{idFaculty}/courses/{idCourse}/groups/" + result.getId();
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        groupService.deleteById(id);
        return "redirect:/faculties/{idFaculty}/courses/{idCourse}";
    }
}
