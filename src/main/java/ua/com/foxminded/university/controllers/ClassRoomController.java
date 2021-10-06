package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.ClassRoom;
import ua.com.foxminded.university.exception.NotFoundException;
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
        log.info("getAll() called");
        List<ClassRoom> classRooms = classRoomService.findAll();
        model.addAttribute("classrooms", classRooms);
        log.info("Exit: {}", classRooms);
        return "classrooms/getAll";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") int id, Model model) {
        log.info("Enter: findById('{}')", id);
        ClassRoom result = classRoomService.findById(id);
        model.addAttribute("classroom", result);
        log.info("Exit: {}", result);
        return "classrooms/showOneClassroom";
    }

    @GetMapping("/new")
    public String getPageCreateClassroom(Model model) {
        log.info("getPageCreateClassroom() called");
        model.addAttribute("classroom", new ClassRoom());
        return "classrooms/new";
    }

    @PostMapping()
    public String createClassroom(@ModelAttribute("classroom") ClassRoom classRoom) {
        log.info("Enter: createClassroom('{}')", classRoom);
        ClassRoom result = classRoomService.save(classRoom);
        log.info("Exit: {}", result);
        return "redirect:/classrooms/" + result.getId();
    }

    @GetMapping("/{id}/edit")
    public String getPageEdit(Model model, @PathVariable("id") int id) {
        log.info("Enter: getPageEdit('{}')", id);
        ClassRoom result = classRoomService.findById(id);
        model.addAttribute("classroom", result);
        log.info("Exit: {}", result);
        return "classrooms/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("classroom") ClassRoom classRoom,
                         @PathVariable("id") int id, Model model) {
        log.info("Enter: update('{}', '{}')", classRoom, id);
        classRoom.setId(id);
        classRoomService.update(classRoom);
        model.addAttribute("classroom", classRoom);
        log.info("Update('{}') was success", classRoom);
        return "classrooms/showOneClassroom";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        log.info("Enter: delete('{}')", id);
        classRoomService.deleteById(id);
        log.info("delete('{}') was success", id);
        return "redirect:/classrooms";
    }
}