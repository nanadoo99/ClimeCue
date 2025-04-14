package nki.ClimCue.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
// 어느시점까지 유효할지.
// SOURCE: 소스코드에만 존재. 컴파일 과정에서 버려짐. 예) @Override
// CLASS: 클래스 파일까지 존재. 런타임시 버려짐.
// RUNTIME: 런타임까지 존재.
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VilageFcstPointValidator.class)
public @interface VilageFcstPointCheck {
    String message() default "날씨정보를 지원하지 않는 지역입니다.";

    Class<?>[] groups() default {}; // 애너테이션을 적용할 특정 상황

    Class<? extends Payload>[] payload() default {}; // 심각한 정도 등 메타데이터 정의.

}
