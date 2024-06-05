package cn.lenmotion.donut.auth.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.lenmotion.donut.auth.entity.CaptchaVo;
import cn.lenmotion.donut.core.annotation.RateLimiter;
import cn.lenmotion.donut.core.constants.ConfigConstants;
import cn.lenmotion.donut.core.constants.RedisConstants;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.framework.config.ProjectProperties;
import cn.lenmotion.donut.framework.template.JacksonRedisTemplate;
import cn.lenmotion.donut.system.remote.SysConfigRemoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author lenmotion
 */
@Tag(name = "验证码")
@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private final JacksonRedisTemplate redisTemplate;
    private final ProjectProperties projectProperties;
    private final SysConfigRemoteService configRemoteService;

    @Operation(summary = "验证码图片", description = "base64格式")
    @GetMapping("/captchaImage")
    @RateLimiter(count = 5, time = 3, message = "验证码获取过于频繁，请稍后再试！")
    public ResponseResult<CaptchaVo> getCode() {
        CaptchaVo captchaVo = new CaptchaVo();
        // 判断是否开启验证码登陆
        Boolean captchaOnOff = configRemoteService.getConfigBoolValue(ConfigConstants.CAPTCHA_ON_OFF);
        captchaVo.setCaptchaOnOff(captchaOnOff);
        if (!Boolean.TRUE.equals(captchaOnOff)) {
            return ResponseResult.success(captchaVo);
        }
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(90, 40, 4, 7);
        // 保存验证码信息
        String uuid = IdUtil.simpleUUID();
        String verifyKey = RedisConstants.CAPTCHA_CODE_KEY + uuid;
        redisTemplate.opsForValue().set(verifyKey, lineCaptcha.getCode(), projectProperties.getCaptchaExpire(), TimeUnit.SECONDS);

        captchaVo.setUuid(uuid);
        captchaVo.setImg(lineCaptcha.getImageBase64());
        return ResponseResult.success(captchaVo);
    }

}
