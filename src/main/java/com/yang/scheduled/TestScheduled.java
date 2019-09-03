package com.yang.scheduled;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.scheduled
 * @Description: TODO
 * @date Date : 2019年08月27日 10:12
 */
@Component
public class TestScheduled {
    SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Scheduled(fixedRate = 2000)
    public void fixedRate() {
        System.out.println("fixedRate>>>"+simpleDateFormat.format(new Date()));
    }
    @Scheduled(fixedDelay = 2000)
    public void fixedDelay() {
        System.out.println("fixedDelay>>>"+simpleDateFormat.format(new Date()));
    }
    @Scheduled(initialDelay = 2000,fixedDelay = 2000)
    public void initialDelay() {
        System.out.println("initialDelay>>>"+simpleDateFormat.format(new Date()));
    }
    @Scheduled(cron = "0/5 * * * * *")
    public void cron() {
        System.out.println("corn>>>"+simpleDateFormat.format(new Date()));
    }
}
