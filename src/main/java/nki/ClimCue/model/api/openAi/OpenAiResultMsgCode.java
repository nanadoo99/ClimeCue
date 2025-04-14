package nki.ClimCue.model.api.openAi;

import lombok.Getter;
import nki.ClimCue.model.api.ApiResultMsgCode;
import nki.ClimCue.model.exception.OpenAiExceptionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import static nki.ClimCue.model.exception.OpenAiExceptionStatus.*;

@Getter
public enum OpenAiResultMsgCode implements ApiResultMsgCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Invalid Authentication, Or Incorrect API key provided. Or You must be a member of an organization to use the API", OpenAiExceptionStatus.UNAUTHORIZED), // 401
    FORBIDDEN(HttpStatus.FORBIDDEN, "Country, region, or territory not supported.", UNSUPPORTED_REGION), // 403
    NOT_FOUND(HttpStatus.NOT_FOUND, "Rate limit reached for requests. Or You exceeded your current quota, please check your plan and billing details.", LIMIT_EXCEEDED), // 429
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "The server had an error while processing your request.", SERVER_ERROR), // 500
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "The engine is currently overloaded, please try again later", ENGINE_OVERLOADED), // 503
    UNKNOWN_ERROR(null, null, OpenAiExceptionStatus.UNKNOWN_ERROR); // 503

    private final HttpStatusCode httpStatus;
    private final String description;
    private final OpenAiExceptionStatus exceptionStatus;

    OpenAiResultMsgCode(HttpStatusCode apiResultHttpStatus, String description, OpenAiExceptionStatus exceptionStatus) {
        this.httpStatus = apiResultHttpStatus;
        this.description = description;
        this.exceptionStatus = exceptionStatus;
    }

    public static OpenAiResultMsgCode fromHttpStatus(HttpStatusCode httpStatus) {
        for (OpenAiResultMsgCode msgCode : OpenAiResultMsgCode.values()) {
            if (msgCode.httpStatus.equals(httpStatus)) {
                return msgCode;
            }
        }
        return UNKNOWN_ERROR;
    }
}