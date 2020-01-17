package cloud.dao;

import cloud.entity.Type;

import java.util.List;

/**
 * 2 * @Author:
 * 3 * @Date: 2019/12/2 16:49
 * 4
 */
public interface TypeMapper {
    /**
     * 查询所有老师的分类
     *
     * @return
     */
    List<Type> selectAllTeacher();

    /**
     * 获取所有家长的分类
     *
     * @return
     */
    List<Type> selectAllParent();

    Long selectTypeName(String typeName);
}
