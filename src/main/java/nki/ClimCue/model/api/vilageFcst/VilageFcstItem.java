package nki.ClimCue.model.api.vilageFcst;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// 단기예보조회
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VilageFcstItem {
    // 발표일자
    @JsonProperty("baseDate")
    private String baseDate;

    // 발표시각
    @JsonProperty("baseTime")
    private String baseTime;

    // 예보지점 x 좌표
    @JsonProperty("nx")
    private int nx;

    // 예보지점 y 좌표
    @JsonProperty("ny")
    private int ny;

    // 자료구분코드
    @JsonProperty("category")
    private String category;

    private String categoryName;

    // 예보일자
    @JsonProperty("fcstDate")
    private String fcstDate;

    // 예보시간
    @JsonProperty("fcstTime")
    private String fcstTime;

    // 예보값
    @JsonProperty("fcstValue")
    private String fcstValue;
}
