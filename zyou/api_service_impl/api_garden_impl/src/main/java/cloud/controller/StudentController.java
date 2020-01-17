package cloud.controller;

import cloud.common.CommonUtil;
import cloud.common.Response.ResultViewModel;
import cloud.common.Response.ResultViewModelUtil;
import cloud.common.exception.BizException;
import cloud.common.page.Page;
import cloud.common.page.PageJson;
import cloud.entity.Student;
import cloud.entity.StudentDTO;
import cloud.entity.StudentImpDTO;
import cloud.service.StudentService;
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
 * Date: Created in 2019/11/29 9:27
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Api(tags = "admin_学生接口", description = "提供学生相关的后台接口")
@EnableSwagger2Doc //生成Swagger2Doc
@RestController
@RequestMapping("student")
public class StudentController {

    private final Logger log = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;


    @ApiOperation(value = "通过条件查询学生信息数据", notes = "通过条件查询学生信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @GetMapping("/getStudentId")
    public ResultViewModel<Map> getStudentId(@ApiParam(value = "学生id") @RequestParam("id") Long id) {
        log.info("学生请求id参数: " + id);
        if (CommonUtil.isEmpty(id)) {
            return ResultViewModelUtil.errorParam();
        }
        try {
            Student student = studentService.selectById(id);
            Map<String, Object> map = new HashMap<>();
            map.put("object", student);
            return ResultViewModelUtil.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "查询学生信息数据", notes = "查询学生信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PostMapping("/getStudent")
    public ResultViewModel<Map> getStudent(@ApiParam(value = "当前页") @RequestParam("currentPage") Integer currentPage,
                                           @ApiParam(value = "页数") @RequestParam("pageNo") Integer pageNo,
                                           @ApiParam(value = "页面展示大小") @RequestParam("pageSize") Integer pageSize,
                                           @RequestBody StudentDTO studentDTO) {
        if (CommonUtil.isEmpty(currentPage) || CommonUtil.isEmpty(pageNo) || CommonUtil.isEmpty(pageSize)) {
            return ResultViewModelUtil.errorParam();
        }
        try {
            PageJson pageJson = new PageJson();
            log.info("查询学生信息接口：" + studentDTO);
            Student student = new Student();
            BeanUtils.copyProperties(studentDTO, student);
            List<Student> studentList = studentService.selectByProperty(student);
            if (studentList == null || studentList.size() < 1) {
                return ResultViewModelUtil.success_No_Data();
            }
            Page page = pageJson.getPage(studentList, currentPage, pageNo, pageSize);
            return ResultViewModelUtil.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "添加学生信息数据", notes = "添加学生信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PostMapping("/addStudent")
    public ResultViewModel<Map> addStudent(@RequestBody StudentDTO studentDTO) {
        try {
            log.info("添加学生接口：" + studentDTO);
            if (CommonUtil.isEmpty(studentDTO) || CommonUtil.isEmpty(studentDTO.getName())) {
                return ResultViewModelUtil.errorParam();
            }
            Student student = new Student();
            BeanUtils.copyProperties(studentDTO, student);
            studentService.insert(student);
            return ResultViewModelUtil.success();
        } catch (BizException e) {
            e.printStackTrace();
            return ResultViewModelUtil.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "修改学生信息数据", notes = "修改学生信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PutMapping("/updateStudent")
    public ResultViewModel<Map> updateStudent(StudentDTO studentDTO) {
        try {
            log.info("修改学生信息接口：" + studentDTO);
            if (CommonUtil.isEmpty(studentDTO) || CommonUtil.isEmpty(studentDTO.getId())) {
                return ResultViewModelUtil.errorParam();
            }
            Student student = new Student();
            BeanUtils.copyProperties(studentDTO, student);
            studentService.update(student);
            return ResultViewModelUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "删除学生信息数据", notes = "删除学生信息数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @DeleteMapping("/deleteStudent")
    public ResultViewModel<Map> deleteStudent(@ApiParam(value = "学生id") @RequestParam("id") Long id) {
        try {
            log.info("删除学生信息接口：" + id);
            if (CommonUtil.isEmpty(id)) {
                return ResultViewModelUtil.errorParam();
            }
            studentService.delByIds(id);
            return ResultViewModelUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "批量转班", notes = "批量转班")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PutMapping("/updateStudentClass")
    public ResultViewModel<Map> updateStudentClass(StudentDTO studentDTO) {
        try {
            log.info("updateStudentClass批量转班：" + studentDTO);
            if (CommonUtil.isEmpty(studentDTO) || CommonUtil.isEmpty(studentDTO.getStuId()) || CommonUtil.isEmpty(studentDTO.getClassId())) {
                return ResultViewModelUtil.error(400, "参数错误");
            }
            Student student = new Student();
            BeanUtils.copyProperties(studentDTO, student);
            studentService.updateStudentClass(student);
            return ResultViewModelUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "2 恢复   4毕业", notes = "2 恢复   4毕业")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PutMapping("/updateStudentState")
    public ResultViewModel<Map> updateStudentState(@ApiParam(value = "2 恢复   4毕业") @RequestParam("state") Integer state,
                                                   @ApiParam(value = "批量操作学生Id 逗号隔开") @RequestParam("stuId") String stuId) {
        try {
            log.info("updateStudentState 2恢复4毕业：" + stuId);
            if (CommonUtil.isEmpty(state) || CommonUtil.isEmpty(stuId)) {
                return ResultViewModelUtil.errorParam();
            }
            studentService.updateStudentState(state, stuId);
            return ResultViewModelUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultViewModelUtil.error();
        }
    }

    @ApiOperation(value = "学生导入", notes = "学生导入")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "参数异常")
            , @ApiResponse(code = 201, message = "成功，但没有数据返回"), @ApiResponse(code = 500, message = "应用异常")})
    @PostMapping("/importStudent")
    public ResultViewModel<Map> importStudent(@RequestBody List<StudentImpDTO> studentImpDTO) {

        log.info("importStudent 学生导入：" + studentImpDTO);
        if (CommonUtil.isEmpty(studentImpDTO)) {
            return ResultViewModelUtil.errorParam();
        }
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < studentImpDTO.size(); i++) {
            try {
                studentService.importStudent(studentImpDTO.get(i));
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
            studentService.batchInsert(studentImpDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultViewModelUtil.success();
    }

}
