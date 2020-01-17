package cloud.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("家长信息表，查询家长信息使用")
public class ParentInfo implements Serializable {
    @ApiModelProperty(value = "家长ID，做修改的时候填写", allowEmptyValue = true)
    private Long id;   //家长ID
    @ApiModelProperty("家长姓名，修改、查询的时候使用")
    private String name;    //家长姓名
    @ApiModelProperty("家长电话，修改、查询的时候使用")
    private String tel; //家长联系电话
    @ApiModelProperty("家长性别，做修改时使用")
    private Integer sex;   //家长性别
    @ApiModelProperty("家长工作单位，做修改时使用")
    private String workUnit;    //家长工作单位
    @ApiModelProperty("家长家庭住址,做修改时使用")
    private String address; //家庭住址
    @ApiModelProperty("家长头像地址，做修改时使用")
    private String headURL; //头像地址
}
