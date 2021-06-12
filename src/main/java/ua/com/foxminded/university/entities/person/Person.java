package ua.com.foxminded.university.entities.person;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Person {
    private int id;
    private String name;
    private String lastName;
}
