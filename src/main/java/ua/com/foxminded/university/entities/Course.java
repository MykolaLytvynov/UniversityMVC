package ua.com.foxminded.university.entities;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int id;
    private int nummerCourse;
    private List<Group> groups = new ArrayList<>();

    public Course(int id, int nummerCourse, List<Group> groups) {
        this.id = id;
        this.nummerCourse = nummerCourse;
        this.groups = groups;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNummerCourse() {
        return nummerCourse;
    }

    public void setNummerCourse(int nummerCourse) {
        this.nummerCourse = nummerCourse;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
