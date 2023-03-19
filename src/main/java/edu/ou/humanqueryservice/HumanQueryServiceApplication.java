package edu.ou.humanqueryservice;

import edu.ou.coreservice.annotation.BaseQueryAnnotation;
import org.springframework.boot.SpringApplication;

@BaseQueryAnnotation
public class HumanQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HumanQueryServiceApplication.class, args);
    }

}
