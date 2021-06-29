package ua.com.foxminded.university.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.entities.Position;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionMapper implements RowMapper<Position> {
    public Position mapRow(ResultSet resultSet, int i) throws SQLException {
        Position position = new Position(resultSet.getString("name"));
        position.setId(resultSet.getInt("id"));
        return position;
    }
}
