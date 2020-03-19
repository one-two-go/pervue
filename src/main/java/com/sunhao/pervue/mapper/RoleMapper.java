package com.sunhao.pervue.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunhao.pervue.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunhao.pervue.entity.RoleVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ql
 * @since 2020-03-03
 */
public interface RoleMapper extends BaseMapper<Role> {


    IPage<RoleVo> selectPageVo(Page page , RoleVo vo);
}
