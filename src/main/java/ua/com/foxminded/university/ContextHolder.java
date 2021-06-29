package ua.com.foxminded.university;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.configuration.ApplicationConfig;
import ua.com.foxminded.university.dao.*;

public final class ContextHolder {

    private final ClassRoomDAO classRoomDAO;
    private final FacultyDAO facultyDAO;
    private final CourseDAO courseDAO;
    private final GroupDAO groupDAO;
    private final StudentDAO studentDAO;
    private final PositionDAO positionDAO;
    private final EmployeeDAO employeeDAO;
    private final SubjectDAO subjectDAO;
    private final LessonDAO lessonDAO;


    public ContextHolder(ClassRoomDAO classRoomDAO, FacultyDAO facultyDAO, CourseDAO courseDAO, GroupDAO groupDAO,
                         StudentDAO studentDAO, PositionDAO positionDAO, EmployeeDAO employeeDAO, SubjectDAO subjectDAO,
                         LessonDAO lessonDAO) {
        this.classRoomDAO = classRoomDAO;
        this.facultyDAO = facultyDAO;
        this.courseDAO = courseDAO;
        this.groupDAO = groupDAO;
        this.studentDAO = studentDAO;
        this.positionDAO = positionDAO;
        this.employeeDAO = employeeDAO;
        this.subjectDAO = subjectDAO;
        this.lessonDAO = lessonDAO;
    }

    public static ContextHolder connectorBuilder() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        ClassRoomDAO classRoomDAO = context.getBean("classRoomDAO", ClassRoomDAO.class);
        FacultyDAO facultyDAO = context.getBean("facultyDAO", FacultyDAO.class);
        CourseDAO courseDAO = context.getBean("courseDAO", CourseDAO.class);
        GroupDAO groupDAO = context.getBean("groupDAO", GroupDAO.class);
        StudentDAO studentDAO = context.getBean("studentDAO", StudentDAO.class);
        PositionDAO positionDAO = context.getBean("positionDAO", PositionDAO.class);
        EmployeeDAO employeeDAO = context.getBean("employeeDAO", EmployeeDAO.class);
        SubjectDAO subjectDAO = context.getBean("subjectDAO", SubjectDAO.class);
        LessonDAO lessonDAO = context.getBean("lessonDAO", LessonDAO.class);


        context.close();
        return new ContextHolder(classRoomDAO, facultyDAO, courseDAO, groupDAO, studentDAO, positionDAO,
                employeeDAO, subjectDAO, lessonDAO);
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

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public StudentDAO getStudentDAO() {
        return studentDAO;
    }

    public PositionDAO getPositionDAO() {
        return positionDAO;
    }

    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }

    public SubjectDAO getSubjectDAO() {
        return subjectDAO;
    }

    public LessonDAO getLessonDAO() {
        return lessonDAO;
    }
}
