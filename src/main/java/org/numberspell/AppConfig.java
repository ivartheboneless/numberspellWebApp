package org.numberspell;

import org.numberspell.data.Dictionary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class AppConfig {
    public static void main(String[] args) {
        //loading words into the dictionary
        Dictionary.load();
        SpringApplication.run(AppConfig.class, args);
    }
}
