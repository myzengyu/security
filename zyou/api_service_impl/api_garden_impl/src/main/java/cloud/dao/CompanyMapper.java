package cloud.dao;


import cloud.entity.Company;

import java.util.List;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/26 13:10
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
public interface CompanyMapper {

    List<Company> selectByProperty(Company company);

    Company selectById(Long id);

    void deleteById(Long id);

    void update(Company company);

    void insert(Company company);
}
