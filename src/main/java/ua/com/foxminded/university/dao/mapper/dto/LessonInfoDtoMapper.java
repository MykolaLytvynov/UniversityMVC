package ua.com.foxminded.university.dao.mapper.dto;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dto.LessonInfoDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class LessonInfoDtoMapper implements RowMapper<LessonInfoDto> {
    @Override
    public LessonInfoDto mapRow(ResultSet resultSet, int i) throws SQLException {
        LessonInfoDto lessonInfoDto = new LessonInfoDto(resultSet.getInt("id"),
                resultSet.getObject("dateTime", LocalDateTime.class),
                resultSet.getInt("duration"),
                resultSet.getInt("subject_id"),
                resultSet.getInt("classroom_id"));
        lessonInfoDto.setClassRoomName(resultSet.getString("name_classroom"));
        lessonInfoDto.setSubjectName(resultSet.getString("name_subject"));
        return lessonInfoDto;
    }
}
