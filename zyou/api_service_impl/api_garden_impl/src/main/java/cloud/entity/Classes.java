package cloud.entity;

import cloud.common.page.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/28 9:12
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */

@Data
public class Classes implements Serializable {
    private static final long serialVersionUID = 8914457231603298479L;

    private Long id; // 班级id
    private Long companyId;
    private String name; // 班级名称
    private Integer type; // 班级类型       1.普通班2.特色班
    private Integer isDel; // 显示标记(0
    private Integer grade; //年级
    private Integer graduated;//0:没毕业 1：毕业
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate; // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate; // 更新时间
    private String reserved1; // 保留字段1
    private String reserved2; // 保留字段2

    public Company company;//机构
    private String companyName;//机构名称
    private Page page;

}
