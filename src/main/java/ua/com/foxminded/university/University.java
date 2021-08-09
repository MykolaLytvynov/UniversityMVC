package ua.com.foxminded.university;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.foxminded.university.configuration.ApplicationConfig;
import ua.com.foxminded.university.configuration.MyWebMvcConfig;
import ua.com.foxminded.university.dao.*;
import ua.com.foxminded.university.entities.*;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.entities.person.Student;
import ua.com.foxminded.university.service.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class University {
    private List<Faculty> faculties = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private List<Lesson> timetable = new ArrayList<>();
    private List<ClassRoom> classRooms = new ArrayList<>();

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

//        context.getBean("positionService", PositionService.class).count();
//        context.getBean("positionService", PositionService.class).findAll();
//
//        context.getBean("subjectService", SubjectService.class).existsById(2);
//        context.getBean("studentService", StudentService.class).findAll();
    }
}
