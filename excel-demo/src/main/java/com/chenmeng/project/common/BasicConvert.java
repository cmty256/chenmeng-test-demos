package com.chenmeng.project.common;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class BasicConvert {

  public static <T extends BasePageDTO> Page<T> convertPage(T obj) {
    Page<T> page = new Page<>();
    page.setCurrent(obj.getCurrent());
    page.setSize(obj.getSize());
    if (CollectionUtil.isEmpty(obj.getOrdres())) {
      OrderItem orderItem = new OrderItem();
      orderItem.setColumn("create_time");
      orderItem.setAsc(false);
      obj.getOrdres().add(orderItem);
    }

    page.setOrders(obj.getOrdres());

    return page;
  }
}