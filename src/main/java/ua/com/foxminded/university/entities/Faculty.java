package ua.com.foxminded.university.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Faculty {
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    private List<Course> courses = new ArrayList<>();

}
