package com.syp.springcloud.clients;

import com.syp.springcloud.api.R;
import com.syp.springcloud.dto.ItemStockDTO;
import com.syp.springcloud.vo.ItemDetailVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author: SYP
 * @Date: 2020/10/13-12-36
 * @Description:
 */
public interface IGoodsFeignClient {
    @PutMapping(value="/items/stock", consumes= MediaType.APPLICATION_JSON_VALUE)
    R decreaseStock(@RequestBody List<ItemStockDTO> itemStockDtos);

    @GetMapping("/items/{ids}")
    R<List<ItemDetailVO>> getItemsByIds(@PathVariable("ids")List<Long> ids);

}
