package ua.com.foxminded.university.entities.person;

public class Employee extends Person{
    private Employee position;
    private int salary;

    public Employee(int id, String name, String lastName, Employee position, int salary) {
        super(id, name, lastName);
        this.position = position;
        this.salary = salary;
    }

    public Employee getPosition() {
        return position;
    }

    public void setPosition(Employee position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
