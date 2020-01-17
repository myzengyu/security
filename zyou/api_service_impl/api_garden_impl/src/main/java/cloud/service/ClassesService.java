package cloud.service;

import cloud.common.exception.BizException;
import cloud.entity.Classes;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/28 9:20
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
public interface ClassesService {

    @RequestMapping("/selectById")
    public Classes selectById(Long id);

    @RequestMapping("/selectCompanyIdByClass")
    public List<Classes> selectCompanyIdByClass(Long companyId, String className);

    @RequestMapping("/selectByProperty")
    public List<Classes> selectByProperty(Classes classes);

    @RequestMapping("/insertCompany")
    public void insertClasses(Classes classes);

    @RequestMapping("/updateCompany")
    public void updateClasses(Classes classes) throws BizException;

    @RequestMapping("/delectCompany")
    public void delectClasses(Long id) throws BizException;

    @RequestMapping("/selectComByClass")
    public List<Classes> selectComByClass(Long cid);

    @RequestMapping("/updateGraduated")
    public void updateGraduated(Long id);

    @RequestMapping("importClasses")
    public void importClasses(Classes classes) throws BizException;

    public void batchInsert(List<Classes> classesList);

}
