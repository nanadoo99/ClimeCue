package nki.ClimCue.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc // Spring MVC의 Http 요청 및 응답 처리 테스트, 자동으로 MockMvc 객체 설정.
public class VilageFcstControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("기상청 API 통신 테스트 - 초단기실황")
    public void ultraSrtNcst() throws Exception {

        String nx = "60";
        String ny = "127";

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

        param.add("nx", nx);
        param.add("ny", ny);

        this.mockMvc.perform(get("/api/vilageFcst/ultraSrtNcst").params(param))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("시간 반환 테스트 - 10분 전")
    void getBaseDateAndBaseTimeUltraSrctNcstBefore10() {
        LocalDateTime time = LocalDateTime.of(2025,1,7,12,7);
        String[] result =  getBaseDateAndBaseTimeUltraSrctNcst(time);

        Assertions.assertThat(result[0]).isEqualTo("20250107");
        Assertions.assertThat(result[1]).isEqualTo("1100");
    }


    @Test
    @DisplayName("시간 반환 테스트 - 10분 후")
    void getBaseDateAndBaseTimeUltraSrctNcstAfter10() {
        LocalDateTime time = LocalDateTime.of(2025,1,7,12,11);
        String[] result =  getBaseDateAndBaseTimeUltraSrctNcst(time);

        Assertions.assertThat(result[0]).isEqualTo("20250107");
        Assertions.assertThat(result[1]).isEqualTo("1200");
    }

    @Test
    @DisplayName("시간 반환 테스트 - 자정 10분 전")
    void getBaseDateAndBaseTimeUltraSrctNcstMidnight() {
        LocalDateTime time = LocalDateTime.of(2025,1,7,0,9);
        String[] result =  getBaseDateAndBaseTimeUltraSrctNcst(time);

        Assertions.assertThat(result[0]).isEqualTo("20250106");
        Assertions.assertThat(result[1]).isEqualTo("2300");
    }

    private static String[] getBaseDateAndBaseTimeUltraSrctNcst(LocalDateTime now) {

        LocalDateTime roundedTime = now.getMinute() < 10 ?
                now.minusHours(1).withMinute(0).withSecond(0).withNano(0) :
                now.withMinute(0).withSecond(0).withNano(0);

        String baseDate = roundedTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = roundedTime.format(DateTimeFormatter.ofPattern("HHmm"));

        return new String[]{baseDate, baseTime};
    }

}
