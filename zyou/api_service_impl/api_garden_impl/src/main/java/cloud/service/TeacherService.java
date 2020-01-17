package cloud.service;

import cloud.common.exception.BizException;
import cloud.entity.Teacher;
import cloud.entity.TeacherInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/29 9:26
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
public interface TeacherService {
    /**
     * 根据手机号查询老师
     *
     * @param tel
     * @return
     */
    List<Teacher> findTeacherByTel(String tel);

    /**
     * 添加老师信息
     *
     * @param teacher
     */
    void addTeacher(Teacher teacher) throws BizException;

    /**
     * 根据老师ID查询老师信息
     *
     * @param id
     */
    Teacher findTeacherById(@Param("id") Long id);

    /**
     * 修改老师信息，并修改老师和班级学校的关系
     *
     * @param teacher     新老师的信息
     * @param teacherById 数据库查询到的旧老师的信息
     */
    void updateTeacher(Teacher teacher, Teacher teacherById) throws BizException;

    /**
     * 老师信息做假删除
     *
     * @param teachId
     * @param isDel
     */
    void updateIsDel(Integer teachId, Integer isDel);

    /**
     * 根据搜索条件查询符合条件的老师
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
    List<Map<Integer, String>> getAreaList(Integer areaId);

    /**
     * 批量操作老师添加操作
     *
     * @param teacherInfos
     * @return
     */
    Map<String, Object> teacherImport(List<TeacherInfo> teacherInfos) throws BizException;
}
