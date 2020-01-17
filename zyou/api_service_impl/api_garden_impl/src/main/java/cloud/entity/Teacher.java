package cloud.entity;

import cloud.common.page.MyPage;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Date:2018-4-26 13:22:24
 *
 * @author rono
 */
@Data
public class Teacher implements Serializable {


    private Long id; // 用户id
    private String number; // 编号
    private String name; // 姓名
    private Integer isManage; //是不是管理员 0是管理员，1不是管理员
    private Integer sex; // 性别
    private String tel; // 联系电话
    private String address; // 住址
    private String headURL; // 头像URL
    private Date birthday; //
    private Date quitTime; // 离职时间
    private Date entryTime; // 入职时间
    private Integer isDel; // 显示标记(0 表示显示，1表示不显示)
    private Date createDate; // 创建时间
    private Date updateDate; // 更新时间
    private String reserved1; // 保留字段1
    private String reserved2; // 保留字段2

//    //分页上传使用
//    private Long companyId; //幼儿园Id
//    private String companyName; //幼儿园名称
//    private String typeArray; //在幼儿园的就职情况

    // 列表展示及查询用
    private Classes classes;    //当前教课班级信息
    private Company company;    //当前学校信息
    private List<Type> types;  //在当前学校任职的信息

    //查询使用的省市区以及分页
    private MyPage page;
    private Long provinceId; //
    private Long cityId; //
    private Long areaId; //


//
//    //按业务分数据时传
//    private String bizness;
//
//    //家长管理 查看自己的学生
//    private List<Student> students;
//    //家长老师查看用
//    private String custtype;//职位显示
//    private String className;//班级名称
//    private String schoolName;//学校名称
//    private List<Company> companies;
//    private List<Long> companyIds;
//
//    private List<Long> bindCompanyIds;

}