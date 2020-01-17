package cloud.common.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 2 * @Author:
 * 3 * @Date: 2019/12/4 9:26
 * 4
 */
@Data
public class MyPage<T> implements Serializable {
    private Integer totalCount; //总记录条数
    private Integer totalPage; //总页数
    private Integer correctPage; //当前页
    private Integer startIndex; //需要查询的起始位置
    private Integer pageSize; //分页大小
    private Integer nextPage; //下一页
    private Integer lastPage;//上一页
    private List<T> list;//需要显示的信息


    public MyPage() {

    }

    public MyPage(Integer correctPage, Integer pageSize) {
        this.correctPage = correctPage;
        this.pageSize = pageSize;
    }

    public MyPage(Integer totalCount, Integer correctPage, Integer pageSize) {
        this.totalCount = totalCount;
        this.correctPage = correctPage;
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        if (totalCount % pageSize != 0 && totalCount / pageSize > 1) {
            totalPage = totalCount / pageSize + 1;
            nextPage = correctPage + 1;
            lastPage = correctPage - 1;
        } else {
            totalPage = totalCount / pageSize;
            nextPage = correctPage;
            lastPage = correctPage - 1;
        }
        return totalPage;
    }

    public Integer getStartIndex() {
        return (correctPage - 1) * pageSize;
    }
}
