package ua.com.foxminded.university.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.entities.ClassRoom;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassRoomMapper implements RowMapper<ClassRoom> {
    @Override
    public ClassRoom mapRow(ResultSet resultSet, int i) throws SQLException {
        ClassRoom classRoom = new ClassRoom(resultSet.getString("name"),
                resultSet.getString("description"));
        classRoom.setId(resultSet.getInt("id"));

        return classRoom;
    }
}
