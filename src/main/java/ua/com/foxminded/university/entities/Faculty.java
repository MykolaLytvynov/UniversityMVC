package ua.com.foxminded.university.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Faculty {
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    private List<Course> courses = new ArrayList<>();

}
