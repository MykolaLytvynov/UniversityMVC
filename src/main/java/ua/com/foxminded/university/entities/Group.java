package ua.com.foxminded.university.entities;

import lombok.Data;
import lombok.NonNull;
import ua.com.foxminded.university.entities.person.Student;

import java.util.ArrayList;
import java.util.List;

@Data
public class Group {
    private Integer id;
    @NonNull
    private Integer nummerGroup;
    private List<Student> students = new ArrayList<>();

}
