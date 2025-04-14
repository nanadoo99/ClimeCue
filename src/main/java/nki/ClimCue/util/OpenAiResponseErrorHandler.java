package nki.ClimCue.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nki.ClimCue.exception.OpenAiApiException;
import nki.ClimCue.model.api.openAi.OpenAiResultMsgCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;

@Slf4j
public class OpenAiResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() ||
                response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatusCode statusCode = response.getStatusCode();
        System.out.println("statusCode = " + statusCode);
        System.out.println("response.getStatusText() = " + response.getStatusText());
        System.out.println("response.getHeaders() = " + response.getHeaders());

        // statusCode의 값을 참고해 OpenAiResultMsgCode를 가져오고 그것의 exceptionStatus 를 넘긴다.
        OpenAiResultMsgCode resultMsgCode = OpenAiResultMsgCode.fromHttpStatus(statusCode);

        throw new OpenAiApiException(resultMsgCode.getExceptionStatus(), "");
    }
}
