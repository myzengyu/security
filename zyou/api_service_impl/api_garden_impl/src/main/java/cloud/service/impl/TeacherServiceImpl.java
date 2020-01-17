package cloud.service.impl;

import cloud.common.CommonUtil;
import cloud.common.exception.BizException;
import cloud.controller.TeacherController;
import cloud.dao.TeacherMapper;
import cloud.entity.Company;
import cloud.entity.Teacher;
import cloud.entity.TeacherInfo;
import cloud.entity.Type;
import cloud.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/29 9:26
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    public List<Teacher> findTeacherByTel(String tel) {
        List<Teacher> teacherByTel = teacherMapper.findTeacherByTel(tel);
        return teacherByTel;
    }

    @Override
    public void addTeacher(Teacher teacher) throws BizException {
        try {
            if (CommonUtil.isNotEmpty(teacher.getTypes())) {
                teacher.getTypes().stream().map(type -> getType(type));
                if (teacher.getTypes().stream().anyMatch(type -> type.getType() < 2)) {
                    teacher.setIsManage(0);
                } else {
                    teacher.setIsManage(1);
                }
            }
            teacherMapper.addTeacher(teacher);
            teacherMapper.addTeacherAndCompany(teacher);
        } catch (Exception e) {
            throw new BizException(400, "老师机构或者职位信息不完善");
        }
    }

    @Override
    public Teacher findTeacherById(Long id) {
        Teacher teacher = teacherMapper.findTeacherById(id);
        return teacher;
    }

    @Override
    public void updateTeacher(Teacher teacher, Teacher teacherById) throws BizException {
        if (CommonUtil.isNotEmpty(teacher.getTypes())) {
            teacher.getTypes().stream().forEach(type -> getType(type));
            if (teacher.getTypes().stream().anyMatch(type -> type.getType() < 2)) {
                teacher.setIsManage(0);
            } else {
                teacher.setIsManage(1);
            }
        }
        //做老师的修改
        teacherMapper.updateTeacher(teacher);
        //做老师和学校的修改
        Company newCompany = teacher.getCompany();
        Company oldCompany = teacherById.getCompany();
        //老师在该学校新的职位表
        List<Long> types = teacher.getTypes().stream().map(type -> type.getId()).collect(Collectors.toList());
        //查询该老师在该学校是否还有教授班级，如果有不做删除
        List<Integer> integerList = teacherMapper.selectByTechIdAndCompanyId(teacher.getId(), oldCompany.getId());
        if (!integerList.stream().allMatch(integer -> integer == null)) {
            throw new BizException(400, "老师信息已经修改，但老师在该学校还存在教授班级，暂不学校绑定的更换");
        }
        //做机构和老师的关系修改
        teacherMapper.delTechIdAndCompanyId(teacher.getId(), oldCompany.getId());
        teacherMapper.insertTechIdAndCompanyId(teacher.getId(), newCompany.getId(), types);

    }

    @Override
    public void updateIsDel(Integer teachId, Integer isDel) {
        if (0 == Integer.valueOf(isDel)) {
            teacherMapper.updateIsDel(Integer.valueOf(teachId), 1);
        } else {
            teacherMapper.updateIsDel(Integer.valueOf(teachId), 0);
        }
    }

    @Override
    public List<Teacher> getTeacherPage(Teacher teacher) {
        if (CommonUtil.isNotEmpty(teacher.getTypes()))
            teacher.getTypes().stream().forEach(type -> getType(type));
        return teacherMapper.getTeacherPage(teacher);
    }

    @Override
    public List<Map<Integer, String>> getAreaList(Integer areaId) {
        return teacherMapper.getAreaList(areaId);
    }

    @Override
    public Map<String, Object> teacherImport(List<TeacherInfo> teacherInfos) {
//        Map<String, Object> map = new HashMap<>();
//        Integer count = 0;
//        List<Teacher> teachers = teacherInfos.stream().map(teacherInfo -> TeacherController.getTeacher(teacherInfo)).collect(Collectors.toList());
//        for (int i = 0; i < teachers.size(); i++) {
//            Teacher teacher = teachers.get(i);
//            if (CommonUtil.isEmpty(teacher, teacher.getName(), teacher.getTel(), teacher.getCompany(), teacher.getTypes())) {
//                count++;
//                map.put("第" + (i + 1) + "行老师信息错误或者不完整", teacher);
//                continue;
//            }
//            if (CommonUtil.isNotEmpty(teacherMapper.findTeacherByTel(teacher.getTel()))) {
//                map.put("第" + (i + 1) + "行老师电话号码重复,请修改", teacher);
//                count++;
//                continue;
//            } else {
//                try {
//                    if (CommonUtil.isNotEmpty(teacher.getTypes())) {
//                        teacher.getTypes().stream().map(type -> getType(type));
//                        if (teacher.getTypes().stream().anyMatch(type -> type.getType() < 2)) {
//                            teacher.setIsManage(0);
//                        } else {
//                            teacher.setIsManage(1);
//                        }
//                    }
//                    teacherMapper.addTeacher(teacher);
//                    teacherMapper.addTeacherAndCompany(teacher);
//                    teacherMapper.addTeacher(teacher);
//                } catch (Exception e) {
//                    //报异常说明未添加成功
//                    count++;
//                    map.put("第" + (i + 1) + "行" + e.getMessage(), teacher);
//                }
//            }
//        }
//        map.put("成功添加老师" + (teachers.size() - count) + "人", "失败" + count + "人，详情见返回");
        return null;
    }


    Type getType(Type type) {
        if (CommonUtil.isEmpty(type, type.getTypeName()))
            return type;
        switch (type.getTypeName()) {
            case "园长":
                type.setId(6L);
                type.setType(1L);
                break;
            case "副园长":
                type.setId(7L);
                type.setType(1L);
                break;
            case "主班老师":
                type.setId(1L);
                type.setType(2L);
                break;
            case "配班老师":
                type.setId(2L);
                type.setType(2L);
                break;
            case "生活老师":
                type.setId(3L);
                type.setType(2L);
                break;
            default:
                type.setId(12L);
                type.setType(2L);
                break;
        }
        return type;
    }
}
