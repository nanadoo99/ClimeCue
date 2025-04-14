package nki.ClimCue.model.api.vilageFcst;

import lombok.Getter;
import nki.ClimCue.model.api.ApiResultMsgCode;
import nki.ClimCue.model.exception.VilageFcstExceptionStatus;

import static nki.ClimCue.model.exception.VilageFcstExceptionStatus.*;

@Getter
public enum VilageFcstResultMsgCode implements ApiResultMsgCode {
    NORMAL_SERVICE("00", "정상", null),
    APPLICATION_ERROR("01", "어플리케이션 에러", TEMPORARILY_UNAVAILABLE),
    DB_ERROR("02", "데이터베이스 에러", TEMPORARILY_UNAVAILABLE),
    NODATA_ERROR("03", "데이터없음 에러", INVALID_LOCATION),
    HTTP_ERROR("04", "HTTP 에러", TEMPORARILY_UNAVAILABLE),
    SERVICETIME_OUT("05", "서비스 연결실패 에러", TEMPORARILY_UNAVAILABLE),
    INVALID_REQUEST_PARAMETER_ERROR("10", "잘못된 요청 파라미터 에러", INVALID_REQUEST),
    NO_MANDATORY_REQUEST_PARAMETERS_ERROR("11", "필수요청 파라미터가 없음", INVALID_REQUEST),
    NO_OPENAPI_SERVICE_ERROR("12", "해당 오픈 API 서비스가 없거나 폐기됨", SERVICE_ERROR),
    SERVICE_ACCESS_DENIED_ERROR("20","서비스 접근 거부", TEMPORARILY_UNAVAILABLE),
    TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR("21", "일시적으로 사용할 수 없는 서비스 키", TEMPORARILY_UNAVAILABLE),
    LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR("22", "서비스 요청제한횟수 초과 에러", SERVICE_ERROR),
    SERVICE_KEY_IS_NOT_REGISTERED_ERROR("30", "등록되지 않은 서비스키", SERVICE_ERROR),
    DEADLINE_HAS_EXPIRED_ERROR("31", "기한만료된 서비스키", SERVICE_ERROR),
    UNREGISTERED_IP_ERROR("32", "등록되지 않은 IP", INVALID_REQUEST),
    UNSIGNED_CALL_ERROR("33", "서명되지 않은 호출", INVALID_REQUEST),
    UNKNOWN_ERROR("99", "기타에러", SERVICE_ERROR);

    private final String responseCode;
    private final String description;
    private final VilageFcstExceptionStatus exceptionStatus;

    VilageFcstResultMsgCode(String responseCode, String description, VilageFcstExceptionStatus exceptionStatus) {
        this.responseCode = responseCode;
        this.description = description;
        this.exceptionStatus = exceptionStatus;
    }

    public static VilageFcstResultMsgCode fromString (String resultMsgText) {
        for(VilageFcstResultMsgCode msg : VilageFcstResultMsgCode.values()) {
            if(msg.name().equals(resultMsgText)) {
                return msg;
            }
        }
        return UNKNOWN_ERROR;
    }

    public VilageFcstExceptionStatus getExceptionStatus(){
        return exceptionStatus;
    }
}
