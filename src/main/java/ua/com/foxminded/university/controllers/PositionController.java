package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Position;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.service.EmployeeService;
import ua.com.foxminded.university.service.PositionService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/position")
public class PositionController {
    private final PositionService positionService;
    private final EmployeeService employeeService;

    @GetMapping()
    public String getAll(Model model) {
        log.info("getAll() called");
        List<Position> result = positionService.findAll();
        model.addAttribute("positions", result);
        log.info("Exit: {}", result);
        return "position/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        log.info("Enter: getById('{}')", id);
        Position result = positionService.findById(id);
        model.addAttribute("position", result);
        List<Employee> allEmploeesOnePosition = employeeService.getAllEmploeesOnePosition(id);
        model.addAttribute("employeesOnePosition", allEmploeesOnePosition);
        log.info("Exit: {}, {}", result, allEmploeesOnePosition);
        return "position/showOnePosition";
    }

    @GetMapping("/new")
    public String getPageCreatePosition(Model model) {
        log.info("getPageCreatePosition() called");
        model.addAttribute("position", new Position());
        return "position/new";
    }

    @PostMapping
    public String createPosition(@ModelAttribute("position") Position position, Model model) {
        log.info("Enter: createFaculty('{}')", position);
        Position result = positionService.save(position);
        log.info("Exit: {}", result);
        return "position/showOnePosition";
    }

    @GetMapping("/{id}/edit")
    public String getPageEdit(@PathVariable("id") int id, Model model) {
        log.info("Enter: getPageEdit('{}')", id);
        Position result = positionService.findById(id);
        model.addAttribute("position", result);
        log.info("Exit: {}", result);
        return "position/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("position") Position position, @PathVariable("id") int id, Model model) {
        log.info("Enter: update('{}', '{}')", position, id);
        position.setId(id);
        positionService.update(position);
        model.addAttribute("position", position);
        List<Employee> allEmploeesOnePosition = employeeService.getAllEmploeesOnePosition(position.getId());
        model.addAttribute("employeesOnePosition", allEmploeesOnePosition);
        log.info("Exit: {}, {}", position, allEmploeesOnePosition);
        return "position/showOnePosition";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        log.info("Enter: delete('{}')", id);
        positionService.deleteById(id);
        log.info("delete('{}') was success", id);
        return "redirect:/position/";
    }
}
