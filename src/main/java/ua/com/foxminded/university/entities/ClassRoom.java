package ua.com.foxminded.university.entities;

import lombok.Data;
import lombok.NonNull;

@Data
public class ClassRoom {
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String description;
}