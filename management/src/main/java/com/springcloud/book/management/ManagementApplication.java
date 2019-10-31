package com.springcloud.book.management;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {"com.springcloud.book.management.remote"})
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

//    @Bean
//    public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet(){
//        HystrixMetricsStreamServlet hystrixMetricsStreamServlet = new HystrixMetricsStreamServlet();
//        ServletRegistrationBean<HystrixMetricsStreamServlet> streamServletServletRegistrationBean = new ServletRegistrationBean<>();
//        streamServletServletRegistrationBean.setServlet(hystrixMetricsStreamServlet);
//        streamServletServletRegistrationBean.setLoadOnStartup(10);
//        streamServletServletRegistrationBean.addUrlMappings("/hystrix.stream");
//        streamServletServletRegistrationBean.setName("HystrixMetricsStreamServlet");
//        return streamServletServletRegistrationBean;
//    }
}
