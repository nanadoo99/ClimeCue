package nki.ClimCue.model.api.vilageFcst;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// 초단기실황조회
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VilageFcstUltraSrtItem {
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

    private String category;
    private String obsrValue;

    private String categoryName; // 카테고리 한글명
    private String obsrValueWithUnit; // 현황 값과 단위

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
        decodeCategory();
        if (obsrValue != null) {
            decodeObsrValue();
        }
    }

    @JsonProperty("obsrValue")
    public void setObsrValue(String obsrValue) {
        this.obsrValue = obsrValue;
        if (category != null) {
            decodeObsrValue();
        }
    }

    private void decodeCategory() {
        if (category != null) {
            categoryName = VilageFcstUltraSrtCategoryCode.valueOf(category).getName();
        }
    }

    private void decodeObsrValue() {
        if (category != null && obsrValue != null) {
            obsrValueWithUnit = VilageFcstUltraSrtCategoryCode.getCodeValueWithUnit(category, obsrValue);
        }
    }

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
