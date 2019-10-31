package com.springcloud.book.foreign.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义Filter
 * 跨域访问的验证信息配置
 * 一般的在response配置中如果1设置了*，则4就不能是true
 * 所以用过滤器Filter可以使1的设置不会与4的设置冲突
 */
@WebFilter(urlPatterns = "/*")
public class CorsFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        //1.允许所有网段跨域访问
        response.setHeader("Access-Control-Allow-Origin","*");
        //2.允许跨域访问的方法类型
        response.setHeader("Access-Control-Allow-Methods","POST, GET");
        //3.设置跨域控制允许头部
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,Authorization");
        //4.跨域请求保持Cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(request,response);
        logger.info(request.getRequestURI() + " this request taked up time:" + (System.currentTimeMillis() - start) + "ms");
    }
}
