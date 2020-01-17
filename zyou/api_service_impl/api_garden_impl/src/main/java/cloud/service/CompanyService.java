package cloud.service;

import cloud.common.exception.BizException;
import cloud.entity.Company;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/26 12:00
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
public interface CompanyService {

    @RequestMapping("/selectById")
    public Company selectById(Long id);

    @RequestMapping("/selectByProperty")
    public List<Company> selectByProperty(Company company);

    @RequestMapping("/insertCompany")
    public void insertCompany(Company company);

    @RequestMapping("/updateCompany")
    public void updateCompany(Company company);

    @RequestMapping("/delectCompany")
    public void delectCompany(Long id) throws BizException;
}
