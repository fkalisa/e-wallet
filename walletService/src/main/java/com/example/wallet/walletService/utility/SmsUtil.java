package com.example.wallet.walletService.utility;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

public  class SmsUtil {
    private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    static public final String ACCOUNT_SID = "AC4e7eafde3355607ea28918e2d8b2f1c9";
    static public  final String AUTH_TOKEN = "TODO";

    static public void sendSms()throws URISyntaxException {


            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message
                    .creator(new PhoneNumber("TODO_phone_to_be_sent_to"), // to
                            new PhoneNumber("+13076557472"), // from
                            "Dear customer, transaction history has been sent to you by e-Wallet")
                    .create();
                      logger.info(message.getSid());


    }
}
