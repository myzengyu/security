package cloud.service.impl;

import cloud.common.CommonUtil;
import cloud.common.exception.BizException;
import cloud.dao.*;
import cloud.entity.*;
import cloud.service.StudentService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/29 9:26
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassesMapper classesMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private ParentMapper parentMapper;
    @Autowired
    private RelationshipMapper relationshipMapper;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(Student student) throws BizException {
        List<String> relationshipList = student.getRelationship();
        List<String> phones = student.getParentPhoneNum();
        List<String> parentName = student.getParentName();
        StringBuffer stringBuffer = new StringBuffer("");
        String tel = "";
        for (int i = 0; i < phones.size(); i++) {
            Student students = studentMapper.selectStudentNameByParentTel(student.getName(), phones.get(i));
            if (students != null) {
                throw new BizException(400, "当前学生已经存在!");
            }
            tel = phones.get(i);
            if (phones.size() != i + 1) {
                stringBuffer.append(tel + "#");
            } else {
                stringBuffer.append(tel);
            }
        }
        student.setParentTels(stringBuffer.toString());
        studentMapper.insert(student);
        //添加家长
        for (int j = 0; j < phones.size(); j++) {
            Parent parent = new Parent();
            parent = parentMapper.selectByPhone(phones.get(j));
            if (parent == null) {
                parent = new Parent();
                parent.setTel(phones.get(j));
                parent.setName(parentName.get(j));
                parentMapper.addParent(parent);
            } else {
                relationshipMapper.deleteRelationship(parent.getId(), student.getId());
                parent.setName(parentName.get(j));
                parentMapper.update(parent);
            }
            //家长+学生关系
            Relationship relationship = new Relationship();
            relationship.setParentId(parent.getId());
            relationship.setStudentId(student.getId());
            relationship.setParentType(Integer.parseInt(relationshipList.get(j)));
            relationshipMapper.insert(relationship);
        }

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Student student) {
        List<String> relationshipList = student.getRelationship();
        List<String> phones = student.getParentPhoneNum();
        List<String> parentName = student.getParentName();
        StringBuffer stringBuffer = new StringBuffer("");
        String tel = "";
        for (int i = 0; i < phones.size(); i++) {
            tel = phones.get(i);
            if (phones.size() != i + 1) {
                stringBuffer.append(tel + "#");
            } else {
                stringBuffer.append(tel);
            }
        }
        student.setParentTels(stringBuffer.toString());
        //添加家长
        for (int j = 0; j < phones.size(); j++) {
            Parent parent = new Parent();
            parent = parentMapper.selectByPhone(phones.get(j));
            if (parent == null) {
                parent = new Parent();
                parent.setTel(phones.get(j));
                parent.setName(parentName.get(j));
                parentMapper.addParent(parent);
            } else {
                relationshipMapper.deleteRelationship(parent.getId(), student.getId());
                parent.setName(parentName.get(j));
                parentMapper.update(parent);
            }
            //家长+学生关系
            Relationship relationship = new Relationship();
            relationship.setParentId(parent.getId());
            relationship.setStudentId(student.getId());
            relationship.setParentType(Integer.parseInt(relationshipList.get(j)));
            relationshipMapper.insert(relationship);
        }
        studentMapper.update(student);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delByIds(Long id) {
        studentMapper.delByIds(id);
    }

    @Override
    public Student selectById(Long id) {
        return studentMapper.selectById(id);
    }

    @Override
    public List<Student> selectByProperty(Student student) {
        return studentMapper.selectByProperty(student);
    }

    /**
     * 学生转班
     *
     * @param student
     * @throws BizException
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateStudentClass(Student student) throws BizException {
        //加了 共享锁
        Classes classes = classesMapper.selectId(student.getClassId());
        if (classes == null) {
            throw new BizException(400, "当前班级不存在");
        } else if (classes.getId() == student.getClassId()) {
            throw new BizException(400, "当前转班的班级和当前班级一样");
        }
        String[] studentIds = student.getStuId().split(",");
        studentMapper.updateStudentClass(student.getClassId(), studentIds);
    }

    /**
     * 2 恢复   4毕业
     *
     * @param state
     * @throws BizException
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateStudentState(Integer state, String stuId) throws BizException {
        if (state.equals("2") || state.equals("4")) {
            String[] studentIds = stuId.split(",");
            studentMapper.updateStudentState(state, studentIds);
        } else {
            throw new BizException(400, "state参数传递错误");
        }
    }

    /**
     * 学生导入 判断传入参数
     *
     * @param studentDTO
     * @throws BizException
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void importStudent(StudentImpDTO studentDTO) throws BizException {
        if (CommonUtil.isEmpty(studentDTO.getClassId())) {
            throw new BizException(400, "当前班级id参数不存在");
        }
        Classes classes = classesMapper.selectById(studentDTO.getClassId());
        if (classes == null) {
            throw new BizException(400, "当前班级不存在");
        }
        if (CommonUtil.isEmpty(studentDTO.getStuName())) {
            throw new BizException(400, "当前学生名称参数不存在");
        }
        if (CommonUtil.isEmpty(studentDTO.getSex())) {
            throw new BizException(400, "当前学生性别参数不存在");
        }
        if (CommonUtil.isEmpty(studentDTO.getBirthday())) {
            throw new BizException(400, "当前学生生日参数不存在");
        }
        List<String> relationship = studentDTO.getRelationship();
        List<String> phones = studentDTO.getParentPhoneNum();
        List<String> parentName = studentDTO.getParentName();
        if (relationship.size() == 0 && phones.size() == 0) {
            throw new BizException(400, "家长信息不全!");
        }
        //家长类型设置
        Set<String> rSet = new HashSet<String>(relationship);
        Collection rs = CollectionUtils.disjunction(relationship, rSet);
        List<String> rlist = new ArrayList<String>(rs);
        if (rlist.size() > 0) {
            throw new BizException();
        }
        for (int i = 0; i < relationship.size(); i++) {
            String custType = relationship.get(i);
            if (CommonUtil.isEmpty(custType)) {
                throw new BizException(400, "家长类型错误!");
            }
            //获取家长类型对应的ID
            Long typeId = typeMapper.selectTypeName(custType);
            if (CommonUtil.isEmpty(typeId)) {
                throw new BizException(400, "家长类型错误!");
            }
            int type = Integer.parseInt(String.valueOf(typeId));
            relationship.remove(i);
            relationship.add(i, typeId.toString());
            String parName = "";
            switch (type) {
                case 4:
                    parName = "妈妈";
                    break;
                case 5:
                    parName = "爷爷";
                    break;
                case 10:
                    parName = "爸爸";
                    break;
                case 11:
                    parName = "奶奶";
                    break;
                case 14:
                    parName = "外公";
                    break;
                case 15:
                    parName = "外婆";
                    break;
                case 16:
                    parName = "其他";
                    break;
            }
            if (parentName != null) {
                if ("".equals(parentName.get(i))) {
                    parentName.remove(i);
                    parentName.add(i, parName);
                }
            } else {
                parentName.remove(i);
                parentName.add(i, parName);
            }
        }
        studentDTO.setParentName(parentName);
        //家长电话
        Set<String> set = new HashSet<String>(phones);
        Collection disjunction = CollectionUtils.disjunction(phones, set);
        List<String> list = new ArrayList<String>(disjunction);
        if (list.size() > 0) {
            throw new BizException(400, "家长电话号码重复!");
        }
        for (int i = 0; i < phones.size(); i++) {
            if (CommonUtil.isNotNumeric(phones.get(i))) {
                throw new BizException(400, "家长电话号码错误");
            }
            if (phones.get(i).length() != 11) {
                throw new BizException(400, "家长电话号码格式错误!");
            }
            Student student = studentMapper.selectStudentNameByParentTel(studentDTO.getStuName(), phones.get(i));
            if (student != null) {
                throw new BizException(400, "当前学生已经存在!");
            }
        }

    }

    /**
     * 学生导入 添加数据
     *
     * @param studentDTOList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void batchInsert(List<StudentImpDTO> studentDTOList) {

        for (int i = 0; i < studentDTOList.size(); i++) {
            Parent parent = new Parent();
            List<String> phones = studentDTOList.get(i).getParentPhoneNum();
            List<String> parentName = studentDTOList.get(i).getParentName();
            List<String> relationshipList = studentDTOList.get(i).getRelationship();
            //添加学生信息
            StudentImpDTO studentDTO = studentDTOList.get(i);
            Student student = new Student();
            student.setName(studentDTO.getStuName());
            if (studentDTO.getSex().equals("男")) {
                student.setAge(0);
            } else {
                student.setAge(1);
            }
            student.setBirthday(studentDTO.getBirthday());
            student.setClassId(studentDTO.getClassId());
            StringBuffer stringBuffer = new StringBuffer("");
            String tel = "";
            for (int j = 0; j < phones.size(); j++) {
                tel = phones.get(j);
                if (phones.size() != j + 1) {
                    stringBuffer.append(tel + "#");
                }
            }
            student.setParentTels(stringBuffer.toString());
            studentMapper.insert(student);
            //添加家长
            for (int j = 0; j < phones.size(); j++) {
                parent = parentMapper.selectByPhone(phones.get(j));
                if (parent == null) {
                    parent = new Parent();
                    parent.setTel(phones.get(j));
                    parent.setName(parentName.get(j));
                    parentMapper.addParent(parent);
                } else {
                    relationshipMapper.deleteRelationship(parent.getId(), student.getId());
                    parent.setName(parentName.get(j));
                    parentMapper.update(parent);
                }
                //家长+学生关系
                Relationship relationship = new Relationship();
                relationship.setParentId(parent.getId());
                relationship.setStudentId(student.getId());
                relationship.setParentType(Integer.parseInt(relationshipList.get(j)));
                relationshipMapper.insert(relationship);
            }

        }


    }
}
