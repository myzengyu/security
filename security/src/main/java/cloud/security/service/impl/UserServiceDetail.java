package cloud.security.service.impl;

import cloud.security.dao.UserMapper;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 2 * @Author:
 * 3 * @Date: 2019/12/26 11:19
 * 4
 */
@Service
public class UserServiceDetail implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        cloud.security.entity.User byUsername = userMapper.findByUsername(username);
        String userJson = JSONObject.toJSONString(byUsername);
        List<String> listAuthorities = userMapper.getListAuthorities(byUsername.getId()).stream().filter(s -> s.trim().replace("[\\s*\t\n\r]","") != "").distinct().collect(Collectors.toList());
        String[] authorities = listAuthorities.toArray(new String[listAuthorities.size()]);
        UserDetails user = User.withUsername(userJson).password(byUsername.getPassword()).authorities(authorities).build();
        return user;
    }
}
