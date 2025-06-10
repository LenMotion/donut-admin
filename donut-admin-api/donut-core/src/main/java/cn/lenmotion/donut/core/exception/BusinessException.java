package cn.lenmotion.donut.core.exception;

import cn.lenmotion.donut.core.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {
    private ResponseCodeEnum responseCodeEnum;

    public BusinessException() {}

    public BusinessException(String message) {
        super(message);
        this.responseCodeEnum = ResponseCodeEnum.FAILED;
    }

    public BusinessException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getRemark());
        this.responseCodeEnum = responseCodeEnum;
    }

    public BusinessException(ResponseCodeEnum responseCodeEnum, String message) {
        super(message);
        this.responseCodeEnum = responseCodeEnum;
    }

}
