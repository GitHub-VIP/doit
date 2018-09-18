package com.qf.shop.manager.dao;

import com.qf.shop.manager.pojo.vo.TbItemCustom;

import java.util.List;
import java.util.Map;

public interface TbItemCustomMapper{

    //long countItems(@Param("query") TbItemQuery query);
    //List<TbItemCustom> listItemsByPage(@Param("page") PageInfo page, @Param("query") TbItemQuery query);

    long countItems(Map<String,Object> map);
    List<TbItemCustom> listItemsByPage(Map<String,Object> map);



}
