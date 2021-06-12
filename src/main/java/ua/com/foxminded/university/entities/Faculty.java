package ua.com.foxminded.university.entities;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class Faculty {
    private int id;
    @NonNull
    private String name;
    private List<Course> courses = new ArrayList<>();

}
