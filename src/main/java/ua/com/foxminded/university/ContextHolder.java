package ua.com.foxminded.university;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.ClassRoomDAO;

public final class ContextHolder {

private final ClassRoomDAO classRoomDAO;


    public ContextHolder(ClassRoomDAO classRoomDAO) {
        this.classRoomDAO = classRoomDAO;
    }

    public static ContextHolder connectorBuilder() {
        ApplicationContext context = new ClassPathXmlApplicationContext("my-beans.xml");
        JdbcTemplate jdbcTemplate = (JdbcTemplate)context.getBean("jdbcTemplate");

        ClassRoomDAO classRoomDAO = new ClassRoomDAO(jdbcTemplate);
        return new ContextHolder(classRoomDAO);
    }

    public ClassRoomDAO getClassRoomDAO() {
        return classRoomDAO;
    }
}
