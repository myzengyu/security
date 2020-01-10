package cloud.spring_zuul.config;

import cloud.spring_zuul.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 2 * @Author:
 * 3 * @Date: 2020/1/8 17:57
 * 4
 */
@Configuration
public class ZuulConfig {
    //把Zuul的规则的添加到spring容器里面
    @Bean
    public AuthFilter preFilter() {
        return new AuthFilter();
    }

    //设置跨域信息
    @Bean
    public FilterRegistrationBean CORSFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setMaxAge(18000L);
        source.registerCorsConfiguration("/**", config);
        CorsFilter corsFilter = new CorsFilter(source);
        FilterRegistrationBean bean = new FilterRegistrationBean(corsFilter);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
