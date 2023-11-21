package com.gaspay.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendinblueService {

    @Value("${sendinblue.api.key}")
    private static String key;
    public static void sendEmail(String from, String[] recipients, String[] ccs, String[] replyTos,
                          String subject, String htmlMsg , byte[] attachmentFile) {

        try {
          TransactionalEmailsApi api = new TransactionalEmailsApi();
          SendSmtpEmailSender sender = new SendSmtpEmailSender();
          sender.setEmail(from);


          List<SendSmtpEmailTo> toList = new ArrayList<SendSmtpEmailTo>();
          SendSmtpEmailTo to = new SendSmtpEmailTo();

          for (String recipient : recipients) {
              to.setEmail(recipient);
              toList.add(to);
          }

          List<SendSmtpEmailCc> ccList = new ArrayList<SendSmtpEmailCc>();
          SendSmtpEmailCc cc = new SendSmtpEmailCc();
          if (ccs != null) {
              for (String copy : ccs) {
                  cc.setEmail(copy);
                  ccList.add(cc);
              }
          }

          SendSmtpEmailReplyTo replyTo = new SendSmtpEmailReplyTo();
          if (replyTos != null){
              replyTo.setEmail(replyTos[0]);
           }

          SendSmtpEmailAttachment attachment = new SendSmtpEmailAttachment();
          List<SendSmtpEmailAttachment> attachmentList = new ArrayList<SendSmtpEmailAttachment>();

          if(attachmentFile != null){
              attachment.setName("data.xlsx");
              attachment.setContent(attachmentFile);
              attachmentList.add(attachment);
          }

          Properties params = new Properties();
          params.setProperty("subject", subject);
          SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
          sendSmtpEmail.setSender(sender);
          sendSmtpEmail.setTo(toList);

          if(ccs != null) {
              sendSmtpEmail.setCc(ccList);
          }

          sendSmtpEmail.setHtmlContent(htmlMsg);
          sendSmtpEmail.setSubject(subject);

          if(replyTos != null){
              sendSmtpEmail.setReplyTo(replyTo);
          }

          sendSmtpEmail.setParams(params);

          if(attachmentFile != null){
              sendSmtpEmail.setAttachment(attachmentList);
          }

          System.out.println(sendSmtpEmail.toString());

          CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
          System.out.println(response.toString());

      } catch (Exception e) {
          System.out.println("Exception occurred:- " + e.getMessage());
          e.printStackTrace();
      }
  }


    public static void sendEmail(String from, String[] recipients, String[] ccs, String[] replyTos,
                                 String subject, String htmlMsg) {
        try {


            TransactionalEmailsApi api = new TransactionalEmailsApi();
            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setEmail(from);


            List<SendSmtpEmailTo> toList = new ArrayList<SendSmtpEmailTo>();
            SendSmtpEmailTo to = new SendSmtpEmailTo();

            for (String recipient : recipients) {
                to.setEmail(recipient);
                toList.add(to);
            }
            List<SendSmtpEmailCc> ccList = new ArrayList<SendSmtpEmailCc>();
            SendSmtpEmailCc cc = new SendSmtpEmailCc();
            if (ccs != null) {
                for (String copy : ccs) {
                    cc.setEmail(copy);
                    ccList.add(cc);
                }
            }


            SendSmtpEmailReplyTo replyTo = new SendSmtpEmailReplyTo();
            if (replyTos != null){
                replyTo.setEmail(replyTos[0]);
            }


            Properties params = new Properties();
            params.setProperty("subject", subject);
            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.setSender(sender);
            sendSmtpEmail.setTo(toList);

            if(ccs != null) {
                sendSmtpEmail.setCc(ccList);
            }

            sendSmtpEmail.setHtmlContent(htmlMsg);
            sendSmtpEmail.setSubject(subject);

            if(replyTos != null){
                sendSmtpEmail.setReplyTo(replyTo);
            }

            sendSmtpEmail.setParams(params);


            System.out.println(sendSmtpEmail.toString());

            CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println("Exception occurred:- " + e.getMessage());
            e.printStackTrace();
        }
    }


}

