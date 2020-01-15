package cloud.spring_zuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 2 * @Author:
 * 3 * @Date: 2020/1/9 10:52
 * 4
 */
@Configuration
public class ResourceFilter {

    private final String RESOURCE_ID = "res1";
    @Autowired
    private TokenStore tokenStore;

    //安全的配置文件
    //开起资源控制服务
    @Configuration
    @EnableResourceServer
    public class SecurityServerConfig extends ResourceServerConfigurerAdapter {

        public void configure(ResourceServerSecurityConfigurer resource) throws Exception {
            resource.tokenStore(tokenStore).resourceId(RESOURCE_ID).stateless(true);
        }

        public void configure(HttpSecurity http) throws Exception {
            //把uaa访问的所有路劲全部放过
            http.authorizeRequests().antMatchers("/uaa/**").permitAll();
        }

    }

    //其他service的资源访问限制
    @Configuration
    @EnableResourceServer
    public class TestServiceConfig extends ResourceServerConfigurerAdapter {
        public void configure(ResourceServerSecurityConfigurer resource) throws Exception {
            resource.tokenStore(tokenStore).resourceId(RESOURCE_ID)
                    .stateless(true);
        }

        public void configure(HttpSecurity http) throws Exception {
            //把test访问的所有路径进行校验，看scope中是否含有role_api
            http
                    .authorizeRequests()
                    .antMatchers("/member/**").access("#oauth2.hasScope('ROLE_API')");
        }
    }
}
