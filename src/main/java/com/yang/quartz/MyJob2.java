package com.yang.quartz;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.quaartz
 * @Description: TODO
 * @date Date : 2019年08月27日 13:50
 */
public class MyJob2 extends QuartzJobBean {
    HelloServiceA helloService;
    public HelloServiceA getHelloService() {
        return helloService;
    }
    public void setHelloService(HelloServiceA helloService) {
        this.helloService = helloService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        helloService.sayHello();
    }
}
