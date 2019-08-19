package com.wayne.sbt2.domain;

import com.google.common.util.concurrent.RateLimiter;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 212331901
 * @date 2019/7/17
 */
public class RateLimiterTest {

    private static int counter;

    public static void main(String[] args) throws InterruptedException {
        RateLimiter limiter = RateLimiter.create(1);
        for (int i = 0; i < 10; i++) {
            performOperation(limiter);
            Thread.sleep(100);
        }
    }

    private static void performOperation(RateLimiter limiter) {
        if (limiter.tryAcquire()){
            System.out.println(Instant.now() + ": Beep " + (++counter));
        }
//        limiter.acquire(5); // will block until get enough permits
//            System.out.println(Instant.now() + ": Beep " + (++counter));
    }
}
