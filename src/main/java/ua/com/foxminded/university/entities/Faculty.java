package ua.com.foxminded.university.entities;

import java.util.ArrayList;
import java.util.List;

public class Faculty {
    private int id;
    private String name;
    private List<Course> courses = new ArrayList<>();


    public Faculty(int id, String name, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
