package cn.lenmotion.donut.framework.aspect;

import cn.dev33.satoken.stp.StpUtil;
import cn.lenmotion.donut.core.annotation.RateLimiter;
import cn.lenmotion.donut.core.constants.RedisConstants;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.utils.IpUtils;
import cn.lenmotion.donut.core.utils.JoinPointUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * @author lenmotion
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimiterAspect {

    private final RedissonClient redissonClient;
    private final HttpServletRequest request;

    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) throws Throwable {
        String combineKey = getCombineKey(rateLimiter, point);
        try {
            RRateLimiter rRateLimiter = redissonClient.getRateLimiter(combineKey);
            rRateLimiter.trySetRate(RateType.OVERALL, rateLimiter.count(), rateLimiter.time(), RateIntervalUnit.SECONDS);

            if (!rRateLimiter.tryAcquire(1)) {
                log.info("限制请求'{}', 缓存key'{}'", rateLimiter.count(), combineKey);
                throw new BusinessException(rateLimiter.message());
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("RateLimiterAspect execute error", e);
            throw new BusinessException("服务器限流异常，请稍候再试");
        }
    }

    private String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        String key = switch (rateLimiter.limitType()) {
            case DEFAULT -> "1";
            case IP -> IpUtils.getIpAddr(request);
            case TOKEN -> StpUtil.getTokenValue();
            case PARAMS -> JoinPointUtils.getJoinPointParams(point, request);
        };
        return RedisConstants.RATE_LIMIT_KEY + request.getRequestURI() + ":" + key;
    }

}
