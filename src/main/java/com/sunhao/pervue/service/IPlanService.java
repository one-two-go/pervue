package com.sunhao.pervue.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunhao.pervue.entity.Plan;
import com.sunhao.pervue.entity.PlanVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunhao
 * @since 2020-03-17
 */
public interface IPlanService extends IService<Plan> {

    IPage<PlanVo> selectPlanList(Page page, PlanVo vo);
}
