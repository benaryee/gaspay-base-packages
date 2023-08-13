package com.rancard.paymentservice.component;

import com.rancard.paymentservice.service.ZeepayOAuth2Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class OAuthTokenRenewalScheduler {

//    //TODO - Verify delay and fix startup execution
//    @Scheduled(fixedDelay = 30000000)
//    public void renewToken() {
//        log.info("About to call for token renewal");
//        tokenService.init();
//    }
}