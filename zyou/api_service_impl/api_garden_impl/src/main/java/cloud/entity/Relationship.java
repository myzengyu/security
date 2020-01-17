package cloud.entity;

import lombok.Data;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/12/4 10:58
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
@Data
public class Relationship {
    private Long parentId;//家长id
    private Long studentId;//学生Id
    private Integer parentType;//关系
}
