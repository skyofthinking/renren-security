package io.renren;

import io.renren.zhuoyue.oauth.HTTPOauthAuthorizeAttribute;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebComponentConfig {


    // 注册Filter
    @Bean
    public FilterRegistrationBean jwtFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HTTPOauthAuthorizeAttribute httpOauthAuthorizeAttribute = new HTTPOauthAuthorizeAttribute();
        registrationBean.setFilter(httpOauthAuthorizeAttribute);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/rapi/*");
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(3);
        return registrationBean;
    }

}
