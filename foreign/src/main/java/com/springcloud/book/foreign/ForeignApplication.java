package com.springcloud.book.foreign;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@ServletComponentScan
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard //监控页面组件
@EnableCircuitBreaker   //启动断路器
@EnableFeignClients(basePackages = {"com.springcloud.book.foreign.remote"})
public class ForeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForeignApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet(){
        HystrixMetricsStreamServlet hystrixMetricsStreamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean<HystrixMetricsStreamServlet> streamServletServletRegistrationBean = new ServletRegistrationBean<>();
        streamServletServletRegistrationBean.setServlet(hystrixMetricsStreamServlet);
        streamServletServletRegistrationBean.setLoadOnStartup(1);
        streamServletServletRegistrationBean.addUrlMappings("/hystrix.stream");
        streamServletServletRegistrationBean.setName("HystrixMetricsStreamServlet");
        return streamServletServletRegistrationBean;
    }
}
