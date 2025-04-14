package nki.ClimCue.exception;

import nki.ClimCue.model.exception.ExceptionStatus;

public class DefaultException extends RuntimeException {
    private ExceptionStatus errorStatus;
    private Object object;

    public DefaultException(ExceptionStatus errorCode, String message) {
        super("[에러코드] "+ errorCode + " " + message);
        this.errorStatus = errorCode;
    }

    public DefaultException(ExceptionStatus errorCode, String message, Object object) {
        this(errorCode, message);
        this.object = object;
    }

    public ExceptionStatus getErrorStatus() {
        return errorStatus;
    }

    public Object getObject() {
        return object;
    }
}
