package com.pingan.test.springbootdemo;

import com.pingan.test.springbootdemo.hystrix.HelloWorldHystrixCommand;
import org.junit.Assert;
import org.junit.Test;

public class HystrixCommand4ExecuteTest {

    @Test
    public void testExecute() {
        // execute()是同步堵塞式执行：先创建一个线程运行HelloWorldHystrixCommand.run()，再返回往下执行
        // 一个对象只能execute()一次
        // execute()事实上是queue().get()
        System.out.println("mainthread: " + Thread.currentThread().getName());
        String executeResult = new HelloWorldHystrixCommand("Hlx").execute();
        System.out.println("execute同步结果：" + executeResult);
        Assert.assertEquals("Hello", executeResult.substring(0, 5));
    }

}
