package com.pingan.test.springbootdemo;

import com.pingan.test.springbootdemo.hystrix.HelloWorldHystrixCommand;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class HystrixCommand4QueueTest {

    @Test
    public void testQueue() throws Exception {
        // queue()是异步非堵塞性执行：直接返回，同时创建一个线程运行HelloWorldHystrixCommand.run()
        // 一个对象只能queue()一次
        // queue()事实上是toObservable().toBlocking().toFuture()
        System.out.println("mainthread: " + Thread.currentThread().getName());
        Future<String> future = new HelloWorldHystrixCommand("Hlx").queue();
        // 使用future时会堵塞，必须等待HelloWorldHystrixCommand.run()执行完返回
        String queueResult = future.get(10000, TimeUnit.MILLISECONDS);
        // String queueResult = future.get();
        System.out.println("queue异步结果：" + queueResult);
        Assert.assertEquals("Hello", queueResult.substring(0, 5));
    }

}
