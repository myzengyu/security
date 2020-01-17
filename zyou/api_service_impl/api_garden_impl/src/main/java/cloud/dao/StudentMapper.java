package cloud.dao;

import cloud.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/29 9:26
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
public interface StudentMapper {

    void insert(Student student);

    Student selectById(Long id);

    List<Student> selectByProperty(Student student);

    void update(Student student);

    void delByIds(Long id);

    List<Student> selectClassIdByStudent(Long classId);

    /**
     * 批量转班
     *
     * @param classId
     * @param studentIds
     */
    void updateStudentClass(@Param("classId") Long classId, @Param("studentIds") String[] studentIds);

    /**
     * 批量 恢复、毕业
     *
     * @param state      状态 2恢复  4毕业
     * @param studentIds 学生ids
     */
    void updateStudentState(@Param("state") Integer state, @Param("studentIds") String[] studentIds);

    List<Student> batchInsert(List<Student> list);

    /**
     * 判断学生唯一
     *
     * @param name
     * @param tel
     * @return
     */
    Student selectStudentNameByParentTel(@Param("name") String name, @Param("tel") String tel);

    /**
     * 查询与该家长绑定的学生信息
     *
     * @param parentId
     */
    List<Student> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 家长修改电话后修改学生的信息
     *
     * @param studentId
     * @param parentTels
     */
    void updateParentTels(@Param("studentId") Long studentId, @Param("parentTels") String parentTels);
}
