package cloud.dao;

import cloud.entity.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 2 * @Author:
 * 3 * @Date: 2019/11/29 17:14
 * 4
 */
public interface TeacherMapper {
    /**
     * 根据老师手机号查询老师
     *
     * @param tel
     * @return
     */
    List<Teacher> findTeacherByTel(@Param("tel") String tel);

    /**
     * 添加老师详情
     *
     * @param teacher
     */
    void addTeacher(Teacher teacher);

    /**
     * 添加老师与学校之间的关系
     *
     * @param teacher
     */
    void addTeacherAndCompany(Teacher teacher);

    /**
     * 根据老师ID查询老师信息
     *
     * @param id
     * @return
     */
    Teacher findTeacherById(@Param("id") Long id);

    /**
     * 修改老师的信息
     *
     * @param teacher
     */
    void updateTeacher(Teacher teacher);

    /**
     * 根据老师ID和学校ID查询老师是否在班级授课，如果授课不允许更改
     *
     * @param teachId
     * @param companyId
     */
    List<Integer> selectByTechIdAndCompanyId(@Param("teachId") Long teachId, @Param("companyId") Long companyId);

    /**
     * 删除未授课老师和班级的关联
     *
     * @param teachId
     * @param companyId
     */
    void delTechIdAndCompanyId(@Param("teachId") Long teachId, @Param("companyId") Long companyId);

    /**
     * 重新添加老师和学校的关联
     *
     * @param teachId
     * @param companyId
     * @param types     老师在该学校的职务的集合
     */
    void insertTechIdAndCompanyId(@Param("teachId") Long teachId, @Param("companyId") Long companyId, @Param("types") List<Long> types);

    /**
     * 老师信息做伪删除
     *
     * @param teachId
     * @param isDel   修改后的状态
     */
    void updateIsDel(@Param("teachId") Integer teachId, @Param("isDel") Integer isDel);

    /**
     * 根据搜索条件查询符合条件的老师的信息
     *
     * @param teacher
     */
    List<Teacher> getTeacherPage(Teacher teacher);

    /**
     * 获取地区信息
     *
     * @param areaId
     * @return
     */
    List<Map<Integer, String>> getAreaList(@Param("areaId") Integer areaId);
}
