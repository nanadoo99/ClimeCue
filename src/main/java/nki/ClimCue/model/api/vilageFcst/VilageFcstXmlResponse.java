package nki.ClimCue.model.api.vilageFcst;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;
import nki.ClimCue.util.VilageFcstResultMsgAdapter;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="OpenAPI_ServiceResponse")
public class VilageFcstXmlResponse {

    @XmlElement(name = "cmmMsgHeader")
    private Header cmmMsgHeader;

    @Data
    @XmlRootElement(name = "cmmMsgHeader")
    public static class Header {
        private String errMsg;
        private VilageFcstResultMsgCode returnAuthMsg;
        private String returnReasonCode;

        @XmlJavaTypeAdapter(VilageFcstResultMsgAdapter.class)
        public VilageFcstResultMsgCode getReturnAuthMsg() {
            return returnAuthMsg;
        }
    }
}
