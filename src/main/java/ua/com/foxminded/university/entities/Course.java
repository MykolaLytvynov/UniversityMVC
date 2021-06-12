package ua.com.foxminded.university.entities;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;


@Data
public class Course {
    private int id;
    @NonNull
    private int nummerCourse;
    private List<Group> groups = new ArrayList<>();

}
