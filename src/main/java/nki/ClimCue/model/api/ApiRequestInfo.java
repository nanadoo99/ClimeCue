package nki.ClimCue.model.api;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiRequestInfo {
    private final String requestUrl;
    private final String requestParams;
    private final long timestamp = System.currentTimeMillis();

    public ApiRequestInfo(String requestUrl, String requestParams) {
        this.requestUrl = requestUrl;
        this.requestParams = requestParams;
    }

    public String getFullRequestUrl(String serviceKey) {
        return requestUrl + requestParams + "&serviceKey=" + serviceKey;
    }
}
