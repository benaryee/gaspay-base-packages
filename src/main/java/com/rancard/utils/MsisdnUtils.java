package com.rancard.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MsisdnUtils {

    public static boolean isValidPhoneNumber(String msisdn){
        return msisdn.matches("^(00233|0233|\\+233|233|0)(23|24|25|53|54|55|27|57|59|28|20|50|26|56|30|31|32|33|34|35|37|38|39)\\d{7}$");
    }

    public static String phoneNumberFormat(String msisdn){
        log.debug("Formatting msisdn: " + msisdn);

        if(!isValidPhoneNumber(msisdn)) msisdn = null;

        else if(msisdn.startsWith("00233"))
            msisdn = msisdn.substring(2);

        else if(msisdn.startsWith("0233"))
            msisdn = msisdn.substring(1);

        else if(msisdn.startsWith("0"))
            msisdn = "233" + msisdn.substring(1);
        else if(msisdn.startsWith("+"))
            msisdn = msisdn.substring(1);
        else if(!msisdn.startsWith("233"))
            msisdn = "233" + msisdn;

        log.debug("Formatted number: " + msisdn);
        return msisdn;
    }

    public static String resolveNetwork(String msisdn , String sessionId) {
        msisdn = isValidPhoneNumber(msisdn) ? phoneNumberFormat(msisdn) : msisdn;
        switch (msisdn.substring(3, 5)) {
            case "20":
            case "50":
                return "OT";
            case "23":
                return "GLO_GHANA";
            case "27":
            case "57":
                return "MYBUZZ";
            case "26":
            case "56":
                return "ZAIN_RANCARD";
            case "24":
            case "54":
            case "53":
            case "55":
            case "59":
            case "25":
                return "MTNGH";
            case "28":
                return "KASAPA";
            default:
                return "Unable to resolve network";
        }
    }
}
