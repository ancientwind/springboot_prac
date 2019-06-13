package com.wayne.sbt2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 212331901
 * @date 2019/6/12
 */
@RestController
public class RabbitmqController {

    public static final String URL = "http://localhost:8080/health";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("test-get")
    public String testGet() {
        try {
            System.out.println("START get at: " + LocalDateTime.now());
            ResponseEntity<String> result = restTemplate.getForEntity(URL, String.class);
            System.out.println("END get at: " + LocalDateTime.now());
            System.out.println("zzz" + result.getStatusCode());
            System.out.println("zzz" + result.getHeaders());
            return "succeed";
        } catch (Exception e) {
            System.out.println("catch get at: " + LocalDateTime.now());
            System.out.println(e.getStackTrace());
            return "failed";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("start get at: " + LocalDateTime.now());
        AtomicBoolean test = new AtomicBoolean(false);

        Thread thread1 = new Thread( () -> {
            int i = 0;
            while (!test.get()) {
                System.out.println(i++);
                if (i==5) {break;}
//                if (Thread.currentThread().isInterrupted()) {
//                    System.out.println("interrupted");
//                    break;}
                else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("interrupted");
                        e.printStackTrace();
                        break;
                    }
                }
            }
        } );
        thread1.start();

        Thread.sleep(3200);
//        test.set(true);
        thread1.interrupt();
    }
}
