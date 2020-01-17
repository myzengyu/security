package cloud.entity;

import cloud.common.page.MyPage;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Parent implements Serializable {

    private Long id;   //家长ID
    private List<Long> ids;//家长集合id（批量添加用）
    private String number; //家长编号
    private String name;    //家长姓名
    private Integer sex;   //家长性别
    private String tel; //家长联系电话
    private String workUnit;    //家长工作单位
    private String address; //家庭住址
    private String headURL; //头像地址
    private String birthday;    //家长生日
    private Integer isDel; //显示标识   0标识显示，1标识不显示
    private Date actTime; //激活时间
    private Date createDate;    //创建时间
    private Date updateDate;    //修改时间
    private String reserved1;
    private String reserved2;

    private Type type; //亲子关系
    private List<Student> student;//该亲子的信息
    //查询使用
    private MyPage page;

}
