package ua.com.foxminded.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import ua.com.foxminded.university.configuration.ApplicationConfig;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.entities.Lesson;
import ua.com.foxminded.university.entities.*;


import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class University {
    private List<Faculty> faculties = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private List<Lesson> timetable = new ArrayList<>();
    private List<ClassRoom> classRooms = new ArrayList<>();

    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        SpringApplication.run(University.class);
//        context.getBean("positionService", PositionService.class).count();
//        context.getBean("positionService", PositionService.class).findAll();
//
//        context.getBean("subjectService", SubjectService.class).existsById(2);
//        context.getBean("studentService", StudentService.class).findAll();
    }
}
