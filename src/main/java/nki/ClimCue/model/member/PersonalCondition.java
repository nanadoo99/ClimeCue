package nki.ClimCue.model.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "personal_condition")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", nullable = false, unique = true)
    private Long userId;

    @Column(name = "age", columnDefinition = "TINYINT UNSIGNED")
    private Integer age;

    @Column(name = "bodyType")
    private String bodyType;

    @Column(name = "exercise")
    private String exercise;

    @Column(name = "medical")
    private String medical;

    @Column(name = "hobby")
    private String hobby;

    public void updatePersonalCondition(PersonalCondition personalCondition) {
        age = personalCondition.getAge();
        bodyType = personalCondition.getBodyType();
        exercise = personalCondition.getExercise();
        medical = personalCondition.getMedical();
        hobby = personalCondition.getHobby();
    }

}
