package cloud.controller;

import cloud.common.CommonUtil;
import cloud.common.Response.ResultViewModel;
import cloud.common.Response.ResultViewModelUtil;
import cloud.common.exception.BizException;
import cloud.common.page.MyPage;
import cloud.dao.TypeMapper;
import cloud.entity.Company;
import cloud.entity.Teacher;
import cloud.entity.TeacherInfo;
import cloud.entity.Type;
import cloud.service.TeacherService;
import com.spring4all.swagger.EnableSwagger2Doc;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/28 10:07
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */

@ApiResponses({
        @ApiResponse(code = 201, message = "添加成功或者未查询到数据", response = ParentController.class),
        @ApiResponse(code = 400, message = "参数为空或者不合法", response = ParentController.class),
        @ApiResponse(code = 401, message = "未授权、验证失败", response = ParentController.class),
        @ApiResponse(code = 404, message = "找不到资源", response = ParentController.class),
        @ApiResponse(code = 409, message = "业务逻辑异常", response = ParentController.class),
        @ApiResponse(code = 500, message = "服务器内部错误", response = ParentController.class),
        @ApiResponse(code = 503, message = "Hystrix异常", response = ParentController.class)
})
@Api(tags = "admin_老师接口", description = "提供老师相关的后台接口")
@EnableSwagger2Doc //生成Swagger2Doc
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final Logger logger = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TypeMapper typeMapper;

    @PostMapping("/getAreaList")
    @ApiOperation("获取地区下拉列表详情")
    public ResultViewModel getAreaList(@ApiParam(name = "areaId", value = "地区Id", defaultValue = "1") @RequestParam(value = "areaId", defaultValue = "1") Integer areaId) {
        List<Map<Integer, String>> areaList = teacherService.getAreaList(areaId);
        if (CommonUtil.isEmpty(areaList))
            return ResultViewModelUtil.success_No_Data();
        return ResultViewModelUtil.success(areaList);
    }


    @ApiOperation(value = "获取老师的分类信息", notes = "获取老师的分类信息")
    @PostMapping("/getTypeList")
    public ResultViewModel getTypeList() {
        List<Type> types = typeMapper.selectAllTeacher();
        return ResultViewModelUtil.success(types);
    }

    @ApiOperation(value = "获取老师分页信息", notes = "获取老师分页信息")
    @PostMapping("/getTeacherPage")
    public ResultViewModel getTeacherPage(@RequestBody TeacherInfo teacherInfo,
                                          @ApiParam(name = "correctPage", value = "当前页", defaultValue = "1")
                                          @RequestParam(value = "correctPage", defaultValue = "1") Integer correctPage,
                                          @ApiParam(name = "pageSize", value = "分页大小", defaultValue = "10")
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        if (CommonUtil.isEmpty(teacherInfo))
            return ResultViewModelUtil.error(400, "参数异常");
        if (CommonUtil.isEmpty(correctPage, pageSize))
            return ResultViewModelUtil.error(400, "分页信息不完善");
        try {

            Teacher teacher = getTeacher(teacherInfo);
            teacher.setPage(new MyPage(correctPage, pageSize));
            List<Teacher> teacherPage = teacherService.getTeacherPage(teacher);
            //为返回数据
            if (CommonUtil.isEmpty(teacherPage))
                return ResultViewModelUtil.success_No_Data();
            MyPage myPage = new MyPage(teacherPage.get(0).getPage().getTotalCount(), correctPage, pageSize);
            teacherPage.stream().forEachOrdered(teacher1 -> teacher1.setPage(null));
            myPage.setList(teacherPage);
            return ResultViewModelUtil.success(myPage);
        } catch (Exception e) {
            return ResultViewModelUtil.error();
        }
    }

    /**
     * 添加老师信息
     *
     * @param teacherInfo
     * @return
     */
    @ApiOperation(value = "添加老师信息", notes = "添加老师信息")
    @PostMapping("/addTeacher")
    public ResultViewModel addTeacher(@RequestBody TeacherInfo teacherInfo) throws BizException {
        if (CommonUtil.isEmpty(teacherInfo))
            return ResultViewModelUtil.error(400, "参数异常");
        if (CommonUtil.isEmpty(teacherInfo.getId(), teacherInfo.getName(), teacherInfo.getTel(), teacherInfo.getCompanyId()))
            return ResultViewModelUtil.error(400, "参数异常");
        List<Teacher> teacherByTel = teacherService.findTeacherByTel(teacherInfo.getTel());
        if (CommonUtil.isEmpty(teacherByTel)) {
            //执行添加老师的操作
            Teacher teacher = getTeacher(teacherInfo);
            teacherService.addTeacher(teacher);
            return ResultViewModelUtil.success();
        }
        return ResultViewModelUtil.error(400, "老师手机号已经被注册，请修改或者联系管理员");
    }

    /**
     * 修改老师信息
     *
     * @param teacherInfo
     * @return
     */
    @ApiOperation(value = "修改老师信息", notes = "修改老师信息")
    @PutMapping("/updateTeacher")
    public ResultViewModel updateTeacher(@RequestBody TeacherInfo teacherInfo) throws BizException {
        if (CommonUtil.isEmpty(teacherInfo))
            return ResultViewModelUtil.error(400, "参数异常");
        if (CommonUtil.isEmpty(teacherInfo.getId(), teacherInfo.getName(), teacherInfo.getTel(), teacherInfo.getCompanyId()))
            return ResultViewModelUtil.error(400, "参数异常");
        Teacher teacherById = teacherService.findTeacherById(teacherInfo.getId());
        //当修改的老师的电话和老师原本电话不一致时，做电话唯一查询
        if (!teacherInfo.getTel().equals(teacherById.getTel())) {
            List<Teacher> teacherByTel = teacherService.findTeacherByTel(teacherInfo.getTel());
            if (CommonUtil.isNotEmpty(teacherByTel))
                return ResultViewModelUtil.error(400, "该手机号已经被其他老师注册，请修改或者联系管理员");
        }
        Teacher teacher = getTeacher(teacherInfo);
        teacherService.updateTeacher(teacher, teacherById);
        return ResultViewModelUtil.success();
    }

    /**
     * 对老师进行伪删除
     *
     * @param
     * @return
     */
    @ApiOperation(value = "删除老师信息", notes = "删除老师信息")
    @DeleteMapping("/delTeacher")
    public ResultViewModel delTeacher(@ApiParam(name = "teachId", value = "要删除的老师的id")
                                      @RequestParam(value = "teachId") Integer teachId,
                                      @ApiParam(name = "isDel", value = "当前老师的状态", defaultValue = "0")
                                      @RequestParam(value = "isDel") Integer isDel) {
        if (CommonUtil.isEmpty(teachId, isDel))
            return ResultViewModelUtil.error(400, "参数异常");
        teacherService.updateIsDel(teachId, isDel);
        return ResultViewModelUtil.success();
    }

    @ApiOperation(value = "批量导入老师信息", notes = "批量导入老师信息")
    @PostMapping("/teacherImport")
    public ResultViewModel teacherImport(@RequestBody List<TeacherInfo> teacherInfos) {
        Map<String, Object> map = new HashMap<>();
        if (CommonUtil.isEmpty(teacherInfos))
            return new ResultViewModelUtil().errorParam();
//        Map(String, Object) map = teacherService.teacherImport(teacherInfos);
        Integer count = 0;
        List<Teacher> teachers = teacherInfos.stream().map(teacherInfo -> getTeacher(teacherInfo)).collect(Collectors.toList());
        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            if (CommonUtil.isEmpty(teacher, teacher.getName(), teacher.getTel(), teacher.getCompany(), teacher.getTypes())) {
                count++;
                map.put("第" + (i + 1) + "行老师信息错误或者不完整", teacher);
                continue;
            }
            if (CommonUtil.isNotEmpty(teacherService.findTeacherByTel(teacher.getTel()))) {
                map.put("第" + (i + 1) + "行老师电话号码重复,请修改", teacher);
                count++;
                continue;
            } else {
                try {
                    teacherService.addTeacher(teacher);
                } catch (BizException e) {
                    //报异常说明未添加成功
                    count++;
                    map.put("第" + (i + 1) + "行" + e.getMessage(), teacher);
                }
            }
        }
        map.put("成功添加老师" + (teachers.size() - count) + "人", "失败" + count + "人，详情见返回");
        return new ResultViewModelUtil().success(map);

    }

    @ApiOperation(value = "批量导出老师信息", notes = "批量导出老师信息")
    @PostMapping("/teacherExport")
    public ResultViewModel teacherExport(@RequestBody TeacherInfo teacherInfo) {
        if (CommonUtil.isEmpty(teacherInfo))
            return ResultViewModelUtil.error(400, "参数异常");
        Teacher teacher = getTeacher(teacherInfo);
        List<Teacher> teacherPage = teacherService.getTeacherPage(teacher);
        //判断是否有查询到数据
        if (CommonUtil.isEmpty(teacherPage))
            return ResultViewModelUtil.success_No_Data();
        //把page属性设置为null
        teacherPage.stream().forEachOrdered(teacher1 -> teacher1.setPage(null));
        return ResultViewModelUtil.success(teacherPage);
    }

    /**
     * 把前端接受的实体类转化为要使用的使用类
     *
     * @param teacherInfo 前端接收的实体类
     * @return 配合service，mapper要使用的实体类
     */
    public static Teacher getTeacher(TeacherInfo teacherInfo) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherInfo, teacher);
        String types = teacherInfo.getTypes();
        //判断types是否为null，为空，为string（swagger2默认为string），是则不操作
        if (types != null && !"".equals(types) && !"string".equals(types)) {
            List<Type> typeList = Arrays.stream(teacherInfo.getTypes().split(",")).map(m -> new Type(Long.valueOf(m))).collect(Collectors.toList());
            //对types进行解析
            teacher.setTypes(typeList);
        } else {
            teacher.setTypes(null);
        }
        //如果companyId不为空，则设置机构信息
        if (CommonUtil.isNotEmpty(teacherInfo.getCompanyId())) {
            Company company = new Company();
            company.setId(teacherInfo.getCompanyId());
            teacher.setCompany(company);
        }
        return teacher;
    }
}
