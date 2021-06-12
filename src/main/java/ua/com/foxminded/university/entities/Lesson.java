package ua.com.foxminded.university.entities;

import ua.com.foxminded.university.entities.person.Teacher;

import java.time.LocalDateTime;
import java.util.List;

public class Lesson {
    private Subject subject;
    private Teacher teacher;
    private LocalDateTime localDateTime;
    private int duration;
    private ClassRoom classRoom;
    private List<Group> lessonForGroups;

    public Lesson(Subject subject, Teacher teacher, LocalDateTime localDateTime, int duration, ClassRoom classRoom, List<Group> lessonForGroups) {
        this.subject = subject;
        this.teacher = teacher;
        this.localDateTime = localDateTime;
        this.duration = duration;
        this.classRoom = classRoom;
        this.lessonForGroups = lessonForGroups;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public List<Group> getLessonForGroups() {
        return lessonForGroups;
    }

    public void setLessonForGroups(List<Group> lessonForGroups) {
        this.lessonForGroups = lessonForGroups;
    }
}
