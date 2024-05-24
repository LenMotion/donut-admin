package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.system.entity.po.SysTenant;
import cn.lenmotion.donut.system.entity.query.SysTenantQuery;
import cn.lenmotion.donut.system.entity.request.SysTenantRequest;
import cn.lenmotion.donut.system.entity.vo.RoleMenuIdVO;
import cn.lenmotion.donut.system.entity.vo.TenantBaseVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author lenmotion
 */
public interface SysTenantService extends DonutService<SysTenant> {

    /**
     * 分页查询
     * @param query 查询条件
     * @return      分页数据
     */
    IPage<SysTenant> selectPageList(SysTenantQuery query);

    /**
     * 新增或修改
     * @param request
     * @return
     */
    Boolean saveOrUpdate(SysTenantRequest request);

    /**
     * 根据id查询
     * @param ids
     * @return
     */
    List<TenantBaseVO> baseInfoByIds(List<Long> ids);

    /**
     * 根据id查询菜单
     * @param tenantId
     * @return
     */
    RoleMenuIdVO listMenuIdByTenantId(Long tenantId);
}
