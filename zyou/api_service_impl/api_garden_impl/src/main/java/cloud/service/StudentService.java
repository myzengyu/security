package cloud.service;

import cloud.common.exception.BizException;
import cloud.entity.Student;
import cloud.entity.StudentImpDTO;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/29 9:26
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
public interface StudentService {


    @RequestMapping("insertStudent")
    public void insert(Student student) throws BizException;

    @RequestMapping("updateStudent")
    public void update(Student student);

    @RequestMapping("deleteStudent")
    public void delByIds(Long id);

    @RequestMapping("selectByStudentId")
    public Student selectById(Long id);

    @RequestMapping("selectByStudent")
    public List<Student> selectByProperty(Student student);

    public void updateStudentClass(Student student) throws BizException;

    public void updateStudentState(Integer state, String stuId) throws BizException;

    public void importStudent(StudentImpDTO studentImpDTO) throws BizException;

    public void batchInsert(List<StudentImpDTO> studentImpDTO);
}
