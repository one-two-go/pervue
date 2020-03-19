package com.sunhao.pervue.controller;


import com.sunhao.pervue.api.ResultEntity;
import com.sunhao.pervue.service.IUser_roleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ql
 * @since 2020-03-03
 */
@RestController
@RequestMapping("/user_role")
public class User_roleController {

    @Autowired
    private IUser_roleService iUser_roleService;



    @RequestMapping("/insert")
    public ResultEntity insert_role(Integer rids[], Integer uid) {

        try{
            iUser_roleService.insertBatch(rids,uid);
            return ResultEntity.ok(true);
        } catch (Exception e) {
            System.err.println("异常");
            e.printStackTrace();
            //返回结果
            return ResultEntity.error();
        }

    }
}

