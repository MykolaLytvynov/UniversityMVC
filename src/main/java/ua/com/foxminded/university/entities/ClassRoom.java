package ua.com.foxminded.university.entities;

import lombok.*;

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