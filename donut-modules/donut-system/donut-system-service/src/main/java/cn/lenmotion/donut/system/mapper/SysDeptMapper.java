package cn.lenmotion.donut.system.mapper;

import cn.lenmotion.donut.system.entity.po.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author LenMotion
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 更新部门的层级id
     * @param oldAncestors
     * @param newAncestors
     * @return
     */
    int updateDeptAncestors(@Param("oldAncestors") String oldAncestors, @Param("newAncestors") String newAncestors);

    /**
     * 更新部门的层级数量
     * @param oldAncestors
     * @param reduceLevel
     * @return
     */
    int updateDeptLevel(@Param("oldAncestors") String oldAncestors, @Param("reduceLevel") Integer reduceLevel);

    /**
     * 获取部门下面的最大层级
     * @param ancestors
     * @return
     */
    Integer maxChildLevel(String ancestors);

    /**
     * 用户是否有某个部门的权限
     * @param deptId
     * @param userId
     * @return
     */
    Integer hasDeptIdByUserId(@Param("deptId") Long deptId, @Param("userId") Long userId);

}