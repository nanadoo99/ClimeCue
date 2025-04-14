package nki.ClimCue.model.api.vilageFcst;


// 초단기
public enum VilageFcstUltraSrtCategoryCode {
    POP("강수확률", "%"),
    PTY("강수형태", ""),
    PCP("1시간 강수량", "mm"),
    REH("습도", "%"),
    SNO("1시간 신적설", "cm"),
    SKY("하늘상태", ""),
    TMP("1시간 기온", "℃"),
    TMN("아침 최저기온", "℃"),
    TMX("낮 최고기운", "℃"),
    UUU("풍속(동서성분)", "m/s"),
    VVV("풍속(남북성분)", "m/s"),
    WAV("파고", "M"),
    VEC("풍향", "deg"),
    WSD("풍속", "m/s"),
    T1H("기온", "℃"),
    RN1("1시간 강수량", "mm");

    private final String name;
    private final String unit;

    VilageFcstUltraSrtCategoryCode(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public static String getCodeValueWithUnit(String code, String value) {
        VilageFcstUltraSrtCategoryCode c = VilageFcstUltraSrtCategoryCode.valueOf(code);
        String result = value;

        switch (c) {
            case PTY:
                result = getTranslatedValueForPTY(value);
                break;
            case SKY:
                result = getTranslatedValueForSKY(value);
                break;
        }

        return result + c.getUnit();
    }

    private static String getTranslatedValueForPTY(String value) {
        switch (value) {
            case "0": return "없음";
            case "1": return "비";
            case "2": return "비/눈";
            case "3": return "눈";
            case "4": return "소나기";
            case "5": return "빗방울";
            case "6": return "빗방울눈날림";
            case "7": return "눈날림";
            default: return "알 수 없는 값";
        }
    }

    private static String getTranslatedValueForSKY(String value) {
        switch (value) {
            case "1": return "맑음";
            case "3": return "구름많음";
            case "4": return "흐림";
            default: return "알 수 없는 값";
        }
    }
}
