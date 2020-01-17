package cloud.common.page;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/12/2 11:03
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
public class Page<T> {
    private int totalPage;        //总页数
    private int totalResult;    //总记录数
    private int currentPage;    //当前页
    private int pageNo = 0; // 页数
    private int pageSize = 10; //页面展示大小
    private List<T> list = new ArrayList<>();

    public Page() {
    }

    public Page(int currentPage, int pageNo, int pageSize) {
        this.currentPage = currentPage;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        if (totalResult != 0) {
            if (totalResult % pageSize == 0) {
                totalPage = totalResult / pageSize;
            } else {
                totalPage = totalResult / pageSize + 1;
            }
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageNo() {
        return pageNo * pageSize;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
