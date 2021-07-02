package com.haims.interceptor;

import com.haims.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration//表名这是个配置类。告诉springboot在加载配置时加载此类
public class InterceptorConfig extends WebMvcConfigurerAdapter {//继承WebMvcConfigurerAdapter
    @Override
    public void addInterceptors(InterceptorRegistry registry) {//重写WebMvcConfigurerAdapter中的addInterceptors方法
        registry.addInterceptor(new LoginInterceptor())//new MyInter1()是我们自定义的拦截器类对象
                .addPathPatterns("/*", "/page/*")//  /*代表拦截所有请求。动态参数，多个参数以逗号隔开
                .excludePathPatterns("/",
                        "/static/picture/favicon.ico",
                        "/static/image/**");//不拦截/inser这个请求。这也是个动态参数，有多个不拦截的请求时，以逗号隔开
        super.addInterceptors(registry);
    }
}

