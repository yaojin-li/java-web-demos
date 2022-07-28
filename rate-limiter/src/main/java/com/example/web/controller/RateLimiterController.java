package com.example.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @Description: 测试 Guava 限流组件 RateLimiter
 * 依赖：
 * <dependency>
 * <groupId>com.google.guava</groupId>
 * <artifactId>guava</artifactId>
 * <version>20.0</version>
 * </dependency>
 *
 * <dependency>
 * <groupId>org.apache.commons</groupId>
 * <artifactId>commons-lang3</artifactId>
 * <version>3.2.1</version>
 * </dependency>
 * --------------------------------------
 * @ClassName: TestRateLimiter.java
 * @Date: 2019/10/10 19:57
 * @SoftWare: IntelliJ IDEA
 * --------------------------------------
 * @Author: lixj
 * @Contact: lixj_zj@163.com
 **/
public class RateLimiterController {
    public static void main(String[] args) {
        // 每秒钟3个令牌放入桶
        double count = 3.0;

        // 构建线程池
        ExecutorService exec = Executors.newCachedThreadPool();

        // 速率：每秒限制3个许可（即每秒钟3个令牌放入桶）
        final RateLimiter rateLimiter = RateLimiter.create(count);

        for (int i = 0; i < 100; i++) {
            final int num = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        // 获取许可（拿到令牌）
                        rateLimiter.acquire();
                        System.out.println("Num:" + num + ", time:" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));

                        // TODO other things
                        // ...
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            // 执行线程
            exec.execute(runnable);
        }
        // 退出线程池
        exec.shutdown();
    }
}









