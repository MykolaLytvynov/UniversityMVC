package ua.com.foxminded.university.dao.mapper.dto;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dto.CourseInfoDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourseInfoDtoMapper implements RowMapper<CourseInfoDto> {
    @Override
    public CourseInfoDto mapRow(ResultSet resultSet, int i) throws SQLException {
        CourseInfoDto courseInfoDto = new CourseInfoDto(resultSet.getInt("id"),
                resultSet.getInt("nummerCourse"), resultSet.getInt("facultyId"));
        courseInfoDto.setFacultyName(resultSet.getString("name"));

        return courseInfoDto;
    }
}
