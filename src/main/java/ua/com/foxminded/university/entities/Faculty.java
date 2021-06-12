package ua.com.foxminded.university.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter @Setter
@EqualsAndHashCode

public class Faculty {
    private int id;
    @NonNull
    private String name;
    private List<Course> courses = new ArrayList<>();

}
