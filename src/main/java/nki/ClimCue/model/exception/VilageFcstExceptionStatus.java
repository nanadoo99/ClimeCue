package nki.ClimCue.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
@Getter
public enum VilageFcstExceptionStatus implements ExceptionStatus {
    INVALID_LOCATION(UNPROCESSABLE_ENTITY, "VF_01", "서비스를 지원하지 않는 지역입니다."),
    TEMPORARILY_UNAVAILABLE(SERVICE_UNAVAILABLE, "VF_02", "재시도 합니다."), // >> 내부 재시도
    INVALID_REQUEST(BAD_REQUEST, "VF_03", "잘못된 요청입니다."),
    SERVICE_ERROR(INTERNAL_SERVER_ERROR, "VF_09", "서비스 이용이 불가합니다..");

    VilageFcstExceptionStatus(HttpStatus httpStatus, String code, String msg) {
        this.status = httpStatus;
        this.code = code;
        this.msg = msg;
    }

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
