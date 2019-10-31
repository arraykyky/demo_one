package com.springcloud.book.decompression;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DecompressionApplication {

    /**
     *  xml文件解压项目
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DecompressionApplication.class, args);
    }

}
