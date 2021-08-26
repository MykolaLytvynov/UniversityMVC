package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Position;
import ua.com.foxminded.university.service.EmployeeService;
import ua.com.foxminded.university.service.PositionService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/position")
public class PositionController {
    private final PositionService positionService;
    private final EmployeeService employeeService;

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("positions", positionService.findAll());
        return "position/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        model.addAttribute("position", positionService.findById(id));
        model.addAttribute("employeesOnePosition", employeeService.getAllEmploeesOnePosition(id));
        return "position/showOnePosition";
    }

    @GetMapping("/new")
    public String newPosition(Model model) {
        model.addAttribute("position", new Position());
        return "position/new";
    }

    @PostMapping
    public String createPosition(@ModelAttribute("position") Position position) {
        Position result = positionService.save(position);
        return "redirect:/position/" + result.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Position position = positionService.findById(id);
        if(position != null) {
            position.setName(position.getName().trim());
        }

        model.addAttribute("position", position);
        return "position/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("position") Position position, @PathVariable("id") int id, Model model) {
        position.setId(id);
        positionService.update(position);
        model.addAttribute("position", position);
        return "position/showOnePosition";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        positionService.deleteById(id);
        return "redirect:/position/";
    }
}
