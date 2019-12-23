package com.google.myjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyjobApplication {

    public static void main(String[] args) {
        TimerTool.timerTest();
    }

}
