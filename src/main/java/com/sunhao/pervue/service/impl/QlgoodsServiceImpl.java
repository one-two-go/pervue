package com.sunhao.pervue.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunhao.pervue.entity.QlGoodsVo;
import com.sunhao.pervue.entity.Qlgoods;
import com.sunhao.pervue.mapper.QlgoodsMapper;
import com.sunhao.pervue.service.IQlgoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ql
 * @since 2020-03-14
 */
@Service
public class QlgoodsServiceImpl extends ServiceImpl<QlgoodsMapper, Qlgoods> implements IQlgoodsService {

    @Autowired
    private QlgoodsMapper qlgoodsMapper;

    @Override
    public IPage<QlGoodsVo> selectGoodsList(Page page, QlGoodsVo vo) {
        return qlgoodsMapper.selectGoodsList(page,vo);
    }
}
