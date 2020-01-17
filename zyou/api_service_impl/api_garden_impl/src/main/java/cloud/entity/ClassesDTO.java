package cloud.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/12/17 14:31
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Data
@ApiModel("班级DTO")
public class ClassesDTO {


    @ApiModelProperty("班级id")
    private String id;
    @ApiModelProperty("班级名称")
    private String name;
    @ApiModelProperty(" 1.普通班2.特色班")
    private Integer type;
    @ApiModelProperty(" 0.毕业 1.初班 2小班 3中班 4大班 6学前班")
    private Integer grade;
    @ApiModelProperty(" 0:没毕业 1：毕业")
    private Integer graduated;
    @ApiModelProperty("机构名称")
    private String companyName;
    @ApiModelProperty("机构Id")
    private Long companyId;
}
