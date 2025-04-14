    package nki.ClimCue.validation;

    import jakarta.validation.ConstraintValidator;
    import jakarta.validation.ConstraintValidatorContext;
    import nki.ClimCue.model.api.vilageFcst.VilageFcstNxNyDto;
    import nki.ClimCue.util.ValidVilageFcstPoints;
    import org.springframework.beans.factory.annotation.Autowired;

    public class VilageFcstPointValidator implements ConstraintValidator<VilageFcstPointCheck, VilageFcstNxNyDto> {
        @Autowired // 스프링 환경에서는 ConstraintValidator 구현체들이 스프링 빈으로 자동으로 등록되지 않지만, 스프링의 LocalValidatorFactoryBean을 통해 스프링 의존성 주입받을 수 있도록 함.
        private ValidVilageFcstPoints validVilageFcstPoints;

        @Override
        public boolean isValid(VilageFcstNxNyDto vilageFcstNxNyDto, ConstraintValidatorContext context) {
            if (vilageFcstNxNyDto == null) {
                return true;
            }

            System.out.println("isValid.vilageFcstNxNyDto = " + vilageFcstNxNyDto);
            System.out.println("validVilageFcstPoints.isValidPoint(vilageFcstNxNyDto) =" + validVilageFcstPoints.isValidPoint(vilageFcstNxNyDto));
            if (!validVilageFcstPoints.isValidPoint(vilageFcstNxNyDto)) {
                System.out.println("isValidPoint >> false");
                // 컨텍스트에 커스텀 에러 메시지를 추가
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("{vilageFcstDto.unsupported_location}")
                        .addPropertyNode("nx")
                        .addConstraintViolation();
                context.buildConstraintViolationWithTemplate("{vilageFcstDto.unsupported_location}")
                        .addPropertyNode("ny")
                        .addConstraintViolation();
                System.out.println("context = " + context);
                return false;
            }
            System.out.println("isValidPoint >> true");
            return true;
        }
    }
