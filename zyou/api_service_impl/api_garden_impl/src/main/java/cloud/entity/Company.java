package cloud.entity;

import cloud.common.page.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/26 11:39
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Data
public class Company implements Serializable {

    private static final long serialVersionUID = 5460980289357847320L;
    private Long id; //
    private String provinceName;//省
    private String cityName;//市
    private String areaName;//区
    private Long provinceId; // 省id
    private Long cityId; // 市id
    private Long areaId; // 区id
    private String number; // 机构编号
    private String name; // 机构名称
    private String manager; // 负责人
    private String tel; // 联系电话
    private String address; // 详细地址
    private String type; //机构类型1 控股园 2 全资园 3 加盟园（分公司） 4 加盟试用园 6捐赠园 7 内部测试员 8 其他
    private Integer isDel; // 显示标记 0：未删除 1：已删除
    private Date createDate; // 创建时间
    private Date updateDate; // 更新时间
    private String reserved1; // 保留字段1
    private String reserved2; // 保留字段2
    private Integer faculty;//幼儿园人数范围
    private Page page;

}
