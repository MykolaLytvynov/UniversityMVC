package ua.com.foxminded.university.entities;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Position {
    private Integer id;
    @NonNull
    private String name;
}
