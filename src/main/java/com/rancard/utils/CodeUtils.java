/*(C) Gaspay App 2024 */
package com.rancard.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CodeUtils {

    public static String generatePickupCode(String orderId, String sessionId) {
        String code = orderId.substring(0, 4) + sessionId.substring(0, 4);
        log.info("[{}] generated pickup code: {}", sessionId, code);
        return code.toUpperCase();
    }
}
