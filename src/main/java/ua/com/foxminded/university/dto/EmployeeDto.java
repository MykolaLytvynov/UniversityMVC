package ua.com.foxminded.university.dto;

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
public class EmployeeDto extends Person{
    @NonNull
    private Integer positionId;
    @NonNull
    private Integer salary;
    @NonNull
    private String positionName;
}
