package ua.com.foxminded.university.entities.person;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ua.com.foxminded.university.entities.person.Person;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Employee extends Person{
    private Integer positionId;
    @NonNull
    private Integer salary;
    @NonNull
    private String positionName;
}
