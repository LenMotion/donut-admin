package cn.lenmotion.donut.system.remote.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.lenmotion.donut.system.service.SysLoginLogService;
import cn.lenmotion.donut.system.remote.SysLoginLogRemoteService;
import cn.lenmotion.donut.system.entity.po.SysLoginLog;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysLoginLogRemoteServiceImpl implements SysLoginLogRemoteService {

    private final SysLoginLogService loginLogService;
    private final RSA loginRsa;

    @Override
    public boolean saveLoginLog(SysLoginLog loginLog) {
        return loginLogService.save(loginLog);
    }

    @Override
    public Map<String, SysLoginLog> selectLoginLogByToken(List<String> tokenValueList) {
        if (CollUtil.isEmpty(tokenValueList)) {
            return new HashMap<>(0);
        }

        List<String> tokens = tokenValueList.stream()
                .map(e -> loginRsa.encryptBase64(StrUtil.split(e, StrUtil.COLON).get(3), KeyType.PrivateKey))
                .toList();
        LambdaQueryWrapper<SysLoginLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysLoginLog::getTokenValue, tokens);

        var list = loginLogService.list(queryWrapper);
        return list.stream().collect(Collectors.toMap(e -> loginRsa.decryptStr(e.getTokenValue(), KeyType.PublicKey), e -> e));
    }

    @Override
    public SysLoginLog getLoginLogById(Long id) {
        return loginLogService.getById(id);
    }

}
