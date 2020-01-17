package cloud.dao;

import cloud.entity.Relationship;
import org.apache.ibatis.annotations.Param;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/12/4 11:10
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
public interface RelationshipMapper {

    /**
     * 删除学生和家长关系
     *
     * @param parentId  家长id
     * @param studentId 学生id
     */
    void deleteRelationship(@Param("parentId") Long parentId, @Param("studentId") Long studentId);

    void insert(Relationship relationship);
}
