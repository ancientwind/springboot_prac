package com.wayne.sbt2.controller;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    public String testGet(@RequestHeader MultiValueMap<String, String> headers, HttpServletRequest request) {
        try {

            System.out.println("Request URI is: " + request.getRequestURI());
            System.out.println("Request IP is: " + request.getRemoteAddr());
            System.out.println("Request HOST is: " + request.getRemoteHost());

            headers.forEach((key, value) -> {
                System.out.println(String.format(
                        "Header '%s' = '%s'", key, value.stream().collect(Collectors.joining("|"))
                ));
            });

            // if request is redirected(like by nginx), to get real ip, config proxy server to forward source ip in header, and then get the specific header
            System.out.println("zzzzz " + request.getHeader("X-Original-Forwarded-For") +
                    request.getHeader("X-Forwarded-For") +
                    request.getHeader("X-Real-Ip"));

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
