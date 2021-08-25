package ua.com.foxminded.university.entities;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Position {
    private Integer id;
    @NonNull
    private String name;
}
