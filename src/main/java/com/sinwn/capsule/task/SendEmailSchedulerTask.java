package com.sinwn.capsule.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendEmailSchedulerTask {

    @Scheduled(fixedRate = 6000)
    private void sendEmail(){

    }
}
