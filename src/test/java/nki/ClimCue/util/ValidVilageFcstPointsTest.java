/*
package nki.ClimCue.util;

import nki.ClimCue.model.api.vilageFcst.VilageFcstNxNyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidVilageFcstPointsTest {

    private ValidVilageFcstPoints validVilageFcstPoints;

    @BeforeEach
    void setUp() {
        validVilageFcstPoints = new ValidVilageFcstPoints();
        validVilageFcstPoints.setVilageFcst_file("src/test/resources/static/excel/vilageFcst.xlsx");
        validVilageFcstPoints.init();  // 이 메서드를 호출하여 데이터를 로드합니다.
    }

    @Test
    void testValidPoint() {
        VilageFcstNxNyDto vilageFcstNxNyDto = new VilageFcstNxNyDto();
        vilageFcstNxNyDto.setNx(60);
        vilageFcstNxNyDto.setNy(127);
        assertTrue(validVilageFcstPoints.isValidPoint(vilageFcstNxNyDto));
    }

    @Test
    void testInvalidPoint() {
        VilageFcstNxNyDto vilageFcstNxNyDto = new VilageFcstNxNyDto();
        vilageFcstNxNyDto.setNx(999999);
        vilageFcstNxNyDto.setNy(99999);
        assertFalse(validVilageFcstPoints.isValidPoint(vilageFcstNxNyDto));
    }
}*/
