package com.example.wallet.walletService.utility;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

public class SmsUtil {
    private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    public final String ACCOUNT_SID = "AC4e7eafde3355607ea28918e2d8b2f1c9";
    public  final String AUTH_TOKEN = "a75294f8a21205bf53b33dcbd3d32b80";

    void sendSms()throws URISyntaxException {


            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            /*
            Message message = Message
                    .creator(new PhoneNumber("+32487555607"), // to
                            new PhoneNumber("+13076557472"), // from
                            "Where's Wallace?")
                    .create();
                      logger.info(message.getSid());

             */
    }
}
