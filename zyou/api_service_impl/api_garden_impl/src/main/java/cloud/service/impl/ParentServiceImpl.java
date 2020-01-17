package cloud.service.impl;

import cloud.common.CommonUtil;
import cloud.common.exception.ApiException;
import cloud.common.exception.BizException;
import cloud.dao.ParentMapper;
import cloud.dao.StudentMapper;
import cloud.entity.Parent;
import cloud.entity.Student;
import cloud.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 2 * @Author:
 * 3 * @Date: 2019/11/29 13:38
 * 4
 */
@Service
public class ParentServiceImpl implements ParentService {
    @Autowired
    private ParentMapper parentMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Parent> getParentPage(Parent parent) {
        return parentMapper.getParentPage(parent);
    }


    @Override
    public Parent searchById(Long id) {
        return parentMapper.searchById(id);

    }

    @Override
    public void updateParent(Parent data) throws BizException {
        Parent parent = parentMapper.selectByPhone(data.getTel());
        if (CommonUtil.isNotEmpty(parent) && (data.getId() != parent.getId()))
            throw new BizException(400, "电话号码重复，请修改或者联系管理员进行修改");

        //说明手机号没有变或者该手机号没有被使用注册
        if (CommonUtil.isEmpty(parent)) {
            String oldTel = parentMapper.selectById(data.getId());
            List<Student> students = studentMapper.selectByParentId(data.getId());
            for (Student student : students) {
                String parentTels = student.getParentTels();
                String replace;
                if (parentTels != null && parentTels.contains(oldTel)) {
                    replace = parentTels.replace(oldTel, data.getTel());
                } else {
                    replace = parentTels + "#" + data.getTel();
                }
                studentMapper.updateParentTels(student.getId(), replace);
            }
        }
        parentMapper.update(data);


    }

    @Override
    public void createParent(List<Parent> parents, Integer studentId) throws Exception {
        if (CommonUtil.isEmpty(parents))
            throw new ApiException(400, "参数异常");
        if (CommonUtil.isEmpty(parents.get(0).getName()) || CommonUtil.isEmpty(parents.get(0).getTel()))
            throw new ApiException(400, "家长姓名、电话异常");
        try {
            for (Parent parent : parents) {
                if (parent.getHeadURL() == null) {
                    parent.setHeadURL("");
                }
                Parent selectByPhone = parentMapper.selectByPhone(parent.getTel());
                if (CommonUtil.isNotEmpty(selectByPhone))
                    throw new BizException(400, "电话号码已存在!操作失败，不继续做操作");
                parentMapper.addParent(parent);
                parentMapper.addParentAndStudent(parent.getId(), parent.getType().getId(), studentId);
            }
        } catch (Exception e) {
            throw new BizException(400, "添加失败,请检查参数，稍后重试");
        }
    }

    @Override
    public void updateParent(Integer parentId, Integer isDel) {
        if (0 == Integer.valueOf(isDel)) {
            parentMapper.updateParent(parentId, 1);
            //对家长和学生的关系进行解除绑定
            parentMapper.UnbindWithStudent(Integer.valueOf(parentId));
        }
        if (1 == Integer.valueOf(isDel))
            parentMapper.updateParent(Integer.valueOf(parentId), 0);
    }

}
