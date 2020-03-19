package com.sunhao.pervue.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunhao.pervue.entity.Plan;
import com.sunhao.pervue.entity.PlanVo;
import com.sunhao.pervue.mapper.PlanMapper;
import com.sunhao.pervue.service.IPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.font.TrueTypeFont;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sunhao
 * @since 2020-03-17
 */
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements IPlanService {

    @Autowired
    private PlanMapper planMapper ;

    @Override
    public IPage<PlanVo> selectPlanList(Page page, PlanVo vo) {

        return planMapper.selectPlanList(page,vo);
    }
}
