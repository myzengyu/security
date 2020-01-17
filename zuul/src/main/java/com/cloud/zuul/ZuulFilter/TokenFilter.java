package com.cloud.zuul.ZuulFilter;

import com.alibaba.fastjson.JSONObject;
import com.cloud.zuul.common.EncryptUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/10/24 16:34
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
public class TokenFilter extends ZuulFilter {
    /**
     * 过滤类型
     * Zuul中的标准类型是用于预路由过滤的“pre”，
     * 用于路由到原点的“route”，用于后路由过滤的“post”，用于错误处理的“error”。
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 开启网关
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;

    }

    /**
     * 把加密的token解密成明文并转发到个个相应的模块去
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        //从上下文获取验证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //是否属于OAuth2的验证信息
        if (!(authentication instanceof OAuth2Authentication))
            return null;
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        //获取用户验证信息
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        //获取用户详情
        String principal = userAuthentication.getName();
        //获取权限信息
        List<String> authorities = new ArrayList<>();
        userAuthentication.getAuthorities().forEach(s -> authorities.add(s.getAuthority()));
        //从验证信息里面获取其他请求的信息
        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
        Map<String, Object> jsonMap = new HashMap<>(requestParameters);
        jsonMap.put("principal", principal);
        jsonMap.put("authorities", authorities);
        //存储到头部信息里面去并转发
        context.addZuulRequestHeader("token-json", EncryptUtil.encodeUTF8StringBase64(JSONObject.toJSONString(jsonMap)));
//        context.addZuulRequestHeader("token-json", JSONObject.toJSONString(jsonMap));
        return null;
    }
}
