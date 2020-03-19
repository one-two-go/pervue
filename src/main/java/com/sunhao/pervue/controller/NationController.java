package com.sunhao.pervue.controller;


import com.sunhao.pervue.api.ResultEntity;
import com.sunhao.pervue.service.INationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 城市字典表 前端控制器
 * </p>
 *
 * @author ql
 * @since 2020-03-03
 */
@RestController
@RequestMapping("/nation")
public class NationController {

    @Autowired
    private INationService iNationService;

    @RequestMapping("list")
    public ResultEntity list(){
        return ResultEntity.ok(iNationService.findAll());

    }
}

