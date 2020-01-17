package cloud.common.globalExceptionHandler;

import cloud.common.Response.ResultViewModel;
import cloud.common.Response.ResultViewModelUtil;
import cloud.common.exception.ApiException;
import cloud.common.exception.BizException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
//如果返回的为json数据或其他对象，添加该注解
@ResponseBody
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResultViewModel NullPointerException(Exception ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("exception", "空指针异常，参数为空，请检查参数是否已输入");
        map.put("msg", ex.getMessage());
        map.put("timeStamp", new Date());
        return ResultViewModelUtil.error(map);
    }

    @ExceptionHandler(BizException.class)
    public ResultViewModel BizException(BizException ex) {
        return ResultViewModelUtil.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResultViewModel ApiException(ApiException ex) {
        return ResultViewModelUtil.error(ex.getErrorCode(), ex.getErrorMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResultViewModel UnKnowException(Exception ex) {
        return ResultViewModelUtil.error(500, ex.getMessage());
    }
}
