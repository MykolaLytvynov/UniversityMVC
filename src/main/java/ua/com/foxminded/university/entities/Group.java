package ua.com.foxminded.university.entities;

import ua.com.foxminded.university.entities.person.Student;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int id;
    private int nummerGroup;
    private Course course;
    private List<Student> students = new ArrayList<>();

    public Group(int id, int nummerGroup, Course course, List<Student> students) {
        this.id = id;
        this.nummerGroup = nummerGroup;
        this.course = course;
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNummerGroup() {
        return nummerGroup;
    }

    public void setNummerGroup(int nummerGroup) {
        this.nummerGroup = nummerGroup;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
