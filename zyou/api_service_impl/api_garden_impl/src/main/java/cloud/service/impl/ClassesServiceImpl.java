package cloud.service.impl;

import cloud.common.CommonUtil;
import cloud.common.exception.BizException;
import cloud.dao.ClassesMapper;
import cloud.dao.CompanyMapper;
import cloud.dao.StudentMapper;
import cloud.entity.Classes;
import cloud.entity.Company;
import cloud.entity.Student;
import cloud.service.ClassesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/28 9:22
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Service
public class ClassesServiceImpl implements ClassesService {

    private final Logger log = LoggerFactory.getLogger(ClassesServiceImpl.class);
    @Autowired
    private ClassesMapper classesMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 通过班级id  查询
     *
     * @param id 班级id
     * @return
     */
    @Override
    public Classes selectById(Long id) {
        return classesMapper.selectById(id);
    }

    @Override
    public List<Classes> selectCompanyIdByClass(Long companyId, String className) {
        return classesMapper.selectByCompanyIdAndClassName(companyId, className);
    }

    /**
     * 通过班级其他信息 查询班级集合，为空查询全部
     *
     * @param classes
     * @return
     */
    @Override
    public List<Classes> selectByProperty(Classes classes) {
        return classesMapper.selectByProperty(classes);
    }

    /**
     * 班级添加
     *
     * @param classes 班级对象
     */
    @Override
    public void insertClasses(Classes classes) {
        classesMapper.insert(classes);
    }

    /**
     * 班级修改
     *
     * @param classes 班级对象
     */
    @Override
    public void updateClasses(Classes classes) throws BizException {
        if (CommonUtil.isEmpty(classes) || CommonUtil.isEmpty(classes.getId())) {
            log.info("班级修改请求参数：" + classes);
            throw new BizException(400, "参数错误");
        }
        classesMapper.update(classes);
    }

    /**
     * 删除班级
     *
     * @param id 班级id
     */
    @Override
    public void delectClasses(Long id) throws BizException {
        List<Student> studentList = studentMapper.selectClassIdByStudent(id);
        if (studentList != null && studentList.size() > 0) {
            throw new BizException(400, "当前班级存在学生");
        }
        classesMapper.deleteById(id);
    }

    /**
     * 通过学校id 查询班级
     *
     * @param cid 学校id
     * @return
     */
    @Override
    public List<Classes> selectComByClass(Long cid) {
        return classesMapper.selectComByClass(cid);
    }

    /**
     * 班级毕业
     *
     * @param id 班级id
     */
    @Override
    public void updateGraduated(Long id) {
        classesMapper.updateGraduated(id);
    }

    @Override
    public void importClasses(Classes classes) throws BizException {

        if (CommonUtil.isEmpty(classes.getCompanyId())) {
            throw new BizException("当前学校id参数不存在");
        }
        if (CommonUtil.isEmpty(classes.getCompanyName())) {
            throw new BizException("当前学校名称参数不存在");
        }
        if (CommonUtil.isEmpty(classes.getGrade())) {
            throw new BizException("当前年级参数不存在");
        }
        if (CommonUtil.isEmpty(classes.getName())) {
            throw new BizException("当前班级名称参数不存在");
        }
        Company company = companyMapper.selectById(classes.getCompanyId());
        if (company == null) {
            throw new BizException("当前学校id参数错误");
        }
        List<Classes> classesList1 = classesMapper.selectByCompanyIdAndClassName(classes.getCompanyId(), classes.getName());
        if (classesList1 != null || classesList1.size() > 0) {
            throw new BizException("当前班级已经存在");
        }
    }

    /**
     * 批量添加 班级
     *
     * @param classesList 班级集合
     */
    @Override
    public void batchInsert(List<Classes> classesList) {
        classesMapper.batchInsert(classesList);
    }

}
