package ua.com.foxminded.university.dao.mapper.dto;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dto.GroupInfoDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupInfoDtoMapper implements RowMapper<GroupInfoDto> {
    @Override
    public GroupInfoDto mapRow(ResultSet resultSet, int i) throws SQLException {
        GroupInfoDto groupInfoDto = new GroupInfoDto(resultSet.getInt("id"),
                resultSet.getInt("nummerGroup"),
                resultSet.getInt("course_id"),
                resultSet.getInt("faculty_id"));
        groupInfoDto.setCourseNumber(resultSet.getInt("nummercourse"));
        groupInfoDto.setFacultyName(resultSet.getString("name"));
        return groupInfoDto;
    }
}
