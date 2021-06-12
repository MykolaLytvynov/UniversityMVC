package ua.com.foxminded.university.entities;

import lombok.Data;
import lombok.NonNull;
import ua.com.foxminded.university.entities.person.Student;

import java.util.ArrayList;
import java.util.List;

@Data
public class Group {
    private int id;
    @NonNull
    private int nummerGroup;
    private List<Student> students = new ArrayList<>();

}
