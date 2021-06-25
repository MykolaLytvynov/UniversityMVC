package ua.com.foxminded.university.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.entities.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
        Group group = new Group(resultSet.getInt("nummerGroup"));
        group.setId(resultSet.getInt("id"));
        return group;
    }
}
