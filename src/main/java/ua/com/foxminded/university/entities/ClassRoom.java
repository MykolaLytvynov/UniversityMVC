package ua.com.foxminded.university.entities;

import lombok.*;

//@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ClassRoom {
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String description;
}