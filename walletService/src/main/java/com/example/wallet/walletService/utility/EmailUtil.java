package com.example.wallet.walletService.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.util.Date;

public class EmailUtil {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);


    public static void sendEmail(Session session, String subject, String body, String fromEmail, String toEmail){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse(toEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            logger.info("Message is ready");
            Transport.send(msg);
            logger.info("Email sent Successfully!!");
        }
        catch (Exception e) {
            logger.error("Error while sending the email" + e.getCause());
        }
    }
}
