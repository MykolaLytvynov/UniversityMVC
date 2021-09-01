package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.FormatterGroup;
import ua.com.foxminded.university.dto.GroupInfoDto;
import ua.com.foxminded.university.dto.LessonInfoDto;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Lesson;
import ua.com.foxminded.university.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;
    private final SubjectService subjectService;
    private final ClassRoomService classRoomService;
    private final GroupService groupService;
    private final FormatterGroup formatterGroup;
    private final EmployeeService employeeService;
    private final CourseService courseService;
    private final FacultyService facultyService;


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("lessons", lessonService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("classrooms", classRoomService.findAll());

        List<LessonInfoDto> lessonInfoDtoList = new ArrayList<>();
        for (Lesson lesson : lessonService.findAll()) {
            LessonInfoDto lessonInfoDto = new LessonInfoDto(lesson.getId(), lesson.getDateTime(), lesson.getDuration());

            lessonInfoDto.setClassRoom(classRoomService.findById(lesson.getClassRoomId()));
            lessonInfoDto.setGroups(lesson.getGroupsIdOneLesson()
                    .stream()
                    .map(idGroup -> groupService.findById(idGroup))
                    .collect(Collectors.toList()));
            Course course = courseService.findById(lessonInfoDto.getGroups()
                    .stream()
                    .findFirst().orElse(null)
                    .getCourseId());
            lessonInfoDto.setCourse(course);
            lessonInfoDto.setFaculty(facultyService.findById(course.getFacultyId()));
            lessonInfoDto.setSubject(subjectService.findById(lesson.getSubjectId()));
            lessonInfoDtoList.add(lessonInfoDto);
        }
        model.addAttribute("lessonInfoDtoList", lessonInfoDtoList);


        return "lessons/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        Lesson lesson = lessonService.findById(id);
        LessonInfoDto lessonInfoDto = new LessonInfoDto(lesson.getId(), lesson.getDateTime(), lesson.getDuration());

        lessonInfoDto.setClassRoom(classRoomService.findById(lesson.getClassRoomId()));
        lessonInfoDto.setGroups(lesson.getGroupsIdOneLesson()
                .stream()
                .map(idGroup -> groupService.findById(idGroup))
                .collect(Collectors.toList()));
        Course course = courseService.findById(lessonInfoDto.getGroups()
                .stream()
                .findFirst().orElse(null)
                .getCourseId());
        lessonInfoDto.setCourse(course);
        lessonInfoDto.setFaculty(facultyService.findById(course.getFacultyId()));
        lessonInfoDto.setSubject(subjectService.findById(lesson.getSubjectId()));

        model.addAttribute("lessonInfoDto", lessonInfoDto);
        return "/lessons/ShowOneLesson";
    }

    @GetMapping("/new")
    public String newLesson(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("classrooms", classRoomService.findAll());

        List<GroupInfoDto> groupInfoDtoList = new ArrayList<>();
        for (Group group : groupService.findAll()) {
            Course course = courseService.findById(group.getCourseId());
            Faculty faculty = facultyService.findById(course.getFacultyId());
            groupInfoDtoList.add(new GroupInfoDto(group.getId(), group.getNumberGroup(), group.getStudents(),
                    course, faculty));
        }
        model.addAttribute("groupInfoDtoList", groupInfoDtoList);
        return "/lessons/new";
    }


    @PostMapping
    public String create(@RequestParam("subjectId") Integer subjectId,
                         @RequestParam("dateTime")
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                         @RequestParam("duration") Integer duration,
                         @RequestParam("classRoomId") Integer classRoomId,
                         @RequestParam("lessonForGroups") List<Integer> lessonForGroups) {
        Lesson result = lessonService.save(new Lesson(subjectId, localDateTime, duration, classRoomId));
        for (Integer groupId : lessonForGroups) {
            lessonService.addGroupsToLesson(groupId, result.getId());
        }
        return "redirect:/lessons/" + result.getId();
    }


    @GetMapping("/{idLesson}/edit")
    public String edit(@PathVariable("idLesson") Integer idLesson, Model model) {
        Lesson result = lessonService.findById(idLesson);
        model.addAttribute("lesson", result);
        result.setGroupsIdOneLesson(lessonService.getAllGroupsOneLesson(idLesson));
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("classrooms", classRoomService.findAll());

        List<GroupInfoDto> groupInfoDtoList = new ArrayList<>();
        for (Group group : groupService.findAll()) {
            Course course = courseService.findById(group.getCourseId());
            Faculty faculty = facultyService.findById(course.getFacultyId());
            groupInfoDtoList.add(new GroupInfoDto(group.getId(), group.getNumberGroup(), group.getStudents(),
                    course, faculty));
        }
        model.addAttribute("groupInfoDtoList", groupInfoDtoList);
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
        Lesson lesson = new Lesson(subjectId, localDateTime, duration, classRoomId);
        lesson.setId(idLesson);
        lessonService.update(lesson);
        if (!lessonForGroups.isEmpty()) {
            lessonService.deleteGroupsFromLesson(idLesson);
            for (Integer groupId : lessonForGroups) {
                lessonService.addGroupsToLesson(groupId, idLesson);
            }
        }
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
        List<Lesson> result = lessonService.getLessonsBetweenDatesForGroup(startTime, endTime, idGroup);
        model.addAttribute("lessons", result);
        model.addAttribute("classrooms", classRoomService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
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
        List<Lesson> result = lessonService.getLessonsBetweenDatesForTeacher(startTime, endTime, idTeacher);
        model.addAttribute("lessons", result);
        model.addAttribute("classrooms", classRoomService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        return "lessons/getLessonsBySearch";
    }


}
