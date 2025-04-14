package nki.ClimCue.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum OpenAiExceptionStatus implements ExceptionStatus {
    UNAUTHORIZED(FORBIDDEN, "OA_01"),
    UNSUPPORTED_REGION(FORBIDDEN, "OA_02"),
    LIMIT_EXCEEDED(FORBIDDEN, "OA_03"),
    SERVER_ERROR(FORBIDDEN, "OA_04"),
    ENGINE_OVERLOADED(FORBIDDEN, "OA_05"),
    UNKNOWN_ERROR(FORBIDDEN, "OA_06");

    OpenAiExceptionStatus(HttpStatus endpointHttpStatus, String code) {
        this.status = endpointHttpStatus;
        this.code = code;
    }

    private final HttpStatus status;
    private final String code;
}
