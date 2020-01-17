package cloud.common.page;

import java.util.*;

/**
 * Description：
 * Author: xw
 * Date: Created in 2019/12/2 14:11
 * Company: 中幼数娱
 * Version: 0.0.1
 * Modified By:
 */
public class PageJson<T> {
    public Page getPage(List<T> list, int current, int pageNo, int pageSize) {
        Page page = new Page();
        if (null != list && list.size() > 0) {
            page.setList(list);
            page.setTotalResult(list.size());
        } else {
            page.setTotalResult(0);
        }
        page.setCurrentPage(current);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.getTotalPage();
        return page;
    }

}
