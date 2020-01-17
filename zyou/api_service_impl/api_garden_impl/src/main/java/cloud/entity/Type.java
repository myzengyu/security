package cloud.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Data
public class Type implements Serializable {

    private Long id; //类型ID
    private Long type;  //类型分类
    private String typeName;    //该类型的中文名字
    private String typeEn;  //该类型的英文名字
    private Long isDel;   //显示标识
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;    //修改时间
    private String reserved1;
    private String reserved2;

    public Type() {

    }

    public Type(Long id) {
        this.id = id;
        if (6L == (id))
            this.setType(1L);
        if (7L == (id))
            this.setType(1L);
        if (1L == (id))
            this.setType(2L);
        if (2L == (id))
            this.setType(2L);
        if (3L == (id))
            this.setType(2L);

    }
}
