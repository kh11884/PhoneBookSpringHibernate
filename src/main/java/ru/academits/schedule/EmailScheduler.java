package ru.academits.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    @Scheduled(fixedRate = 1000000)
    public void sendEmailWithContactList(){
        System.out.println("Scheduler!");
    }
}
