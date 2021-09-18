package ua.com.foxminded.university.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Lesson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class LessonMapper implements RowMapper<Lesson> {
    @Override
    public Lesson mapRow(ResultSet resultSet, int i) throws SQLException {
        Lesson lesson = new Lesson(resultSet.getObject("dateTime", LocalDateTime.class),
                resultSet.getInt("duration"),
                resultSet.getInt("subject_id"),
                resultSet.getInt("classroom_id"));
        lesson.setId(resultSet.getInt("id"));
        lesson.setClassRoomName(resultSet.getString("name_classroom"));
        lesson.setSubjectName(resultSet.getString("name_subject"));
        return lesson;
    }
}
