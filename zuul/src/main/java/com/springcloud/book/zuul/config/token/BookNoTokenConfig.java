package com.springcloud.book.zuul.config.token;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("book.zuul.token-filter")
public class BookNoTokenConfig {
    //将放行路由放入集合
    private List<String> noAuthenticationRoutes;

    public List<String> getNoAuthenticationRoutes() {
        return noAuthenticationRoutes;
    }

    public void setNoAuthenticationRoutes(List<String> noAuthenticationRoutes) {
        this.noAuthenticationRoutes = noAuthenticationRoutes;
    }
}
