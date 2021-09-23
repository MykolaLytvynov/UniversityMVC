package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.dto.LessonDto;
import ua.com.foxminded.university.entities.ClassRoom;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Lesson;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.service.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
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
        log.info("getAll() called");
        List<LessonDto> result = lessonService.findAll();
        model.addAttribute("lessons", result);
        log.info("Exit: {}", result);
        return "lessons/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        log.info("Enter: getById('{}')", id);
        LessonDto result = lessonService.findLessonDtoById(id);
        model.addAttribute("lessonDto", result);
        log.info("Exit: {}", result);
        return "/lessons/ShowOneLesson";
    }

    @GetMapping("/new")
    public String getPageCreateLesson(Model model) {
        log.info("getPageCreateEmployee() called");
        List<Subject> allSubjects = subjectService.findAll();
        model.addAttribute("subjects", allSubjects);
        List<ClassRoom> allClassrooms = classRoomService.findAll();
        model.addAttribute("classrooms", allClassrooms);
        List<Group> allGroups = groupService.findAll();
        model.addAttribute("groups", allGroups);
        log.info("Exit: {}, {}, {}", allSubjects, allClassrooms, allGroups);
        return "/lessons/new";
    }

    @PostMapping
    public String create(@RequestParam("subjectId") Integer subjectId,
                         @RequestParam("dateTime")
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                         @RequestParam("duration") Integer duration,
                         @RequestParam("classRoomId") Integer classRoomId,
                         @RequestParam("lessonForGroups") List<Integer> lessonForGroups, Model model) {
        log.info("Enter: create('{}', '{}', '{}', '{}', '{}')", subjectId, localDateTime, duration, classRoomId, lessonForGroups);
        Lesson result = lessonService.save(new Lesson(localDateTime, duration, classRoomId, subjectId));
        model.addAttribute("lessonDto", result);
        log.info("Exit: {}", result);
        return "/lessons/ShowOneLesson";
    }

    @GetMapping("/{idLesson}/edit")
    public String getPageEdit(@PathVariable("idLesson") Integer idLesson, Model model) {
        log.info("Enter: getPageEdit('{}')", idLesson);
        LessonDto lesson = lessonService.findLessonDtoById(idLesson);
        model.addAttribute("lesson", lesson);
        List<Subject> allSubjects = subjectService.findAll();
        model.addAttribute("subjects", allSubjects);
        List<ClassRoom> allClassrooms = classRoomService.findAll();
        model.addAttribute("classrooms", allClassrooms);
        List<Group> allGroups = groupService.findAll();
        model.addAttribute("groups", allGroups);
        log.info("Exit: {}, {}, {}, {}", lesson, allSubjects, allClassrooms, allGroups);
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
        log.info("Enter: update('{}', '{}', '{}', '{}', '{}')", subjectId, localDateTime, duration, classRoomId, lessonForGroups, idLesson);
        Lesson lesson = new Lesson(localDateTime, duration, classRoomId, subjectId);
        lesson.setId(idLesson);
        lessonService.update(lesson, lessonForGroups);
        log.info("update lesson was success");
        return "redirect:/lessons/" + idLesson;
    }

    @DeleteMapping("/{idLesson}")
    public String delete(@PathVariable("idLesson") Integer idLesson) {
        log.info("Enter: delete('{}')", idLesson);
        lessonService.deleteById(idLesson);
        log.info("delete('{}') was success", idLesson);
        return "redirect:/lessons";
    }

    @GetMapping("/{idGroup}/lessons-group/search")
    public String getPageSearchLessonsForGroup(@PathVariable("idGroup") Integer idGroup, Model model) {
        log.info("Enter: getPageSearchLessonsForGroup('{}')", idGroup);
        Group group = groupService.findById(idGroup);
        model.addAttribute("group", group);
        log.info("Exit: {}", group);
        return "/lessons/searchLessonsForGroup";
    }

    @GetMapping("/{idGroup}/lessons-group")
    public String getLessonsForGroup(@PathVariable("idGroup") Integer idGroup,
                                     @RequestParam("startTime")
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                     @RequestParam("endTime")
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                     Model model) {
        log.info("Enter: getLessonsForGroup('{}', '{}', '{}')", idGroup, startTime, endTime);
        List<LessonDto> result = lessonService.getLessonsBetweenDatesForGroup(startTime, endTime, idGroup);
        model.addAttribute("LessonInfoDtoList", result);
        log.info("Exit: {}", result);
        return "lessons/getLessonsBySearch";
    }

    @GetMapping("/{idTeacher}/lessons-teacher/search")
    public String getPageSearchLessonsForTeacher(@PathVariable("idTeacher") Integer idTeacher, Model model) {
        log.info("Enter: getPageSearchLessonsForTeacher('{}')", idTeacher);
        Employee employee = employeeService.findById(idTeacher);
        model.addAttribute("teacher", employee);
        log.info("Exit: {}", employee);
        return "/lessons/searchLessonsForTeacher";
    }

    @GetMapping("/{idTeacher}/lessons-teacher")
    public String getLessonsForTeacher(@PathVariable("idTeacher") Integer idTeacher,
                                       @RequestParam("startTime")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                       @RequestParam("endTime")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                       Model model) {
        log.info("Enter: getLessonsForGroup('{}', '{}', '{}')", idTeacher, startTime, endTime);
        List<LessonDto> result = lessonService.getLessonsBetweenDatesForTeacher(startTime, endTime, idTeacher);
        model.addAttribute("LessonInfoDtoList", result);
        log.info("Exit: {}", result);
        return "lessons/getLessonsBySearch";
    }

}
