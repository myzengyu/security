package cloud.common.exception;

import io.swagger.models.auth.In;

/**
 * 自定义异常处理类
 *
 * @author rono
 */
public class ApiException extends Exception {
    private static final long serialVersionUID = 1L;

    private Integer errorCode;

    private String errorMsg;

    // ***默认***
    public ApiException() {
        super();
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    // ***自定义***
    public ApiException(Integer errorCode, String errorMsg) {
        super(errorCode + ":" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}