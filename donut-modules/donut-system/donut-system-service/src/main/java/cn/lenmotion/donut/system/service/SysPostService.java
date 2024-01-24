package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.system.entity.po.SysPost;
import cn.lenmotion.donut.system.entity.query.PostQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author LenMotion
 */
public interface SysPostService extends DonutService<SysPost> {

    /**
     * 分页查询
     *
     * @param postQuery
     * @return
     */
    IPage<SysPost> selectPostList(PostQuery postQuery);

    /**
     * 查询组织机构下面的岗位（包含全局岗位）
     * @param postQuery
     * @return
     */
    List<SysPost> selectDeptPostList(PostQuery postQuery);
}
