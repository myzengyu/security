package cloud.spring_zuul.filter;

import cloud.spring_zuul.common.EncryptUtil;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 2 * @Author:
 * 3 * @Date: 2020/1/8 17:51
 * 4
 */

public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURI);
        System.out.println(requestURL);
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        //从上下文中 拿到用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //是否属于OAuth2的验证信息
        if (!(authentication instanceof OAuth2Authentication))
            return null;
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        //把验证信息转化成security类型的，然后获取用户验证信息
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        //获取用户信息
        String principal = userAuthentication.getName();
        //获取用户权限,把他添加到新的list集合里面去
        List<String> authorities = new ArrayList<>();
        //遍历每一个权限，获取里面的权限信息
        userAuthentication.getAuthorities().stream().forEach(auth -> authorities.add(((GrantedAuthority) auth).getAuthority()));

        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
        //把获取到的parameters放到新的hashMap中去
        Map<String, Object> jsonToken = new HashMap<>(requestParameters);
        if (userAuthentication != null) {
            jsonToken.put("principal", principal);
            jsonToken.put("authorities", authorities);
        }
        //把Auth中解密好的明文信息转成json加密后储存到zuul的请求头中去
        currentContext.addZuulRequestHeader("token-json", EncryptUtil.encodeUTF8StringBase64(JSONObject.toJSONString(jsonToken)));
        return null;
    }
}
