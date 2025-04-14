package nki.ClimCue.exception;

import nki.ClimCue.model.api.ApiConnectionInfo;
import nki.ClimCue.model.api.vilageFcst.VilageFcstResultMsgCode;
import nki.ClimCue.model.exception.VilageFcstExceptionStatus;

public class VilagaFcstApiException extends ApiException {
    // 연결시도 이전
    public VilagaFcstApiException(VilageFcstExceptionStatus vilageFcstApiExceptionStatus, String message) {
        super(vilageFcstApiExceptionStatus, message);
    }

    public VilagaFcstApiException(VilageFcstExceptionStatus vilageFcstApiExceptionStatus, String message, Object object) {
        super(vilageFcstApiExceptionStatus, message, object);
    }

    // 연결시도 이후
    public VilagaFcstApiException(VilageFcstExceptionStatus vilageFcstApiExceptionStatus, VilageFcstResultMsgCode resultMsg, ApiConnectionInfo apiConnectionInfo) {
        this(vilageFcstApiExceptionStatus, "[API 응답 코드] " + resultMsg + " [API 연결 정보] " + apiConnectionInfo.toString());
    }

    public VilagaFcstApiException(VilageFcstExceptionStatus vilageFcstApiExceptionStatus, VilageFcstResultMsgCode resultMsg, ApiConnectionInfo apiConnectionInfo, Object object) {
        this(vilageFcstApiExceptionStatus, "[API 응답 코드] " + resultMsg + " [API 연결 정보] " + apiConnectionInfo.toString(), object);
    }


    public VilageFcstExceptionStatus getErrorStatus() {
        return (VilageFcstExceptionStatus)super.getErrorStatus();
    }
}
