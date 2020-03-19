package com.sunhao.pervue.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunhao.pervue.entity.QlGoodsVo;
import com.sunhao.pervue.entity.Qlgoods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ql
 * @since 2020-03-14
 */
public interface IQlgoodsService extends IService<Qlgoods> {

    IPage<QlGoodsVo> selectGoodsList(Page page, QlGoodsVo vo);

}
