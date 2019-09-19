package com.pingan.test.springbootdemo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * {@link HelloWorldCommand2}
 */
public class HelloWorldCommand2 extends HystrixCommand<String> {

    private final String name;

    public HelloWorldCommand2(String name) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
                /* 配置信号量隔离方式,默认采用线程池隔离 */
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        TimeUnit.MILLISECONDS.sleep(900);
        return "HystrixThread:" + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "fall back HystrixThread:" + Thread.currentThread().getName();
    }

    public static void main(String[] args) throws Exception {
        HelloWorldCommand2 command = new HelloWorldCommand2("semaphore");
        String result = command.execute();
        System.out.println(result);
        System.out.println("MainThread:" + Thread.currentThread().getName());


        HelloWorldCommand2 command1 = new HelloWorldCommand2("semaphore");
        Future<String> future = command1.queue();
        System.out.println(future.get());
        System.out.println("MainThread:" + Thread.currentThread().getName());
    }

}
