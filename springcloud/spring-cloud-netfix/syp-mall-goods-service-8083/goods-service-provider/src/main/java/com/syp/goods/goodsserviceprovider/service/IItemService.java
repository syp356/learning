package com.syp.goods.goodsserviceprovider.service;

import com.syp.goods.goodsserviceprovider.domain.ItemStockDO;
import com.syp.goods.goodsserviceprovider.mapper.entitys.TbItem;

import java.util.List;

/**
 * @Author: SYP
 * @Date: 2020/10/13-13-09
 * @Description:
 */
public interface IItemService {
    List<TbItem> findItemsByIds(List<Long> ids);
    boolean decreaseStock(List<ItemStockDO> itemStockDOS);
}
