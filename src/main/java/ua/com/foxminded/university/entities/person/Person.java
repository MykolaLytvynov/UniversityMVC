package ua.com.foxminded.university.entities.person;

import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Person {
    private Integer id;
    private String name;
    private String lastName;
}
