package ua.com.foxminded.university;

import ua.com.foxminded.university.dao.ClassRoomDAO;
import ua.com.foxminded.university.entities.ClassRoom;
import ua.com.foxminded.university.entities.Course;
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

    public static void main(String[] args) {

        ContextHolder contextHolder = ContextHolder.connectorBuilder();

        System.out.println(contextHolder.getClassRoomDAO().existsById(1));
        System.out.println(contextHolder.getClassRoomDAO().existsById(2));
        System.out.println(contextHolder.getClassRoomDAO().existsById(15));

        List<Faculty> faculties = contextHolder.getFacultyDAO().findAll();
        for (Faculty faculty : faculties) {
            System.out.println(faculty);
        }


//        Faculty faculty = contextHolder.getFacultyDAO().findById(32);
//        faculty.setName(faculty.getName().trim());
//        faculty.setDescription(faculty.getDescription().trim());
//
//        System.out.println(faculty);
    }
}
