package com.yang.test;



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
        B b = new B();
        VolatileTest test = new VolatileTest(b);
        Thread t = new Thread(test);
        t.start();
        System.out.println("启动线程");
        Thread.sleep(1000);
        test.stop();
        Thread.sleep(1000);
        System.out.println("主线程结束");

    }
}
