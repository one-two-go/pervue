package com.sunhao.pervue.controller;


import com.sunhao.pervue.api.ResultEntity;
import com.sunhao.pervue.entity.Menu;
import com.sunhao.pervue.entity.User;
import com.sunhao.pervue.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    @RequestMapping("list")
    public ResultEntity list(){
        List<Menu> menus = iMenuService.listMenus();
        return ResultEntity.ok(menus);
    }

    @RequestMapping("menuByname")
    public ResultEntity listMenuByUsername(HttpServletRequest request ,String username){

        System.err.println( "列表:"+request.getSession().getId());
        User user = (User) request.getSession().getAttribute("user");

        if(user != null ){
            username = user.getUsername();
        }

        List<Menu> menus = iMenuService.listMenuByUsername(username);
        return ResultEntity.ok(menus);
    }

}


