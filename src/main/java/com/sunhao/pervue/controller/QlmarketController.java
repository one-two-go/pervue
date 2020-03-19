package com.sunhao.pervue.controller;


import com.sunhao.pervue.api.ResultEntity;
import com.sunhao.pervue.entity.Qlmarket;
import com.sunhao.pervue.service.IQlmarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ql
 * @since 2020-03-14
 */
@RestController
@RequestMapping("/qlmarket")
public class QlmarketController {

    @Autowired
    private IQlmarketService iQlmarketService;

    @RequestMapping("marketList")
    public ResultEntity marketList(){
        List<Qlmarket> list = iQlmarketService.list();
        return ResultEntity.ok(list);
    }



}

