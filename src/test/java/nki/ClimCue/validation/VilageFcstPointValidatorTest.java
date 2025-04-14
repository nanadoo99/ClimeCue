package nki.ClimCue.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import nki.ClimCue.model.api.vilageFcst.VilageFcstNxNyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VilageFcstPointValidatorTest {
    @Autowired
    private Validator validatorInjected;

    @Test
    public void test_o() {
        VilageFcstNxNyDto nxny = new VilageFcstNxNyDto(60, 127);
        Set<ConstraintViolation<VilageFcstNxNyDto>> violations = validatorInjected.validate(nxny);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void test_x() {
        VilageFcstNxNyDto nxny = new VilageFcstNxNyDto(9999, 9999);
        Set<ConstraintViolation<VilageFcstNxNyDto>> violations = validatorInjected.validate(nxny);

        assertFalse(violations.isEmpty());
    }

}