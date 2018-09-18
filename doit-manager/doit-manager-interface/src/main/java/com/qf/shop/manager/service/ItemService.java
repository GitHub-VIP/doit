package com.qf.shop.manager.service;

import com.qf.shop.common.pojo.dto.ItemResult;
import com.qf.shop.common.pojo.dto.PageInfo;
import com.qf.shop.manager.pojo.vo.TbItemCustom;
import com.qf.shop.manager.pojo.vo.TbItemQuery;

import java.util.List;

public interface ItemService {

    /**
     * 处理商品的服务层相关接口
     * 分页查询
     * @param   page 分页条件
     * @return 返回的JSON格式
     */
    ItemResult<TbItemCustom> listItemsByPage(PageInfo page, TbItemQuery query);

    //批量修改状态
    int updateItemsByIds(List<Long> ids);

}
