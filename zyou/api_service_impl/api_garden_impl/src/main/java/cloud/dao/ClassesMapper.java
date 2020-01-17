package cloud.dao;


import cloud.entity.Classes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/28 9:22
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
public interface ClassesMapper {


    void insert(Classes classes);

    void deleteById(Long id);

    void update(Classes classes);

    Classes selectById(Long id);

    Classes selectId(Long id);

    /**
     * 判断当前学校是否存在重复班级
     *
     * @param companyId
     * @param className
     * @return
     */
    List<Classes> selectByCompanyIdAndClassName(@Param("companyId") Long companyId, @Param("className") String className);

    /**
     * 通过学校id 查询班级
     *
     * @param companyId 学校id
     * @return
     */
    List<Classes> selectCompanyIdByClass(Long companyId);

    /**
     * 通过学校id 查询班级
     *
     * @param cid 学校id
     * @return
     */
    List<Classes> selectComByClass(Long cid);

    List<Classes> selectByProperty(Classes classes);

    /**
     * 班级毕业
     *
     * @param id 班级id
     */
    void updateGraduated(Long id);

    void batchInsert(List<Classes> list);
}
