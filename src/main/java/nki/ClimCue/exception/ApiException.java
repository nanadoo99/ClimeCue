package nki.ClimCue.exception;

import nki.ClimCue.model.exception.ExceptionStatus;

public class ApiException extends DefaultException {
    public ApiException(ExceptionStatus errorStatus, String message) {
        super(errorStatus, message);
    }

    public ApiException(ExceptionStatus errorStatus, String message, Object object) {
        super(errorStatus, message, object);
    }
}
