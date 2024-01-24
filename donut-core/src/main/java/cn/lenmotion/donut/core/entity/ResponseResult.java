package cn.lenmotion.donut.core.entity;

import cn.lenmotion.donut.core.enums.ResponseCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author lenmotion
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "响应结果")
public class ResponseResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 47834854132961799L;

    @Schema(description = "响应码")
    private Integer code;

    @Schema(description = "响应消息")
    private String msg;

    @Schema(description = "响应数据")
    private T result;

    public static <T> ResponseResult<T> custom(Integer code, String message, T result) {
        return new ResponseResult<>(code, message, result);
    }

    public static <T> ResponseResult<T> custom(ResponseCodeEnum responseCodeEnum, T result) {
        return custom(responseCodeEnum.getCode(), responseCodeEnum.getRemark(), result);
    }

    public static <T> ResponseResult<T> custom(ResponseCodeEnum responseCodeEnum) {
        return custom(responseCodeEnum, null);
    }

    public static <T> ResponseResult<T> success() {
        return custom(ResponseCodeEnum.SUCCESS, null);
    }

    public static <T> ResponseResult<T> success(String msg) {
        return custom(ResponseCodeEnum.SUCCESS.getCode(), msg, null);
    }

    public static <T> ResponseResult<T> success(T result) {
        return custom(ResponseCodeEnum.SUCCESS, result);
    }

    public static <T> ResponseResult<T> success(String message, T data) {
        return custom(ResponseCodeEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> ResponseResult<T> failed() {
        return custom(ResponseCodeEnum.FAILED, null);
    }

    public static <T> ResponseResult<T> failed(String message) {
        return custom(ResponseCodeEnum.FAILED.getCode(), message, null);
    }

    public static <T> ResponseResult<T> failed(T result) {
        return custom(ResponseCodeEnum.FAILED, result);
    }

    public static <T> ResponseResult<T> failed(String message, T result) {
        return custom(ResponseCodeEnum.FAILED.getCode(), message, result);
    }

    public static <T> ResponseResult<T> unLogin() {
        return custom(ResponseCodeEnum.UN_LOGIN);
    }

}
