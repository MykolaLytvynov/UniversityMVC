package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.ClassRoom;
import ua.com.foxminded.university.service.ClassRoomService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/classrooms")
public class ClassRoomController {

    private final ClassRoomService classRoomService;

    @GetMapping
    public String getAll(Model model) {
        List<ClassRoom> classRooms = classRoomService.findAll();
        model.addAttribute("classrooms", classRooms);
        return "classrooms/getAll";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") int id, Model model) {
        ClassRoom result = classRoomService.findById(id);
        model.addAttribute("classroom", result);
        return "classrooms/showOneClassroom";
    }

    @GetMapping("/new")
    public String newClassroom(Model model) {
        model.addAttribute("classroom", new ClassRoom());
        return "classrooms/new";
    }

    @PostMapping()
    public String createClassroom(@ModelAttribute("classroom") ClassRoom classRoom) {
        ClassRoom result = classRoomService.save(classRoom);
        return "redirect:/classrooms/" + result.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        ClassRoom result = classRoomService.findById(id);
        if(result != null) {
            result.setName(result.getName().trim());
            result.setDescription(result.getDescription().trim());
        }
        model.addAttribute("classroom", result);
        return "classrooms/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("classroom") ClassRoom classRoom,
                         @PathVariable("id") int id) {
        classRoom.setId(id);
        classRoomService.update(classRoom);
        return "classrooms/showOneClassroom";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        classRoomService.deleteById(id);
        return "redirect:/classrooms";
    }

}
