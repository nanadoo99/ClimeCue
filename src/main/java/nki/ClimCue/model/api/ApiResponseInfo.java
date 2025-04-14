package nki.ClimCue.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ApiResponseInfo {
    private final int httpStatus;
    private final String responseContentType;
    private final String responseMessage;
    private final long timestamp = System.currentTimeMillis();

}
