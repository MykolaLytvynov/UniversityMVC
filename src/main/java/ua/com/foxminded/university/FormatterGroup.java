package ua.com.foxminded.university;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.FacultyService;
import ua.com.foxminded.university.service.GroupService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FormatterGroup {
    private final GroupService groupService;
    private final CourseService courseService;
    private final FacultyService facultyService;

    public Map<Integer, String> getAllDataAllGroup() {
        Map<Integer, String> mapGroupAndCourseAndFaculty = new HashMap<>();
        for (Group group : groupService.findAll()) {
            Course course = courseService.findById(group.getCourseId());
            Faculty faculty = facultyService.findById(course.getFacultyId());
            mapGroupAndCourseAndFaculty.put(group.getId(), faculty.getName() + ": " + course.getNumberCourse() + " course, " + group.getNumberGroup() + " group");
        }
        return mapGroupAndCourseAndFaculty;
    }

    public Map<Integer, String> getAllDataAllGroupOneFaculty(Integer facultyId) {
        Faculty faculty = facultyService.findById(facultyId);

        Map<Integer, String> mapAllDataAllGroupOneFaculty = new HashMap<>();
        List<Group> groupsOneFaculty = new ArrayList<>();

        facultyService.getCoursesOneFaculty(facultyId).stream()
                .peek(course -> courseService.getGroupsOneCourse(course.getId()).stream()
                        .forEach(group -> groupsOneFaculty.add(group)));
        for (Group group : groupsOneFaculty) {
            Course course = courseService.findById(group.getCourseId());
            mapAllDataAllGroupOneFaculty.put(group.getId(), faculty.getName() + ": " + course.getNumberCourse() + " course, " + group.getNumberGroup() + " group");
        }
        return mapAllDataAllGroupOneFaculty;
    }

    public String getDataOneGroup(Integer groupId) {
        Group group = groupService.findById(groupId);
        Course course = courseService.findById(group.getCourseId());
        Faculty faculty = facultyService.findById(course.getFacultyId());
        return faculty.getName() + ": " + course.getNumberCourse() + " course, " + group.getNumberGroup() + " group";
    }
}
