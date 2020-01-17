package cloud.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description：批量添加学生 导入数据
 * Author: xw
 * Date: Created in 2019/12/17 15:21
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Data
@ApiModel("批量添加学生导入数据")
public class StudentImpDTO {
    @ApiModelProperty("学校id")
    private Long companyId; //学校id
    @ApiModelProperty("学校名称")
    private String companyName; //学校名称
    @ApiModelProperty("班级id")
    private Long classId; //班级id
    @ApiModelProperty("班级名")
    private String className; //班级名
    @ApiModelProperty("0.毕业 1.初班 2小班 3中班 4大班 6学前班")
    private Integer grade; //年级
    @ApiModelProperty("家长姓名")
    private List<String> parentName;
    @ApiModelProperty("家长电话号码集合")
    private List<String> parentPhoneNum;
    @ApiModelProperty("关系集合")
    private List<String> relationship;
    @ApiModelProperty("学生名")
    private String stuName;//学生名
    @ApiModelProperty("生日")
    private String birthday;// 生日
    @ApiModelProperty(" 0.男 1.女")
    private Integer sex; // 性别 0.男 1.女
}
