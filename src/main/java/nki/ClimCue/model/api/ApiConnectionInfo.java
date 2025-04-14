package nki.ClimCue.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ApiConnectionInfo {
    private final ApiRequestInfo apiRequestInfo;
    private final ApiResponseInfo apiResponseInfo;
}
