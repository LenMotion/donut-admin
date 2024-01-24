package cn.lenmotion.donut.framework.advice;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.enums.ResponseCodeEnum;
import cn.lenmotion.donut.core.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author lenmotion
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    static final Map<Class<?>, ResponseCodeEnum> EXCEPTION_RESPONSE = new HashMap<>();

    static {
        EXCEPTION_RESPONSE.put(NotLoginException.class, ResponseCodeEnum.UN_LOGIN);
        EXCEPTION_RESPONSE.put(NotPermissionException.class, ResponseCodeEnum.UN_AUTH);
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<Boolean> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                                       HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestUri, e.getMethod());
        return ResponseResult.failed("请求方式错误");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseResult<Boolean> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'" + requestUri + "',发生未知异常.", e);
        return ResponseResult.failed("系统异常，请联系管理员");
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<Boolean> handleException(Exception e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'" + requestUri + "',发生系统异常.", e);
        return ResponseResult.failed("系统异常，请联系管理员");
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Boolean> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        log.warn(errorMsg);
        return ResponseResult.failed(errorMsg);
    }

    /**
     * 如果是BusinessException抛出的异常，直接返回前端就行
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResponseResult<Object> businessException(BusinessException e) {
        log.warn(e.getMessage());
        return ResponseResult.custom(e.getResponseCodeEnum().getCode(), e.getMessage(), null);
    }

    /**
     * SaTokenException 未登录
     */
    @ExceptionHandler(value = SaTokenException.class)
    public ResponseResult<Object> saTokenException(SaTokenException e) {
        log.error(e.getMessage());

        ResponseCodeEnum responseEnum = EXCEPTION_RESPONSE.get(e.getClass());
        if (responseEnum != null) {
            return ResponseResult.custom(responseEnum);
        }

        return ResponseResult.custom(ResponseCodeEnum.UN_LOGIN, e.getMessage());
    }

}
