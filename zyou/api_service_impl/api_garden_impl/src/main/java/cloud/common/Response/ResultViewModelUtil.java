package cloud.common.Response;

import java.io.Serializable;
import java.util.Map;

/**
 * Description：
 * Author: xueyuan
 * Date: Created in 2019/8/15 16:05
 * Company: 中幼数娱
 * Copyright: Copyright (c) 2017
 * Version: 0.0.1
 * Modified By:
 */
public class ResultViewModelUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 请求成功方法01
     *
     * @return 视图模型实例
     */
    public static ResultViewModel success(Object object) {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(200);
        resultViewModel.setMsg("处理成功");
        resultViewModel.setData(object);
        return resultViewModel;
    }

    /**
     * 请求成功方法01
     *
     * @return 视图模型实例
     */
    public static ResultViewModel errorParam(Object object) {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(400);
        resultViewModel.setMsg("参数异常(类型无效或者字段为空)");
        resultViewModel.setData(object);
        return resultViewModel;
    }

    /**
     * 请求成功方法02
     *
     * @return 视图模型实例
     */
    public static ResultViewModel success_No_Data() {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(201);
        resultViewModel.setMsg("成功，但没有数据返回");
        return resultViewModel;
    }

    /**
     * 请求成功方法03
     *
     * @return 视图模型实例
     */
    public static ResultViewModel success() {
        return success(null);
    }

    /**
     * 请求错误：参数错误
     *
     * @return 视图模型实例
     */
    public static ResultViewModel errorParam() {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(400);
        resultViewModel.setMsg("参数异常(类型无效或者字段为空)");
        return resultViewModel;
    }

    /**
     * 请求错误：无权限访问
     *
     * @return 视图模型实例
     */
    public static ResultViewModel errorNoAuth() {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(401);
        resultViewModel.setMsg("无权限访问");
        return resultViewModel;
    }

    /**
     * 请求错误：系统异常
     *
     * @return 视图模型实例
     */
    public static ResultViewModel error() {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(500);
        resultViewModel.setMsg("应用异常");
        resultViewModel.setData("系统维护中...");
        return resultViewModel;
    }

    /**
     * 请求错误：因为异常
     *
     * @return 视图模型实例
     */
    public static ResultViewModel error(Integer code, String msg) {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(code);
        resultViewModel.setMsg(msg);
        return resultViewModel;
    }

    public static ResultViewModel error(Object object) {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setData(object);
        return resultViewModel;
    }
}
