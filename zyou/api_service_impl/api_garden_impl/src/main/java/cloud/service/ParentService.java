package cloud.service;

import cloud.common.exception.BizException;
import cloud.entity.Parent;

import java.util.List;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/29 9:26
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
public interface ParentService {

    /**
     * 根据搜索条件获取分页信息
     *
     * @param parent
     * @return
     */
    List<Parent> getParentPage(Parent parent);

    /**
     * 根据家长ID查询该家长是否已经存在
     *
     * @param id
     * @return
     */
    Parent searchById(Long id);

    void updateParent(Parent data) throws BizException;

    /**
     * 添加新的家长信息，添加的时候校验手机号是否已经被注册过了
     *
     * @param parents
     */
    void createParent(List<Parent> parents, Integer studentId) throws Exception;

    /**
     * 对家长信息进行伪删除
     *
     * @param parentId
     * @param isDel
     */
    void updateParent(Integer parentId, Integer isDel);
}
