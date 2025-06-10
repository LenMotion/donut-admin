package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.system.entity.po.SysPost;
import cn.lenmotion.donut.system.entity.query.PostQuery;
import cn.lenmotion.donut.system.mapper.SysPostMapper;
import cn.lenmotion.donut.system.service.SysPostService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author LenMotion
 */
@Service
public class SysPostServiceImpl extends DonutServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    @Override
    public IPage<SysPost> selectPostList(PostQuery postQuery) {
        LambdaQueryWrapper<SysPost> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StrUtil.isNotBlank(postQuery.getPostName()), SysPost::getPostName, postQuery.getPostName())
                .like(StrUtil.isNotBlank(postQuery.getPostCode()), SysPost::getPostCode, postQuery.getPostCode())
                .eq(ObjUtil.isNotNull(postQuery.getDeptId()), SysPost::getDeptId, postQuery.getDeptId())
                .eq(StrUtil.isNotBlank(postQuery.getStatus()), SysPost::getStatus, postQuery.getStatus())
                .eq(ObjUtil.isNotNull(postQuery.getPostType()), SysPost::getPostType, postQuery.getPostType())
                .orderByAsc(SysPost::getOrderNo);
        return baseMapper.selectPage(postQuery.toPage(), queryWrapper);
    }

    @Override
    public List<SysPost> selectDeptPostList(PostQuery postQuery) {
        Set<Long> queryDeptIds = new HashSet<>(2);
        queryDeptIds.add(BaseConstants.PARENT_ID);

        if (!Objects.isNull(postQuery.getDeptId())) {
            queryDeptIds.add(postQuery.getDeptId());
        }

        LambdaQueryWrapper<SysPost> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SysPost::getDeptId, queryDeptIds)
                .eq(SysPost::getStatus, BaseStatusEnum.ENABLED.getCode())
                .orderByAsc(SysPost::getOrderNo);
        return list(queryWrapper);
    }

    @Override
    public boolean saveOrUpdate(SysPost entity) {
        // 查询岗位名称是否重复
        super.validColumnUnique(entity, SysPost::getPostName, "岗位名称");
        // 查询岗位编码是否重复
        super.validColumnUnique(entity, SysPost::getPostCode, "岗位编号");
        if (Objects.isNull(entity.getId())) {
            entity.setStatus(BaseStatusEnum.DISABLE.getCode());
        }
        return super.saveOrUpdate(entity);
    }

}
