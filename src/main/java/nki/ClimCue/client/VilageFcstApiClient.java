package nki.ClimCue.client;

import nki.ClimCue.model.api.ApiConnectionInfo;
import nki.ClimCue.model.api.ApiRequestInfo;
import nki.ClimCue.model.api.ApiResponseInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VilageFcstApiClient {

    public static ApiConnectionInfo getNetworkConnection(ApiRequestInfo apiRequestInfo, String serviceKey) throws IOException {
        HttpURLConnection connection = null;
        InputStream stream = null;
        String response;
        String contentType;

        try {
            URL url = new URL(apiRequestInfo.getFullRequestUrl(serviceKey));
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(600000);
            connection.setReadTimeout(600000);
            connection.setDoInput(true);

            contentType = connection.getContentType();
            stream = connection.getInputStream();
            response = readStreamToString(stream);

            return new ApiConnectionInfo(apiRequestInfo, new ApiResponseInfo(connection.getResponseCode(), contentType, response));
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    // InputStream 을 통해 전달받은 정보 >> 문자열로 반환
    public static String readStreamToString(InputStream stream) throws IOException{
        StringBuilder result = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        String readLine;
        while((readLine = br.readLine()) != null) {
            result.append(readLine + "\n\r");
        }
        stream.close();
        br.close();

        return result.toString();
    }
}
