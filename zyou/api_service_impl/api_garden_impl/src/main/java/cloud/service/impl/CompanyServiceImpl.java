package cloud.service.impl;

import cloud.common.exception.BizException;
import cloud.dao.ClassesMapper;
import cloud.dao.CompanyMapper;
import cloud.entity.Classes;
import cloud.entity.Company;
import cloud.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/10/22 9:57
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private ClassesMapper classesMapper;

    @Override
    public Company selectById(Long id) {
        return companyMapper.selectById(id);
    }

    @Override
    public List<Company> selectByProperty(Company company) {
        return companyMapper.selectByProperty(company);
    }

    @Override
    public void insertCompany(Company company) {
        companyMapper.insert(company);
    }

    @Override
    public void updateCompany(Company company) {
        companyMapper.update(company);
    }

    @Override
    public void delectCompany(Long id) throws BizException {
        List<Classes> classesList = classesMapper.selectCompanyIdByClass(id);
        if (classesList != null && classesList.size() > 0) {
            throw new BizException(400, "当前学校存在班级");
        }
        companyMapper.deleteById(id);
    }
}
