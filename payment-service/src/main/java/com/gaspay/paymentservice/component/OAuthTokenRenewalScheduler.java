package com.gaspay.paymentservice.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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