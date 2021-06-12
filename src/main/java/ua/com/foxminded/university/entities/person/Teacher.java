package ua.com.foxminded.university.entities.person;

import ua.com.foxminded.university.entities.Subject;

public class Teacher extends Employee{
    private Subject subject;

    public Teacher(int id, String name, String lastName, Employee position, int salary, Subject subject) {
        super(id, name, lastName, position, salary);
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
