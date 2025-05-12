package nki.ClimCue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import nki.ClimCue.client.VilageFcstApiClient;
import nki.ClimCue.exception.VilagaFcstApiException;
import nki.ClimCue.model.api.ApiConnectionInfo;
import nki.ClimCue.model.api.ApiRequestInfo;
import nki.ClimCue.model.api.vilageFcst.VilageFcstNxNyDto;
import nki.ClimCue.model.api.vilageFcst.VilageFcstResultMsgCode;
import nki.ClimCue.model.api.vilageFcst.VilageFcstUltraSrtItems;
import nki.ClimCue.model.api.vilageFcst.VilageFcstXmlResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Service
public class VilageFcstServiceUltraSrt implements VilageFcstService {

    private static final Logger log = LoggerFactory.getLogger(VilageFcstServiceUltraSrt.class);
    private final VilageFcstApiClient vilageFcstApiClient = new VilageFcstApiClient();
    private static final int MAX_RETRY_COUNT = 3;
    private static final int RETRY_DELAY_SECONDS = 5;

    @Value("${open-api.vilage-fcst.service-key}")
    private String serviceKey;

    @Value("${open-api.vilage-fcst.call-back-url.root}")
    private String rootUrl;

    // 초단기실황조회
    @Value("${open-api.vilage-fcst.call-back-url.get-ultra-srt-ncst}")
    private String ultraSrtNcstPath;

    @Override
    public VilageFcstUltraSrtItems ultraSrtNcst(VilageFcstNxNyDto vilageFcstNxNyDto) throws Exception {

        String[] baseDateAndBaseTime = getBaseDateAndBaseTimeUltraSrctNcst();
        VilageFcstUltraSrtItems result = getFcst(ultraSrtNcstPath, baseDateAndBaseTime[0], baseDateAndBaseTime[1], vilageFcstNxNyDto);

        log.info("ultraSrtNcst result: {}", result);

        return result;
    }

    public VilageFcstUltraSrtItems ultraSrtNcst2(VilageFcstNxNyDto vilageFcstNxNyDto) throws Exception {
        String[] baseDateAndBaseTime = getBaseDateAndBaseTimeUltraSrctNcst();
        int retryCount = 0;
        VilageFcstUltraSrtItems result = null;

        // 재시도
        while (retryCount < MAX_RETRY_COUNT) {
            try{
                result = getFcst(ultraSrtNcstPath, baseDateAndBaseTime[0], baseDateAndBaseTime[1], vilageFcstNxNyDto);
                log.info("ultraSrtNcst result: {}", result);
                break; // 성공시 반복 종료
            } catch (Exception e){
                retryCount++;
                if (retryCount < MAX_RETRY_COUNT) {
                    log.warn("통신 오류 발생, {}초 후 재시도... (재시도 {}회)", RETRY_DELAY_SECONDS, retryCount);
                    TimeUnit.SECONDS.sleep(RETRY_DELAY_SECONDS);
                } else {
                    throw e;
                }
            }
        }

        return result;
    }

    private VilageFcstUltraSrtItems getFcst(String api, String baseDate, String baseTime, VilageFcstNxNyDto vilageFcstNxNyDto) throws Exception {
        VilageFcstUltraSrtItems vilageFcstUltraSrtItems = null;

        ApiRequestInfo apiRequestInfo = getApiRequestInfo(api, baseDate, baseTime, vilageFcstNxNyDto);
        ApiConnectionInfo apiConnectionInfo = vilageFcstApiClient.getNetworkConnection(apiRequestInfo, serviceKey);

        String contentType = apiConnectionInfo.getApiResponseInfo().getResponseContentType();
        String response = apiConnectionInfo.getApiResponseInfo().getResponseMessage();

        System.out.println("getFcst response: " + response);

        if (contentType != null) {
            if (contentType.contains("application/json")) {
                vilageFcstUltraSrtItems = parsingJsonObject(response);
                checkResultMsg(vilageFcstUltraSrtItems.getResultMsg(), apiConnectionInfo);
            } else if (contentType.contains("application/xml") || contentType.contains("text/xml")) {
                checkResultMsg(readXmlToObject(response).getCmmMsgHeader().getReturnAuthMsg(), apiConnectionInfo);
            }
        }


        return vilageFcstUltraSrtItems;
    }

    // 요청 url 생성
    private ApiRequestInfo getApiRequestInfo(String api, String baseDate, String baseTime, VilageFcstNxNyDto vilageFcstNxNyDto) {
        String basicUrlStr = rootUrl + api;
        String requestParams =
                "?numOfRows=" + 14 +
                        "&pageNo=" + 1 +
                        "&dataType=JSON" +
                        "&base_date=" + baseDate +
                        "&base_time=" + baseTime +
                        "&nx=" + vilageFcstNxNyDto.getNx() +
                        "&ny=" + vilageFcstNxNyDto.getNy();

        ApiRequestInfo apiRequestInfo = new ApiRequestInfo(basicUrlStr, requestParams);
        return apiRequestInfo;
    }

    // xml 응답 >> 자바 객체
    private static VilageFcstXmlResponse readXmlToObject(String response) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(VilageFcstXmlResponse.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(response);
        VilageFcstXmlResponse vilageFcstXmlResponse = (VilageFcstXmlResponse) unmarshaller.unmarshal(reader);

        System.out.println("vilageFcstXmlResponse = " + vilageFcstXmlResponse);
        return vilageFcstXmlResponse;

    }

    // 현재시각을 기준으로 BaseTime 을 구한다.
    private static String[] getBaseDateAndBaseTimeUltraSrctNcst() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime roundedTime = now.getMinute() < 10 ?
                now.minusHours(1).withMinute(0).withSecond(0).withNano(0) :
                now.withMinute(0).withSecond(0).withNano(0);

        String baseDate = roundedTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = roundedTime.format(DateTimeFormatter.ofPattern("HHmm"));

        return new String[]{baseDate, baseTime};
    }

    // json 응답 >> 자바 객체
    private static VilageFcstUltraSrtItems parsingJsonObject(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(json, VilageFcstUltraSrtItems.class);
    }

    // VilageFcstResultMsgCode 를 확인하고 그에 맞는 예외를 발생시킨다.
    private static void checkResultMsg(VilageFcstResultMsgCode resultMsg, ApiConnectionInfo apiConnectionInfo) {
        if(resultMsg.getExceptionStatus() != null) {
            throw new VilagaFcstApiException(resultMsg.getExceptionStatus(), resultMsg, apiConnectionInfo);
        }
    }
}
