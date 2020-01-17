package cloud.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Date:2018-4-26 13:22:24
 *
 * @author rono
 */
@Data
@ApiModel("前端接收老师参数使用的类")
public class TeacherInfo implements Serializable {

    @ApiModelProperty("用户ID，做修改时需要添加")
    private Long id; // 用户id
    @ApiModelProperty("老师的姓名，做修改、查询时需要添加")
    private String name; // 姓名
    @ApiModelProperty("老师的性别，做修改时需要添加")
    private Integer sex; // 性别
    @ApiModelProperty("老师的联系电话，做修改、查询时需要添加")
    private String tel; // 联系电话
    @ApiModelProperty("老师的家庭住址，做修改时需要添加")
    private String address; // 住址
    @ApiModelProperty("老师的头像地址，做修改时需要添加")
    private String headURL; // 头像URL
    @ApiModelProperty("职位情况，6：园长，7：副园长，1,2,3,12,13：为老师")
    private String types;  //在当前学校任职的信息
    @ApiModelProperty("机构ID，做查询时使用")
    private Long companyId; //机构ID

    //查询使用的省市区
    @ApiModelProperty("省份的ID,做查询时使用")
    private Long provinceId; //
    @ApiModelProperty("市的ID,做查询时使用")
    private Long cityId; //
    @ApiModelProperty("区域的ID,做查询时使用")
    private Long areaId; //


}