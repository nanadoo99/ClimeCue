package nki.ClimCue.model.member;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class PersonalConditionDto {
    private Long userId;

    @Min(value = 0, message = "나이는 0살 이상이어야 합니다.")
    @Max(value = 120, message = "나이는 120살 이하이어야 합니다.")
    private Integer age;

    @Size(max = 20, message = "체질은 20자 이하이어야 합니다.")
    private String bodyType;

    @Size(max = 20, message = "취미는 20자 이하이어야 합니다.")
    private String hobby;

    @Size(max = 20, message = "선호하는 운동은 20자 이하이어야 합니다.")
    private String exercise;

    @Size(max = 20, message = "건강상태는 20자 이하이어야 합니다.")
    private String medical;


    @Builder
    public PersonalConditionDto(Long userId, Integer age, String bodyType, String hobby, String exercise, String medical) {
        this.userId = userId;
        this.age = age;
        this.bodyType = bodyType;
        this.hobby = hobby;
        this.exercise = exercise;
        this.medical = medical;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType != null ?  bodyType.strip() : null;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby != null ?  hobby.strip() : null;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise != null ?  exercise.strip() : null;
    }

    public void setMedical(String medical) {
        this.medical = medical != null ?  medical.strip() : null;
    }

    public PersonalCondition toEntity() {
        return PersonalCondition.builder()
                .userId(userId)
                .age(age)
                .bodyType(bodyType)
                .hobby(hobby)
                .exercise(exercise)
                .medical(medical)
                .build();
    }

    public static PersonalConditionDto toDto(PersonalCondition entity) {
        PersonalConditionDto.PersonalConditionDtoBuilder builder = PersonalConditionDto.builder()
                .userId(entity.getUserId());

        Optional.ofNullable(entity.getAge()).ifPresent(builder::age);
        Optional.ofNullable(entity.getBodyType()).ifPresent(builder::bodyType);
        Optional.ofNullable(entity.getHobby()).ifPresent(builder::hobby);
        Optional.ofNullable(entity.getExercise()).ifPresent(builder::exercise);
        Optional.ofNullable(entity.getMedical()).ifPresent(builder::medical);

        return builder.build();
    }

}
