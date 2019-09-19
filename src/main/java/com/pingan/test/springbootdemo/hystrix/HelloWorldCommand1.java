package com.pingan.test.springbootdemo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

/**
 * {@link HelloWorldCommand1}
 */
public class HelloWorldCommand1 extends HystrixCommand<String> {

    private final String name;

    public HelloWorldCommand1(String name) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
                /*配置依赖超时时间,500毫秒*/
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)));
        this.name = name;
    }

    @Override
    protected String getFallback() {
        return "getFallback executed" + super.getExecutionException();
    }

    @Override
    protected String run() throws Exception {
        //sleep 1 秒,调用会超时
        TimeUnit.MILLISECONDS.sleep(1000);
        return "Hello " + name + " thread:" + Thread.currentThread().getName();
    }

    public static void main(String[] args) throws Exception {
        HelloWorldCommand1 command = new HelloWorldCommand1("test-Fallback");
        String result = command.execute();
        System.out.println(result);
    }

}
