package cloud.entity;

import cloud.common.page.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/11/28 16:05
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Data
public class Student implements Serializable {

    private static final long serialVersionUID = 3892658044240037196L;
    private Long id; // 学生id
    private List<Long> ids;//家长集合id（批量添加用）
    private Long provinceId; //
    private Long cityId; //
    private Long areaId; //
    private String number; // 编号
    private Long classId;
    private String name; // 姓名
    private Integer sex; // 性别 0.男 1.女
    private Integer age; // 年龄
    private String headURL; // 头像URL
    private String birthday; // 生日
    private String address; // 住址
    private Integer state; // 状态 1. 待分班 2.已分班 3.离园 4.毕业
    private Date departureDate; // 离园时间
    private Date entryDate;//入园时间
    private String parentTels;//家长手机号 集合#分开
    private String stuId;//批量操作学生
    private Integer isDel; //
    private Date createDate; //
    private Date updateDate; //
    private String reserved1; //
    private String reserved2; //

    private List<String> parentName; //家长名

    private List<String> parentPhoneNum; //家长手机号

    private List<String> relationship; //关系
    private Classes classes;
    private Page page;
}
