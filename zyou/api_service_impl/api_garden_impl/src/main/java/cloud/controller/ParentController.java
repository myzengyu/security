package cloud.controller;

import cloud.common.CommonUtil;
import cloud.common.Response.ResultViewModel;
import cloud.common.Response.ResultViewModelUtil;
import cloud.common.exception.BizException;
import cloud.common.page.MyPage;
import cloud.dao.TypeMapper;
import cloud.entity.Parent;
import cloud.entity.ParentInfo;
import cloud.entity.Type;
import cloud.service.ParentService;
import com.spring4all.swagger.EnableSwagger2Doc;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@ApiResponses({
        @ApiResponse(code = 201, message = "添加成功或者未查询到数据", response = ParentController.class),
        @ApiResponse(code = 400, message = "参数为空或者不合法", response = ParentController.class),
        @ApiResponse(code = 401, message = "未授权、验证失败", response = ParentController.class),
        @ApiResponse(code = 404, message = "找不到资源", response = ParentController.class),
        @ApiResponse(code = 409, message = "业务逻辑异常", response = ParentController.class),
        @ApiResponse(code = 500, message = "服务器内部错误", response = ParentController.class),
        @ApiResponse(code = 503, message = "Hystrix异常", response = ParentController.class)
})
@Api(tags = "admin_家长接口", description = "提供家长相关的后台接口")
@EnableSwagger2Doc //生成Swagger2Doc
@RestController
@RequestMapping("/parent")
public class ParentController {

    @Autowired
    private ParentService parentService;
    @Autowired
    private TypeMapper typeMapper;
    private Logger logger = LoggerFactory.getLogger(ParentController.class);

    @ApiOperation(value = "获取家长的分类信息", notes = "获取家长的分类信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok")})
    @GetMapping("/getTypeList")
    public ResultViewModel getTypeList() {
        List<Type> types = typeMapper.selectAllParent();
        return ResultViewModelUtil.success(types);
    }

    /*
     *家长管理   查看家长信息
     */
    @PostMapping("/getParentPage")
    @ApiOperation(value = "获取家长分页信息", notes = "获取家长个人信息")
    public ResultViewModel getParentPage(@RequestBody ParentInfo parentInfo,
                                         @ApiParam(name = "correctPage", value = "当前页", defaultValue = "1")
                                         @RequestParam(value = "correctPage", defaultValue = "1") Integer correctPage,
                                         @ApiParam(name = "pageSize", value = "分页大小", defaultValue = "10")
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        if (CommonUtil.isEmpty(parentInfo))
            return ResultViewModelUtil.error(400, "参数异常");
        if (CommonUtil.isEmpty(correctPage, pageSize))
            return ResultViewModelUtil.error(400, "分页信息不完善");
        Parent parent = new Parent();
        BeanUtils.copyProperties(parentInfo, parent);
        MyPage page = new MyPage(correctPage, pageSize);
        parent.setPage(page);
        List<Parent> teacherPage = parentService.getParentPage(parent);
        if (CommonUtil.isEmpty(teacherPage))
            return ResultViewModelUtil.success_No_Data();
        MyPage myPage = new MyPage(teacherPage.get(0).getPage().getTotalCount(), correctPage, pageSize);
        teacherPage.stream().forEachOrdered(teacher1 -> teacher1.setPage(null));
        myPage.setList(teacherPage);
        return ResultViewModelUtil.success(myPage);
    }


    @ApiOperation(value = "修改家长信息", notes = "修改家长信息")
    @PostMapping("/updateParent")
    public ResultViewModel<Map> updateParent(@RequestBody ParentInfo parentInfo) throws BizException {
        if (CommonUtil.isEmpty(parentInfo, parentInfo.getId()))
            return ResultViewModelUtil.error(400, "参数异常");
        Parent data = parentService.searchById(parentInfo.getId());
        if (CommonUtil.isEmpty(data))
            return ResultViewModelUtil.error(201, "该家长信息不存在！");
        Parent parent = new Parent();
        BeanUtils.copyProperties(parentInfo, parent);
        parentService.updateParent(parent);
        return ResultViewModelUtil.success();


    }

    @ApiOperation(value = "删除家长信息", notes = "删除家长信息")
    @PostMapping("/delParent")
    public ResultViewModel delParent(@ApiParam(name = "parentId", value = "要删除的父母的id")
                                     @RequestParam(value = "parentId") Integer parentId,
                                     @ApiParam(name = "isDel", value = "当前父母的状态", defaultValue = "0")
                                     @RequestParam(value = "isDel") Integer isDel) {
        if (CommonUtil.isEmpty(parentId, isDel))
            return ResultViewModelUtil.error(400, "参数异常");
        parentService.updateParent(parentId, isDel);
        return ResultViewModelUtil.success();
    }

//    @RequestMapping("/addParent")
//    public ResultViewModel addParent(@RequestBody List<Parent> parent) throws Exception {
//        parentService.createParent(parent,2);
//        return ResultViewModelUtil.success();
//    }


    @ApiOperation(value = "批量导出家长信息", notes = "批量导出家长信息")
    @PostMapping("/parentExport")
    public ResultViewModel parentExport(@RequestBody ParentInfo parentInfo) {
        if (CommonUtil.isEmpty(parentInfo))
            return ResultViewModelUtil.errorParam();
        Parent parent = new Parent();
        BeanUtils.copyProperties(parentInfo, parent);
        List<Parent> parentPage = parentService.getParentPage(parent);
        if (CommonUtil.isEmpty(parentPage))
            return ResultViewModelUtil.success_No_Data();
        parentPage.stream().forEachOrdered(teacher1 -> teacher1.setPage(null));
        return ResultViewModelUtil.success(parentPage);
    }
}
