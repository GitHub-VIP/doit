package com.qf.shop.search.dao;

import com.qf.shop.search.pojo.vo.TbItemIndex;

public interface TbItemIndexMapper {

    TbItemIndex  listItemsById(long itemId);
}