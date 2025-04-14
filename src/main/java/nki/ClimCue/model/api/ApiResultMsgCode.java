package nki.ClimCue.model.api;

import nki.ClimCue.model.exception.ExceptionStatus;
import org.springframework.http.HttpStatus;

public interface ApiResultMsgCode {
    String getDescription();
    ExceptionStatus getExceptionStatus();
}
