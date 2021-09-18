package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.entities.Lesson;
import ua.com.foxminded.university.service.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;
    private final SubjectService subjectService;
    private final ClassRoomService classRoomService;
    private final GroupService groupService;
    private final EmployeeService employeeService;


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("lessons", lessonService.findAll());
        return "lessons/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        model.addAttribute("lessonDto", lessonService.findLessonDtoById(id));
        return "/lessons/ShowOneLesson";
    }

    @GetMapping("/new")
    public String newLesson(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("classrooms", classRoomService.findAll());
        model.addAttribute("groups", groupService.findAll());
        return "/lessons/new";
    }

    @PostMapping
    public String create(@RequestParam("subjectId") Integer subjectId,
                         @RequestParam("dateTime")
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                         @RequestParam("duration") Integer duration,
                         @RequestParam("classRoomId") Integer classRoomId,
                         @RequestParam("lessonForGroups") List<Integer> lessonForGroups) {
        Lesson result = lessonService.save(new Lesson(localDateTime, duration, classRoomId, subjectId));
        return "redirect:/lessons/" + result.getId();
    }

    @GetMapping("/{idLesson}/edit")
    public String edit(@PathVariable("idLesson") Integer idLesson, Model model) {
        model.addAttribute("lesson", lessonService.findLessonDtoById(idLesson));
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("classrooms", classRoomService.findAll());
        model.addAttribute("groups", groupService.findAll());
        return "/lessons/edit";
    }

    @PatchMapping("/{idLesson}")
    public String update(@RequestParam("subjectId") Integer subjectId,
                         @RequestParam("dateTime")
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                         @RequestParam("duration") Integer duration,
                         @RequestParam("classRoomId") Integer classRoomId,
                         @RequestParam("lessonForGroups") List<Integer> lessonForGroups,
                         @PathVariable("idLesson") Integer idLesson) {
        Lesson lesson = new Lesson(localDateTime, duration, classRoomId, subjectId);
        lesson.setId(idLesson);
        lessonService.update(lesson, lessonForGroups);
        return "redirect:/lessons/" + idLesson;
    }

    @DeleteMapping("/{idLesson}")
    public String delete(@PathVariable("idLesson") Integer idLesson) {
        lessonService.deleteById(idLesson);
        return "redirect:/lessons";
    }

    @GetMapping("/{idGroup}/lessons-group/search")
    public String searchLessonsForGroup(@PathVariable("idGroup") Integer idGroup, Model model) {
        model.addAttribute("group", groupService.findById(idGroup));
        return "/lessons/searchLessonsForGroup";
    }

    @GetMapping("/{idGroup}/lessons-group")
    public String getLessonsForGroup(@PathVariable("idGroup") Integer idGroup,
                                     @RequestParam("startTime")
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                     @RequestParam("endTime")
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                     Model model) {
        model.addAttribute("LessonInfoDtoList", lessonService.getLessonsBetweenDatesForGroup(startTime, endTime, idGroup));
        return "lessons/getLessonsBySearch";
    }

    @GetMapping("/{idTeacher}/lessons-teacher/search")
    public String searchLessonsForTeacher(@PathVariable("idTeacher") Integer idTeacher, Model model) {
        model.addAttribute("teacher", employeeService.findById(idTeacher));
        return "/lessons/searchLessonsForTeacher";
    }

    @GetMapping("/{idTeacher}/lessons-teacher")
    public String getLessonsForTeacher(@PathVariable("idTeacher") Integer idTeacher,
                                       @RequestParam("startTime")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                       @RequestParam("endTime")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                       Model model) {
        model.addAttribute("LessonInfoDtoList", lessonService.getLessonsBetweenDatesForTeacher(startTime, endTime, idTeacher));
        return "lessons/getLessonsBySearch";
    }

}
