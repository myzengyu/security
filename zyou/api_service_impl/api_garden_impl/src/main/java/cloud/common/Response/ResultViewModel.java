package cloud.common.Response;


import java.io.Serializable;

/**
 * Description：
 * Author: xueyuan
 * Date: Created in 2019/8/15 16:04
 * Company: 中幼数娱
 * Copyright: Copyright (c) 2017
 * Version: 0.0.1
 * Modified By:
 */

public class ResultViewModel<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String msg;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
