package cloud.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/12/17 15:12
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Data
@ApiModel("学校DTO")
public class CompanyDTO {

    @ApiModelProperty("机构id")
    private String id;
    @ApiModelProperty("省")
    private String provinceName;
    @ApiModelProperty("市")
    private String cityName;
    @ApiModelProperty("区")
    private String areaName;
    @ApiModelProperty("幼儿园人数范围")
    private Integer faculty;
    @ApiModelProperty("省id")
    private Long provinceId;
    @ApiModelProperty("市id")
    private Long cityId;
    @ApiModelProperty("区id")
    private Long areaId;
    @ApiModelProperty("机构编号")
    private String number;
    @ApiModelProperty("机构名称")
    private String name;
    @ApiModelProperty("负责人")
    private String manager;
    @ApiModelProperty("联系电话")
    private String tel;
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty("1 控股园 2 全资园 3 加盟园（分公司） 4 加盟试用园 6捐赠园 7 内部测试员 8 其他")
    private String type;
}
