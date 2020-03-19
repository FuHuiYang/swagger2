package com.yang.test;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.test
 * @Description: TODO
 * @date Date : 2019年09月12日 16:34
 */
public class VolatileTest implements Runnable {
    private B b;
    //    private volatile  B b;
    public VolatileTest(B b) {
        this.b = b;
    }
    @Override
    public void run() {
        while (b.getFlag()){
            System.out.println(b.getFlag());//①
        }
        System.out.println("线程结束");
    }
    public void stop(){
        b.setFlag(false);
    }

    public static void main(String[] args) throws InterruptedException {
//        B b = new B();
//        VolatileTest test = new VolatileTest(b);
//        Thread t = new Thread(test);
//        t.start();
//        System.out.println("启动线程");
//        Thread.sleep(1000);
//        test.stop();
//        Thread.sleep(1000);
//        System.out.println("主线程结束");
        String iphone ="173 5959　8131";
        System.out.println(iphone.getBytes());
        RiskCheckRequest request =new RiskCheckRequest();
        LinkManDto linkManDto=new LinkManDto();
        List <LinkManDto>list =new ArrayList();
        linkManDto.setPhone(iphone);
        list.add(linkManDto);
        request.setList(list);
        System.out.println("--"+request.getList().get(0).getPhone()+"--");
        validateParam(request);
        System.out.println("--"+request.getList().get(0).getPhone()+"--");

    }

    private static void validateParam(RiskCheckRequest request) {
        List<LinkManDto> list = request.getList();
        String phone = list.get(0).getPhone();
       if(StringUtils.isBlank(phone)){
           System.out.println(123);
       }
//        request.getList().get(0).setPhone(phone.replaceAll("\\s*", ""));
        request.getList().get(0).setPhone(phone.replace("　", ""));
//        request.getList().get(0).setPhone(phone.replaceAll(" ",""));
    }

}
