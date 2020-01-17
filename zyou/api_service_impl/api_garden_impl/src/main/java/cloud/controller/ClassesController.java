package cloud.controller;

import cloud.common.CommonUtil;
import cloud.common.Response.ResultViewModel;
import cloud.common.Response.ResultViewModelUtil;
import cloud.common.exception.BizException;
import cloud.common.page.Page;
import cloud.common.page.PageJson;
import cloud.entity.Classes;
import cloud.entity.ClassesDTO;
import cloud.service.ClassesService;
import com.spring4all.swagger.EnableSwagger2Doc;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/28 10:07
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Api(tags = "admin_班级接口", description = "提供班级相关的后台接口")
@EnableSwagger2Doc //生成Swagger2Doc
@RestController
@RequestMapping("/classes")
public class ClassesController {

    private final Logger log = LoggerFactory.getLogger(ClassesController.class);

    @Autowired
    private ClassesService iClassesService;


    @ApiOperation(value = "通过id查询班级信息数据", notes = "通过id查询班级信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @GetMapping("/getClassesId")
    public ResultViewModel<Map> getClassesId(@ApiParam(value = "班级id") @RequestParam("id") Long id) {
        log.info("班级请求id参数: " + id);
        if (CommonUtil.isEmpty(id)) {
            return ResultViewModelUtil.errorParam();
        }
        try {
            Classes classes = iClassesService.selectById(id);
            Map<String, Object> map = new HashMap<>();
            map.put("object", classes);
            return ResultViewModelUtil.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "通过条件查询班级信息数据", notes = "通过条件查询班级信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PostMapping("/getClasses")
    public ResultViewModel<Map> getClasses(
            @ApiParam(value = "当前页") @RequestParam("currentPage") Integer currentPage,
            @ApiParam(value = "页数") @RequestParam("pageNo") Integer pageNo,
            @ApiParam(value = "页面展示大小") @RequestParam("pageSize") Integer pageSize,
            @RequestBody ClassesDTO classesDTO) {
        log.info("班级查询条件: " + classesDTO);
        if (CommonUtil.isEmpty(currentPage) || CommonUtil.isEmpty(pageNo) || CommonUtil.isEmpty(pageSize)) {
            return ResultViewModelUtil.errorParam();
        }
        PageJson pageJson = new PageJson();
        Classes classes = new Classes();
        try {
            BeanUtils.copyProperties(classesDTO, classes);
            Page page = new Page(currentPage, pageNo, pageSize);
            classes.setPage(page);
            List<Classes> classesList = iClassesService.selectByProperty(classes);
            if (classesList == null || classesList.size() < 1) {
                return ResultViewModelUtil.success_No_Data();
            }
            Page pages = pageJson.getPage(classesList, currentPage, pageNo, pageSize);
            return ResultViewModelUtil.success(pages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "添加班级信息", notes = "添加班级信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PostMapping("/addClasses")
    public ResultViewModel<Map> addClasses(@RequestBody ClassesDTO classesDTO) {
        log.info("班级查询条件: " + classesDTO);
        if (CommonUtil.isEmpty(classesDTO) || CommonUtil.isEmpty(classesDTO.getName())) {
            return ResultViewModelUtil.errorParam();
        }
        Classes classes = new Classes();
        BeanUtils.copyProperties(classesDTO, classes);
        try {
            iClassesService.insertClasses(classes);
            return ResultViewModelUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "修改班级信息", notes = "修改班级信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PutMapping("/updateClasses")
    public ResultViewModel<Map> updateClasses(ClassesDTO classesDTO) {
        log.info("updateClasses 班级查询条件: " + classesDTO);
        if (CommonUtil.isEmpty(classesDTO) || CommonUtil.isEmpty(classesDTO.getId())) {
            return ResultViewModelUtil.errorParam();
        }
        Classes classes = new Classes();
        BeanUtils.copyProperties(classesDTO, classes);
        try {
            iClassesService.updateClasses(classes);
            return ResultViewModelUtil.success();
        } catch (BizException e) {
            e.printStackTrace();
            return ResultViewModelUtil.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "删除班级信息数据", notes = "删除班级信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @DeleteMapping("/deleteClasses")
    public ResultViewModel<Map> deleteClasses(@ApiParam(value = "班级id") @RequestParam("id") Long id) {
        log.info("班级删除接口请求id参数: " + id);
        if (CommonUtil.isEmpty(id)) {
            return ResultViewModelUtil.errorParam();
        }
        try {
            iClassesService.delectClasses(id);
            return ResultViewModelUtil.success();
        } catch (BizException be) {
            be.printStackTrace();
            return ResultViewModelUtil.error(be.getCode(), be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "通过学校id查询班级是否重复", notes = "查询班级是否重复")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @GetMapping("/getRepeatClasses")
    public ResultViewModel<Map> getRepeatClasses(@ApiParam(value = "学校id") @RequestParam("companyId") Long companyId,
                                                 @ApiParam(value = "班级名称") @RequestParam("name") String name) {
        log.info("getRepeatClasses 学校id参数: " + companyId + "班级名称：" + name);
        if (CommonUtil.isEmpty(companyId) || CommonUtil.isEmpty(name)) {
            return ResultViewModelUtil.errorParam();
        }
        try {
            List<Classes> classesList = iClassesService.selectCompanyIdByClass(companyId, name);
            Map<String, Object> map = new HashMap<>();
            map.put("repeat", CommonUtil.isNotEmpty(classesList) ? "yes" : "no");
            return ResultViewModelUtil.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "批量导入班级信息", notes = "批量导入班级信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PostMapping("/importClasses")
    public ResultViewModel<Map> importClasses(@RequestBody List<ClassesDTO> classesDTOList) {
        log.info("批量导入班级信息: " + classesDTOList);
        if (CommonUtil.isEmpty(classesDTOList)) {
            return ResultViewModelUtil.errorParam();
        }
        List<Classes> classesList = new ArrayList<Classes>(classesDTOList.size());
        //导入对象Copy 到班级对象
        for (int i = 0; i < classesDTOList.size(); i++) {
            Classes classes = new Classes();
            BeanUtils.copyProperties(classesDTOList.get(i), classes);
            classesList.add(classes);
        }
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < classesList.size(); i++) {
            try {
                iClassesService.importClasses(classesList.get(i));
            } catch (BizException ex) {
                String err = ex.getMessage();
                Map<String, Object> map = new HashMap<String, Object>(2);
                map.put("length", i + 1);
                map.put("message", err);
                mapList.add(map);
                ex.printStackTrace();
            }
        }
        if (CommonUtil.isNotEmpty(mapList) && mapList.size() > 0) {
            return ResultViewModelUtil.errorParam(mapList);
        }
        try {
            iClassesService.batchInsert(classesList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultViewModelUtil.success();
    }


}
