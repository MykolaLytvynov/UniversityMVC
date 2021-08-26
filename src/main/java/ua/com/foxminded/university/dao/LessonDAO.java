package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mapper.GroupMapper;
import ua.com.foxminded.university.dao.mapper.LessonMapper;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Lesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Component
@Slf4j
@RequiredArgsConstructor
public class LessonDAO implements CrudOperations<Lesson, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final LessonMapper lessonMapper;

    public static final String SAVE_LESSON = "INSERT INTO lessons (subjectId, dateTime, duration, classRoomId) VALUES (?, ?, ?, ?)";
    public static final String FIND_BY_ID = "SELECT * FROM lessons WHERE id = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM lessons WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM lessons";
    public static final String COUNT = "SELECT COUNT(*) FROM lessons";
    public static final String DELETE_LESSON = "DELETE FROM lessons WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM lessons";

    public static final String GET_ALL_GROUPS_ONE_LESSON = "SELECT idGroup FROM groupsLessons WHERE idLesson = ?";
    public static final String ADD_GROUPS_TO_LESSON = "INSERT INTO groupsLessons (idGroup, idLesson) VALUES (?, ?)";
    public static final String DELETE_GROUP_FROM_LESSON = "DELETE FROM groupsLessons WHERE idGroup = ? AND idLesson = ?";

    public static final String GET_LESSONS_BETWEEN_DATA = "SELECT * FROM lessons WHERE dateTime BETWEEN ? AND ?";


    @Override
    public Lesson save(Lesson lesson) {
        log.debug("save('{}') called", lesson);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_LESSON, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, lesson.getSubjectId());
                ps.setObject(2, lesson.getDateTime());
                ps.setInt(3, lesson.getDuration());
                ps.setInt(4, lesson.getClassRoomId());
                return ps;
            }
        }, keyHolder);

        Integer id = ofNullable(keyHolder.getKeys())
                .map(map -> (Integer) map.get("id"))
                .orElseThrow(() -> new RuntimeException(format("Query '%s' didn't returned it!", SAVE_LESSON)));
        lesson.setId(id);
        log.debug("save(Lesson) was success. Returned '{}'", lesson);
        return lesson;
    }

    @Override
    public Optional<Lesson> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Lesson result = jdbcTemplate.queryForObject(FIND_BY_ID, lessonMapper, id);
        result.setGroupsIdOneLesson(getAllGroupsOneLesson(result.getId()));
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }

    @Override
    public boolean existsById(Integer id) {
        log.debug("existsById('{}') called", id);
        Integer count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        boolean result = count != null && count > 0;
        log.debug("existsById('{}') returned '{}'", id, result);
        return result;
    }

    @Override
    public List<Lesson> findAll() {
        log.debug("findAll() called");
        List<Lesson> result = jdbcTemplate.query(FIND_ALL, lessonMapper);
        result.stream()
                .forEach(lesson -> lesson.setGroupsIdOneLesson(getAllGroupsOneLesson(lesson.getId())));
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    @Override
    public long count() {
        log.debug("count() called");
        long result = jdbcTemplate.queryForObject(COUNT, Integer.class);
        log.debug("count() returned '{}'", result);
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        jdbcTemplate.update(DELETE_LESSON, id);
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Lesson lesson) {
        log.debug("delete('{}') called", lesson);
        jdbcTemplate.update(DELETE_LESSON, lesson.getId());
        log.debug("delete('{}') was success", lesson);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        jdbcTemplate.update(DELETE_ALL);
        log.debug("deleteAll() was success");
    }

    public void addGroupsToLesson(Integer groupId, Integer lessonId) {
        log.debug("addGroupsToLesson('{}','{}') called", groupId, lessonId);
        jdbcTemplate.update(ADD_GROUPS_TO_LESSON, groupId, lessonId);
        log.debug("addGroupsToLesson('{}','{}') was success", groupId, lessonId);
    }

    public List<Integer> getAllGroupsOneLesson(Integer lessonId) {
        log.debug("getAllGroupsOneLesson('{}') called", lessonId);
        List<Integer> result = jdbcTemplate.queryForList(GET_ALL_GROUPS_ONE_LESSON, Integer.class, lessonId);
        log.debug("getAllGroupsOneLesson('{}') returned '{}'", lessonId, result);
        return result;
    }

    public void deleteGroupFromLesson(Group group, Lesson lesson) {
        log.debug("deleteGroupFromLesson('{}','{}') called", group, lesson);
        jdbcTemplate.update(DELETE_GROUP_FROM_LESSON, group.getId(), lesson.getId());
        log.debug("deleteGroupFromLesson('{}','{}') was success", group, lesson);
    }

    @Override
    public void update(Lesson lesson) {

    }

    public List<Lesson> getLessonsBetweenData (LocalDateTime start, LocalDateTime end) {
        log.debug("getLessonsBetweenData('{}', '{}') called", start, end);
        List<Lesson> result = jdbcTemplate.query(GET_LESSONS_BETWEEN_DATA, lessonMapper, start, end);
        log.debug("getLessonsBetweenData('{}', '{}') returned '{}'", start, end, result);
        return result;
    }
}
