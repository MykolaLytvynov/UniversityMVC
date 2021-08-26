package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.FormatterGroup;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Lesson;
import ua.com.foxminded.university.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;
    private final SubjectService subjectService;
    private final ClassRoomService classRoomService;
    private final GroupService groupService;
    private final FormatterGroup formatterGroup;


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("lessons", lessonService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("classrooms", classRoomService.findAll());
        model.addAttribute("mapGroupAndCourseAndFaculty", formatterGroup.getAllDataAllGroup());

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        model.addAttribute("formatterTime", formatter);
        return "lessons/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        Lesson lesson = lessonService.findById(id);
        model.addAttribute("lesson", lesson);
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("classrooms", classRoomService.findAll());

        List<String> dataAllGroupOneLesson = new ArrayList<>();
        lesson.getGroupsIdOneLesson().stream()
                .forEach(integer -> dataAllGroupOneLesson.add(formatterGroup.getDataOneGroup(integer)));

        model.addAttribute("dataAllGroupOneLesson", dataAllGroupOneLesson);
        return "/lessons/ShowOneLesson";
    }

    @GetMapping("/new")
    public String newLesson(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("classrooms", classRoomService.findAll());
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("mapGroupAndCourseAndFaculty", formatterGroup.getAllDataAllGroup());

        return "/lessons/new";
    }


    @PostMapping
    public String create(@RequestParam("subjectId") int subjectId,
                         @RequestParam("dateTime")
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                         @RequestParam("duration") int duration,
                         @RequestParam("classRoomId") int classRoomId,
                         @RequestParam("lessonForGroups") List<Integer> lessonForGroups) {
        Lesson result = lessonService.save(new Lesson(subjectId, localDateTime, duration, classRoomId));
        for (Integer groupId : lessonForGroups) {
            lessonService.addGroupsToLesson(groupId, result.getId());
        }
        return "redirect:/lessons/" + result.getId();
    }

//    @GetMapping("/with-data")
//    public String getLessonsBetweenData(@RequestParam("startTime")
//                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
//                                        @RequestParam("endTime")
//                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
//                                        Model model) {
//
//        model.addAttribute("lessons", lessonService.getLessonsBetweenData(start, end));
//        model.addAttribute("subjects", subjectService.findAll());
//        model.addAttribute("classrooms", classRoomService.findAll());
//
//        return "lessons/getAll";
//    }

}
