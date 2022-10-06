package com.pradeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pradeep.dtos.EmailResponse;
import com.pradeep.dtos.Emaildto;
import com.pradeep.util.ResourceUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationResources {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${NotificationServiceURL}")
    private String notificationServiceURL;

    private static final String REQUEST_URL_INFO = ">> Notification Service URL : ";
    private static final String SLASH = "/";

    public EmailResponse sendEmail(Emaildto emaildto) {
        String finalURL= notificationServiceURL+SLASH+"email";
        ResponseEntity<EmailResponse> response = restTemplate.exchange(finalURL, HttpMethod.POST, ResourceUtil.getNewHttpEntity(emaildto), new ParameterizedTypeReference<EmailResponse>() {});
        return response.getBody();
    }

}
