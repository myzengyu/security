package cloud.security.dao;

import cloud.security.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2 * @Author:
 * 3 * @Date: 2019/12/26 11:25
 * 4
 */
public interface UserMapper {
    /**
     * 根据用户名称查询用户信息
     * @param username
     * @return
     */
    User findByUsername(@Param("username") String username);

    /**
     * 查询所有权限
     * @param id
     */
    List<String> getListAuthorities(@Param("id") Long id);
}
