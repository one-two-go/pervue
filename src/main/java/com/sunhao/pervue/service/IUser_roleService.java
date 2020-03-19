package com.sunhao.pervue.service;

import com.sunhao.pervue.entity.User_role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ql
 * @since 2020-03-03
 */
public interface IUser_roleService extends IService<User_role> {


    void insertBatch(Integer[] rids, Integer uid) throws Exception;


}
