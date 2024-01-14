package com.example.markethome;

import com.example.markethome.FIle.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableScheduling
public class SemoconectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemoconectApplication.class, args);
    }

}
