package cloud.controller;

import cloud.common.CommonUtil;
import cloud.common.Response.ResultViewModel;
import cloud.common.Response.ResultViewModelUtil;
import cloud.common.exception.BizException;
import cloud.common.page.Page;
import cloud.common.page.PageJson;
import cloud.entity.Company;
import cloud.entity.CompanyDTO;
import cloud.service.CompanyService;
import com.spring4all.swagger.EnableSwagger2Doc;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/26 14:08
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Api(tags = "admin_学校接口", description = "提供学校相关的后台接口")
@EnableSwagger2Doc //生成Swagger2Doc
@RestController
@RequestMapping("company")
public class CompanyController {

    private final Logger log = LoggerFactory.getLogger(CompanyController.class);


    @Autowired
    private CompanyService iCompanyService;


    @ApiOperation(value = "通过条件查询学校信息数据", notes = "通过条件查询学校信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @GetMapping("/getCompanyId")
    public ResultViewModel<Map> getCompanyId(@ApiParam(value = "学校id") @RequestParam("id") Long id) {
        log.info("学校请求id参数: " + id);
        if (CommonUtil.isEmpty(id)) {
            return ResultViewModelUtil.errorParam();
        }
        try {
            Company company = iCompanyService.selectById(id);
            if (CommonUtil.isEmpty(company)) {
                return ResultViewModelUtil.success_No_Data();
            }
            Map<String, Object> map = new HashMap<>();
            map.put("object", company);
            return ResultViewModelUtil.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "查询所有学校信息数据", notes = "查询所有学校信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PostMapping("/getCompany")
    public ResultViewModel<Map> getCompany(
            @ApiParam(value = "当前页") @RequestParam("currentPage") Integer currentPage,
            @ApiParam(value = "页数") @RequestParam("pageNo") Integer pageNo,
            @ApiParam(value = "页面展示大小") @RequestParam("pageSize") Integer pageSize,
            @RequestBody CompanyDTO companyDTO) {
        log.info("getCompany学校查询条件: " + companyDTO);
        if (CommonUtil.isEmpty(currentPage) || CommonUtil.isEmpty(pageNo) || CommonUtil.isEmpty(pageSize)) {
            return ResultViewModelUtil.errorParam();
        }
        try {
            PageJson pageJson = new PageJson();
            Company company = new Company();
            BeanUtils.copyProperties(companyDTO, company);
            Page pages = new Page(currentPage, pageNo, pageSize);
            company.setPage(pages);
            List<Company> listCompany = iCompanyService.selectByProperty(company);
            if (listCompany == null || listCompany.size() < 1) {
                return ResultViewModelUtil.success_No_Data();
            }
            Page page = pageJson.getPage(listCompany, currentPage, pageNo, pageSize);
            return ResultViewModelUtil.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "添加学校信息数据", notes = "添加学校信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PostMapping("/addCompany")
    public ResultViewModel<Map> addCompany(@RequestBody CompanyDTO companyDTO) {
        log.info("添加学校请求参数:" + companyDTO);
        try {
            if (CommonUtil.isEmpty(companyDTO) || CommonUtil.isEmpty(companyDTO.getName())) {
                return ResultViewModelUtil.errorParam();
            }
            Company company = new Company();
            BeanUtils.copyProperties(companyDTO, company);
            iCompanyService.insertCompany(company);
            return ResultViewModelUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "修改学校信息数据", notes = "修改学校信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PutMapping("/updateCompany")
    public ResultViewModel<Map> updateCompany(CompanyDTO companyDTO) {
        log.info("修改学校请求参数:" + companyDTO);
        if (CommonUtil.isEmpty(companyDTO) || CommonUtil.isEmpty(companyDTO.getId())) {
            return ResultViewModelUtil.errorParam();
        }
        try {
            Company company = new Company();
            BeanUtils.copyProperties(companyDTO, company);
            iCompanyService.updateCompany(company);
            return ResultViewModelUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "删除学校信息数据", notes = "删除学校信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @DeleteMapping("/deleteCompany")
    public ResultViewModel<Map> deleteCompany(@ApiParam(value = "学校id") @RequestParam("id") Long id) {
        log.info("学校删除接口请求id参数: " + id);
        if (CommonUtil.isEmpty(id)) {
            return ResultViewModelUtil.errorParam();
        }
        try {
            iCompanyService.delectCompany(id);
            return ResultViewModelUtil.success();
        } catch (BizException be) {
            be.printStackTrace();
            return ResultViewModelUtil.error(be.getCode(), be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }
}
