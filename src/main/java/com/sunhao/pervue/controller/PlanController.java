package com.sunhao.pervue.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunhao.pervue.api.ResultEntity;
import com.sunhao.pervue.entity.PlanVo;
import com.sunhao.pervue.service.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sunhao
 * @since 2020-03-17
 *
 */
@RestController
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private IPlanService planService;

    // 商品的列表
    @RequestMapping("plansList")
    public ResultEntity PlanList(PlanVo vo, @RequestParam(defaultValue = "1") long current, @RequestParam(defaultValue = "3")long size){

        Page page = new Page<>(current,size);
        IPage<PlanVo> pageInfo = planService.selectPlanList(page,vo);
        return ResultEntity.ok(pageInfo);
    }
}

