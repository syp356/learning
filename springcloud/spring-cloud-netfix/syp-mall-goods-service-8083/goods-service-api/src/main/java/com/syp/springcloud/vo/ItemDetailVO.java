package com.syp.springcloud.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: SYP
 * @Date: 2020/10/13
 * @Description:
 */
@Data
public class ItemDetailVO {

    private Long id;

    private String title;

    private BigDecimal price;

    private Integer num;

    private String image;

    private Long cid;
}
