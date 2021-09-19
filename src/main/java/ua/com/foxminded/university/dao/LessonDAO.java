package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Component
@Slf4j
@RequiredArgsConstructor
public class LessonDAO implements CrudOperations<Lesson, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final LessonMapper lessonMapper;
    private final GroupMapper groupMapper;

    private static final String SAVE_LESSON = "INSERT INTO lessons (subjectId, dateTime, duration, classRoomId) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT lessons.id, dateTime, duration, classRoom.id classroom_id, subjects.id subject_id, " +
            "classRoom.name name_classroom, subjects.name name_subject " +
            "FROM lessons INNER JOIN classRoom ON lessons.classRoomId = classRoom.id INNER JOIN subjects ON lessons.subjectId = subjects.id WHERE lessons.id = ?";
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM lessons WHERE id = ?";
    private static final String FIND_ALL = "SELECT lessons.id, dateTime, duration, classRoom.id classroom_id, subjects.id subject_id, " +
                        "classRoom.name name_classroom, subjects.name name_subject " +
                        "FROM lessons INNER JOIN classRoom ON lessons.classRoomId = classRoom.id INNER JOIN subjects ON lessons.subjectId = subjects.id";
    private static final String COUNT = "SELECT COUNT(*) FROM lessons";
    private static final String DELETE_LESSON = "DELETE FROM lessons WHERE id = ?";
    private static final String DELETE_ALL = "DELETE FROM lessons";
    private static final String GET_ALL_GROUPS_ONE_LESSON = "SELECT idGroup id, nummerGroup, nummerCourse, name, courses.id course_id, faculties.id faculty_id FROM groupsLessons " +
            "INNER JOIN groups ON groupsLessons.idGroup = groups.id INNER JOIN courses ON groups.courseId = courses.id " +
            "INNER JOIN faculties ON courses.facultyId = faculties.id WHERE idLesson = ?";
    private static final String ADD_GROUPS_TO_LESSON = "INSERT INTO groupsLessons (idGroup, idLesson) VALUES (?, ?)";
    private static final String DELETE_GROUP_FROM_LESSON = "DELETE FROM groupsLessons WHERE idLesson = ?";
    private static final String UPDATE_LESSON = "UPDATE lessons SET subjectId = ?, dateTime = ?, duration = ?, classRoomId = ? WHERE id = ?";
    private static final String GET_LESSONS_BETWEEN_DATES_FOR_GROUP = "SELECT lessons.id, subjectId subject_id, dateTime, duration, classRoomId classroom_id," +
            " classRoom.name name_classroom, subjects.name name_subject FROM lessons INNER JOIN groupsLessons ON lessons.id = groupsLessons.idLesson " +
            "INNER JOIN classRoom ON lessons.classRoomId = classRoom.id INNER JOIN subjects ON lessons.subjectId = subjects.id" +
            " WHERE (idGroup = ?) AND (dateTime BETWEEN ? AND ?)";
    private static final String GET_LESSONS_BETWEEN_DATES_FOR_TEACHER = "SELECT lessons.id, subjectId subject_id, dateTime, " +
            "duration, classRoomId classroom_id, classRoom.name name_classroom, subjects.name name_subject " +
            "FROM lessons INNER JOIN subjects ON lessons.subjectId = subjects.id " +
            "INNER JOIN classRoom ON lessons.classRoomId = classRoom.id WHERE (employeeId = ?) AND (dateTime BETWEEN ? AND ?)";


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
        result.setGroups(getGroupsOneLesson(result.getId()));
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
                .forEach(lesson -> lesson.setGroups(getGroupsOneLesson(lesson.getId())));
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

    public void deleteGroupsFromLesson(Integer lessonId) {
        log.debug("deleteGroupsFromLesson('{}') called", lessonId);
        jdbcTemplate.update(DELETE_GROUP_FROM_LESSON, lessonId);
        log.debug("deleteGroupsFromLesson('{}') was success", lessonId);
    }

    @Override
    public void update(Lesson lesson) {
        log.debug("update('{}') called", lesson);
        jdbcTemplate.update(UPDATE_LESSON, lesson.getSubjectId(), lesson.getDateTime(), lesson.getDuration(),
                lesson.getClassRoomId(), lesson.getId());
        log.debug("update('{}') was success", lesson);
    }

    public List<Lesson> getLessonsBetweenDatesForGroup(LocalDateTime start, LocalDateTime end, Integer idGroup) {
        log.debug("getLessonsBetweenDatesForGroup('{}', '{}', '{}') called", start, end, idGroup);
        List<Lesson> result = jdbcTemplate.query(GET_LESSONS_BETWEEN_DATES_FOR_GROUP, lessonMapper, idGroup, start, end);
        log.debug("getLessonsBetweenDatesForGroup('{}', '{}', '{}') returned '{}'", start, end, idGroup);
        return result;
    }

    public List<Lesson> getLessonsBetweenDatesForTeacher(LocalDateTime start, LocalDateTime end, Integer idTeacher) {
        log.debug("getLessonsBetweenDatesForGroup('{}', '{}', '{}') called", start, end, idTeacher);
        List<Lesson> result = jdbcTemplate.query(GET_LESSONS_BETWEEN_DATES_FOR_TEACHER, lessonMapper, idTeacher, start, end);
        log.debug("getLessonsBetweenDatesForGroup('{}', '{}', '{}') returned '{}'", start, end, idTeacher);
        return result;
    }

    private List<Group> getGroupsOneLesson(Integer lessonId) {
        log.debug("getGroupsDtoOneLesson('{}') called", lessonId);
        List<Group> result = jdbcTemplate.query(GET_ALL_GROUPS_ONE_LESSON, groupMapper, lessonId);
        log.debug("getGroupsDtoOneLesson('{}') returned '{}'", lessonId, result);
        return result;
    }
}
