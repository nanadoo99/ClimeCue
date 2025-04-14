package nki.ClimCue.exception;

import nki.ClimCue.model.api.ApiConnectionInfo;
import nki.ClimCue.model.api.vilageFcst.VilageFcstResultMsgCode;
import nki.ClimCue.model.exception.OpenAiExceptionStatus;

public class OpenAiApiException extends ApiException {

    public OpenAiApiException(OpenAiExceptionStatus exceptionStatus, String message) {
        super(exceptionStatus, message);
    }

    public OpenAiExceptionStatus getErrorStatus() {
        return (OpenAiExceptionStatus)super.getErrorStatus();
    }
}
