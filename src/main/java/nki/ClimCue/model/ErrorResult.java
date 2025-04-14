package nki.ClimCue.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResult {
    private String code;
    private String message;
}
