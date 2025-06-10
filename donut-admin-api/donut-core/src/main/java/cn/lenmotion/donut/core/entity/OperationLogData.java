package cn.lenmotion.donut.core.entity;

import lombok.Data;

/**
 * @author lenmotion
 */
@Data
public class OperationLogData {
    /**
     * 操作人员
     */
    private Long userId;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求URL
     */
    private String url;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    private String status;

    /**
     * 错误消息
     */
    private String errorMsg;

}
