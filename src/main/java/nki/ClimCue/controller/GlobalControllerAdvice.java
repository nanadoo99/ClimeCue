package nki.ClimCue.controller;


import jakarta.servlet.http.HttpServletRequest;
import nki.ClimCue.exception.DefaultException;
import nki.ClimCue.exception.OpenAiApiException;
import nki.ClimCue.exception.VilagaFcstApiException;
import nki.ClimCue.model.ErrorResult;
import nki.ClimCue.model.exception.ExceptionStatus;
import nki.ClimCue.model.exception.OpenAiExceptionStatus;
import nki.ClimCue.model.exception.VilageFcstExceptionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler({VilagaFcstApiException.class})
    public ResponseEntity<ErrorResult> exceptionHandler(HttpServletRequest request, final VilagaFcstApiException e) {
        e.printStackTrace();
        VilageFcstExceptionStatus exceptionStatus = e.getErrorStatus();
        HttpStatus endpointStatus = exceptionStatus.getStatus();
        String errorCode = exceptionStatus.getCode();

        Object[] args = exceptionStatus == VilageFcstExceptionStatus.INVALID_LOCATION
                ? new Object[] { e.getObject() }
                : new Object[] {};

        return ResponseEntity
                .status(endpointStatus)
                .body(ErrorResult.builder()
                        .code(errorCode)
                        .message(messageSource.getMessage(errorCode, args, "위치 정보를 불러오지 못했습니다.", null))
                        .build());
    }

    @ExceptionHandler({OpenAiApiException.class})
    public ResponseEntity<ErrorResult> exceptionHandler(HttpServletRequest request, final OpenAiApiException e) {
        e.printStackTrace();
        OpenAiExceptionStatus exceptionStatus = e.getErrorStatus();
        HttpStatus endpointStatus = exceptionStatus.getStatus();
        String errorCode = exceptionStatus.getCode();

        return ResponseEntity
                .status(endpointStatus)
                .body(ErrorResult.builder()
                        .code(errorCode)
                        .message(messageSource.getMessage(errorCode, new Object[]{}, "AI 서비스를 이용할 수 없습니다.", null))
                        .build());
    }

}
