package com.twillioExample.Controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.lookups.v1.PhoneNumber;
import com.twillioExample.Payload.SMSRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Twillio_Controller {

    // Twilio credentials
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String fromPhoneNumber;

    // POST endpoint to send SMS
    @PostMapping("/send-sms")
    //http://localhost:8080/send-sms
    public ResponseEntity<String> sendSMS(@RequestBody SMSRequest smsRequest) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(smsRequest.getTo()),
                        new com.twilio.type.PhoneNumber(fromPhoneNumber),
                        smsRequest.getBody())
                .create();
        return new ResponseEntity<>("SMS sent successfully. SID: " + message.getSid(), HttpStatus.CREATED);
    }
}
