package cloud.common.exception;


import cloud.common.Response.ResultViewModel;

public class BizException extends Exception {
    /**
     *
     */
    private Integer code;

    private static final long serialVersionUID = 3148833208442176697L;

    public BizException() {
        super();
    }


    public BizException(String msg) {
        super(msg);
    }

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public BizException(ResultViewModel resultViewModel) {
        super(resultViewModel.getMsg());
        this.code = resultViewModel.getCode();
    }

    public BizException(ResultViewModel resultViewModel, String msg) {
        super(msg);
        this.code = resultViewModel.getCode();
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getCode() {
        return code;
    }

}