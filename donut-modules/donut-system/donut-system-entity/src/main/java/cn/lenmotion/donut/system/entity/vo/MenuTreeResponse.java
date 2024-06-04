package cn.lenmotion.donut.system.entity.vo;

import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.system.entity.po.SysMenu;

import java.util.List;

/**
 * @author lenmotion
 */
public class MenuTreeResponse extends ResponseResult<List<SysMenu>> {
    public MenuTreeResponse(Integer code, String msg, List<SysMenu> result, String traceId) {
        super(code, msg, result, traceId);
    }
}
