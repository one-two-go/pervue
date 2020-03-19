package com.sunhao.pervue.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunhao.pervue.api.ResultEntity;
import com.sunhao.pervue.entity.Menu_role;
import com.sunhao.pervue.service.IMenu_roleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ql
 * @since 2020-03-03
 */
@RestController
@RequestMapping("/menu_role")
public class Menu_roleController {

    @Autowired
    private IMenu_roleService iMenu_roleService;

    @RequestMapping("insert")
    public ResultEntity insert(Integer rid, Integer mids[]){

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("rid",rid);
        iMenu_roleService.remove(queryWrapper);

        List<Menu_role> entiyList = new ArrayList<>();

        if(mids != null && mids.length >0){
            for (Integer mid:mids
                 ) {
                Menu_role menu_role = new Menu_role();
                menu_role.setMid(mid);
                menu_role.setRid(rid);
                entiyList.add(menu_role);
            }
            iMenu_roleService.saveBatch(entiyList);

        }
        return ResultEntity.ok(true);
    }

}

