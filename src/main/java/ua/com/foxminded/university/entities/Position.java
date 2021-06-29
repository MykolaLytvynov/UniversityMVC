package ua.com.foxminded.university.entities;

import lombok.Data;
import lombok.NonNull;

@Data
public class Position {
    private Integer id;
    @NonNull
    private String name;
}
