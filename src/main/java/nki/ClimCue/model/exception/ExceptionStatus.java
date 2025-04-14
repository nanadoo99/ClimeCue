package nki.ClimCue.model.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionStatus {
    HttpStatus getStatus();
    String getCode();
}
