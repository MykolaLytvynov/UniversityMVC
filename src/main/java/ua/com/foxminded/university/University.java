package ua.com.foxminded.university;

import ua.com.foxminded.university.entities.ClassRoom;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.entities.Lesson;
import ua.com.foxminded.university.entities.person.Employee;

import java.util.ArrayList;
import java.util.List;

public class University {
    private List<Faculty> faculties = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private List<Lesson> timetable = new ArrayList<>();
    private List<ClassRoom> classRooms = new ArrayList<>();

}
