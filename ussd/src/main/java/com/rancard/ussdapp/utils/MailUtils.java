package com.rancard.ussdapp.utils;


import com.rancard.ussdapp.email.SendinblueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailUtils {
    private static String notificationsFrom;

    @Value("${email.from.notifications}")
    private void setNotificationsFrom(String notificationsFrom) {
        MailUtils.notificationsFrom = notificationsFrom;
    }


    public static void sendNotification(String recipient, String subject, String htmlMsg) {
        SendinblueService.sendEmail(notificationsFrom, new String[]{recipient}, null, null, subject, htmlMsg,null);
    }

    public static void sendNotificationWithAttachment(String recipient, String subject, String htmlMsg, byte[] emailAttachment) {
        log.info("Attachment : " + emailAttachment);
        SendinblueService.sendEmail(notificationsFrom, new String[]{recipient}, null, null, subject, htmlMsg , emailAttachment);
    }
}
