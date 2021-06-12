package ua.com.foxminded.university.entities;

import ua.com.foxminded.university.entities.person.Teacher;

public class Subject {
    private int id;
    private String name;
    private String description;
    private Teacher teacher;
    private int amountLessons;

    public Subject(int id, String name, String description, Teacher teacher, int amountLessons) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.amountLessons = amountLessons;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getAmountLessons() {
        return amountLessons;
    }

    public void setAmountLessons(int amountLessons) {
        this.amountLessons = amountLessons;
    }
}
