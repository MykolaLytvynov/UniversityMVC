package ua.com.foxminded.university;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.configuration.ApplicationConfig;
import ua.com.foxminded.university.dao.ClassRoomDAO;
import ua.com.foxminded.university.dao.CourseDAO;
import ua.com.foxminded.university.dao.FacultyDAO;

public final class ContextHolder {

    private final ClassRoomDAO classRoomDAO;
    private final FacultyDAO facultyDAO;
    private final CourseDAO courseDAO;


    public ContextHolder(ClassRoomDAO classRoomDAO, FacultyDAO facultyDAO, CourseDAO courseDAO) {
        this.classRoomDAO = classRoomDAO;
        this.facultyDAO = facultyDAO;
        this.courseDAO = courseDAO;
    }

    public static ContextHolder connectorBuilder() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        ClassRoomDAO classRoomDAO = context.getBean("classRoomDAO", ClassRoomDAO.class);
        FacultyDAO facultyDAO = context.getBean("facultyDAO", FacultyDAO.class);
        CourseDAO courseDAO = context.getBean("courseDAO", CourseDAO.class);


        context.close();
        return new ContextHolder(classRoomDAO, facultyDAO, courseDAO);
    }


    public ClassRoomDAO getClassRoomDAO() {
        return classRoomDAO;
    }

    public FacultyDAO getFacultyDAO() {
        return facultyDAO;
    }

    public CourseDAO getCourseDAO() {
        return courseDAO;
    }
}
