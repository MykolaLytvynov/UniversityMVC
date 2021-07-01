package ua.com.foxminded.university.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.entities.Lesson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LessonMapper implements RowMapper<Lesson> {
    @Override
    public Lesson mapRow(ResultSet resultSet, int i) throws SQLException {
        Lesson lesson = new Lesson(resultSet.getInt("subjectId"),
                resultSet.getObject("dateTime", LocalDateTime.class),
                resultSet.getInt("duration"),
                resultSet.getInt("classRoomId"));
            lesson.setId(resultSet.getInt("id"));
        return lesson;
    }
}
