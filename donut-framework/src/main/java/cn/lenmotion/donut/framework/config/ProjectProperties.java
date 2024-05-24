package cn.lenmotion.donut.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lenmotion
 */
@Data
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本号
     */
    private String version;

    /**
     * 登陆信息加密公钥
     */
    private String rsaPublicKey;

    /**
     * 登陆信息加密私钥
     */
    private String rsaPrivateKey;

    /**
     * 验证码过期时间
     */
    private Long captchaExpire;

    /**
     * 临时目录，用于存放临时文件，之后会被删除，以/结尾
     */
    private String templatePath;

    /**
     * 文件扩展名
     */
    private List<String> fileExt;

    /**
     * 忽略需要设置租户的表
     */
    private List<String> tenantIgnoreTables;

}
