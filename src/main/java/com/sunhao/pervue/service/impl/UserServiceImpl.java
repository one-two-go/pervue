package com.sunhao.pervue.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunhao.pervue.entity.User;
import com.sunhao.pervue.entity.UserVo;
import com.sunhao.pervue.mapper.UserMapper;
import com.sunhao.pervue.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ql
 * @since 2020-03-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<UserVo> selectPageVo(Page page, UserVo vo) {
        return userMapper.selectPageVo(page,vo);
    }
}
