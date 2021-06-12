package ua.com.foxminded.university;

import ua.com.foxminded.university.entities.ClassRoom;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.entities.Lesson;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.entities.person.Teacher;

import java.util.ArrayList;
import java.util.List;

public class University {
    private List<Faculty> faculties = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private List<Lesson> timetable = new ArrayList<>();
    private List<ClassRoom> classRooms = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(Teacher.builder().position("Физрук").salary(10_000).id(13).lastName("Коноплёв").name("Гашиш").build());
    }
}
