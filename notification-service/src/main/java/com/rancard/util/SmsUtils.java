package com.rancard.util;


import com.rancard.service.ApiClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SmsUtils {

    private final ApiClientService apiClientService;

    public void sendSms(String recipient, String message) {
        log.info("Sending sms to {} with message {}", recipient, message);
        String url = "http://msg.rancardmobility.com:16013/cgi-bin/sendsms";

        String requestParams = "?to="+recipient+"&text="+message+"&username=tester&password=foobar&priority=0&from=Gaspay&smsc=MTNGH_BULK";;
        apiClientService.getRequest(url,requestParams,"order-sms");
    }
}
