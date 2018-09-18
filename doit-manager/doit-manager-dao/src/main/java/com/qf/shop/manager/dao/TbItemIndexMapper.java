package com.qf.shop.manager.dao;

import com.qf.shop.manager.pojo.vo.TbItemIndex;

import java.util.List;

public interface TbItemIndexMapper {

    List<TbItemIndex>  listItemsByCondition();
}