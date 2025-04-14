package nki.ClimCue.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import nki.ClimCue.model.api.vilageFcst.VilageFcstResultMsgCode;

public class VilageFcstResultMsgAdapter extends XmlAdapter<String, VilageFcstResultMsgCode> {

    @Override
    public VilageFcstResultMsgCode unmarshal(String v) throws Exception {
        return VilageFcstResultMsgCode.fromString(v);
    }

    @Override
    public String marshal(VilageFcstResultMsgCode v) throws Exception {
        if (v == null) {
            return null;
        }
        return v.name();
    }
}
