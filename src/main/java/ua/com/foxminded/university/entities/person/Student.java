package ua.com.foxminded.university.entities.person;

import ua.com.foxminded.university.entities.Group;

public class Student extends Person{
    private Group group;

    public Student(int id, String name, String lastName, Group group) {
        super(id, name, lastName);
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
