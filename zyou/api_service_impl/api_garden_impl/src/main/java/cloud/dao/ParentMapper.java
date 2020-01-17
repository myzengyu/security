package cloud.dao;

import cloud.entity.Parent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 2 * @Author:
 * 3 * @Date: 2019/11/29 16:47
 * 4
 */
public interface ParentMapper {


    /**
     * 查询该电话号码是否被注册
     *
     * @param tel
     */
    Parent selectByPhone(@Param("tel") String tel);

    /**
     * 添加新的家长用户
     *
     * @param parent
     */
    void addParent(Parent parent);

    /**
     * 修改家长的信息
     *
     * @param parent
     */
    void update(Parent parent);

    /**
     * 添加家长和学生的关系
     *
     * @param parentId
     * @param typeId
     * @param studentId
     */

    void addParentAndStudent(@Param("parentId") Long parentId, @Param("typeId") Long typeId, @Param("studentId") Integer studentId);

    /**
     * 对家长信息进行伪删除
     *
     * @param parentId
     * @param i
     */
    void updateParent(@Param("parentId") Integer parentId, @Param("isDel") int i);

    /**
     * 解除当前家长的所有学生绑定
     *
     * @param parentId
     */
    void UnbindWithStudent(@Param("parentId") Integer parentId);

    /**
     * 根据查询条件查询分页信息
     *
     * @param parent
     * @return
     */
    List<Parent> getParentPage(Parent parent);

    /**
     * 根据ID查询家长信息
     *
     * @param id
     * @return
     */
    Parent searchById(@Param("id") Long id);

    /**
     * 查询家长信息
     *
     * @param parentId
     */
    String selectById(Long parentId);
}
