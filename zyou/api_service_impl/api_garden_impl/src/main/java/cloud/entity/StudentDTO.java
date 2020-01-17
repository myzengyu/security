package cloud.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description： 批量添加学生 导入数据
 * Author: xw
 * Date: Created in 2019/12/3 17:52
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Data
@ApiModel("学生DTO")
public class StudentDTO {
    @ApiModelProperty("学生id")
    private Long id; // 学生id
    @ApiModelProperty("家长集合id（批量添加用）")
    private List<Long> ids;//家长集合id（批量添加用）
    @ApiModelProperty("省id")
    private Long provinceId;
    @ApiModelProperty("市id")
    private Long cityId;
    @ApiModelProperty("区id")
    private Long areaId;
    @ApiModelProperty("编号")
    private String number; // 编号
    @ApiModelProperty("班级id")
    private Long classId;
    @ApiModelProperty("姓名")
    private String name; // 姓名
    @ApiModelProperty("0.男 1.女")
    private Integer sex; // 性别 0.男 1.女
    @ApiModelProperty("年龄")
    private Integer age; // 年龄
    @ApiModelProperty("头像URL")
    private String headURL; // 头像URL
    @ApiModelProperty("生日")
    private String birthday; // 生日
    @ApiModelProperty("住址")
    private String address; // 住址
    @ApiModelProperty(" 1. 待分班 2.已分班 3.离园 4.毕业")
    private Integer state; // 状态 1. 待分班 2.已分班 3.离园 4.毕业
    @ApiModelProperty("学生数组id（批量操作学生用）")
    private String stuId;//批量操作学生
    @ApiModelProperty("家长名集合")
    private List<String> parentName; //家长名
    @ApiModelProperty("家长手机号集合")
    private List<String> parentPhoneNum; //家长手机号
    @ApiModelProperty("关系集合")
    private List<String> relationship; //关系
}
