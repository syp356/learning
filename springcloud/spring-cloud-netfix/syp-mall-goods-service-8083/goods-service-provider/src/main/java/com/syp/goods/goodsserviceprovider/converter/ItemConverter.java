package com.syp.goods.goodsserviceprovider.converter;

import com.syp.goods.goodsserviceprovider.domain.ItemStockDO;
import com.syp.goods.goodsserviceprovider.mapper.entitys.TbItem;
import com.syp.springcloud.dto.ItemStockDTO;
import com.syp.springcloud.vo.ItemDetailVO;

import java.util.List; /**
 * @Author: SYP
 * @Date: 2020/10/13
 * @Description:
 */
public interface ItemConverter {
    List<ItemStockDO> itemStock2DOList(List<ItemStockDTO> itemStockDtos);
    List<ItemDetailVO> itemDetail2VOList(List<TbItem> itemList);
}
