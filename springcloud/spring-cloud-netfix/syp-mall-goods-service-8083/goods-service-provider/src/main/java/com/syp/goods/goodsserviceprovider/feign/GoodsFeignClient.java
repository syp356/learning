package com.syp.goods.goodsserviceprovider.feign;

import com.syp.goods.goodsserviceprovider.converter.ItemConverter;
import com.syp.goods.goodsserviceprovider.domain.ItemStockDO;
import com.syp.goods.goodsserviceprovider.mapper.entitys.TbItem;
import com.syp.goods.goodsserviceprovider.service.IItemService;
import com.syp.springcloud.api.R;
import com.syp.springcloud.clients.IGoodsFeignClient;
import com.syp.springcloud.dto.ItemStockDTO;
import com.syp.springcloud.vo.ItemDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: SYP
 * @Date: 2020/10/13
 * @Description:
 */
@Slf4j
@RestController
public class GoodsFeignClient implements IGoodsFeignClient {
    @Autowired
    IItemService itemService;

    @Autowired
    ItemConverter itemConverter;

    public R decreaseStock(List<ItemStockDTO> itemStockDtos) {
        log.info("begin GooodsFeignClient.decreaseStock: "+itemStockDtos);
        List<ItemStockDO> itemStockDOs = itemConverter.itemStock2DOList(itemStockDtos);
        boolean rs = itemService.decreaseStock(itemStockDOs);
        return new R.Builder().buildOk();
    }

    public R<List<ItemDetailVO>> getItemsByIds(List<Long> ids) {
        log.info("begin GoodsFeignClient.getItemsByIds:"+ids);
        List<TbItem> itemList = itemService.findItemsByIds(ids);
        List<ItemDetailVO> itemDetailVOs =itemConverter.itemDetail2VOList(itemList);
        return new R.Builder<List<ItemDetailVO>>().setData(itemDetailVOs).buildOk();
    }
}
