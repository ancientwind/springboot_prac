package com.wayne.sbt2.controller;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("pear");

        Observable<Object> test = Observable.fromIterable(list);
        list.add("orange");
        test.throttleLast(1, TimeUnit.MILLISECONDS)
                .map(s -> "result: " + s)
                .subscribe(System.out::println);
    }
}
