package com.sunhao.pervue.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunhao.pervue.entity.Plan;
import com.sunhao.pervue.entity.PlanVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sunhao
 * @since 2020-03-17
 */
public interface PlanMapper extends BaseMapper<Plan> {

    IPage<PlanVo> selectPlanList(Page page, PlanVo vo);
}
