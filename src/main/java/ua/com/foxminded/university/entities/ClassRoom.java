package ua.com.foxminded.university.entities;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter @Setter
@EqualsAndHashCode

public class ClassRoom {
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String description;
}
