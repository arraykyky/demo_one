package com.springcloud.book.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.springcloud.book.zuul.config.redis.RedisServer;
import com.springcloud.book.zuul.config.token.BookNoTokenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ValidataTokenFilter extends ZuulFilter {

    private BookNoTokenConfig bookNoTokenConfig;

    public ValidataTokenFilter(BookNoTokenConfig bookNoTokenConfig){
        this.bookNoTokenConfig = bookNoTokenConfig;
    }

    @Autowired
    private RedisServer redisServer;

    //拦截器类型，pro/post/route
    @Override
    public String filterType() {
        return "pre";
    }

    //执行顺序，数字越小越先执行
    @Override
    public int filterOrder() {
        return 100;
    }

    //拦截器是否生效
    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return !bookNoTokenConfig.getNoAuthenticationRoutes().contains(context.get("requestURI"));
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        Object hasValue = redisServer.get(request.getParameter("token"));
        if (hasValue!=null){
            context.setResponseStatusCode(200);
            context.setSendZuulResponse(true);
            context.set("isSuccess",true);
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("responseCode",400);
            jsonObject.put("responseMsg","用户登录失效");
            context.setResponseStatusCode(400);
            context.setSendZuulResponse(false);
            context.set("isSuccess",false);
            context.getResponse().setContentType("text/html;charset=utf-8");
            context.setResponseBody(jsonObject.toJSONString());
        }
        return null;
    }
}
