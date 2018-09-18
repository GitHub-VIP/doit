package com.qf.shop.search.service;

import com.qf.shop.search.pojo.dto.TbItemIndexResult;

/**
 * 封装搜索索引库的业务逻辑层接口
 * User: DHC
 * Date: 2018/9/14
 * Time: 14:14
 * Version:V1.0
 */
public interface SearchIndexService {
    /**
     * 根据条件查询索引库
     * @param keyword  前台传递过来查询关键字
     * @param pageIndex 当前页的页码
     * @param pageSize 每页显示的条数
     * @return
     */
    TbItemIndexResult searchIndex(String keyword, int pageIndex, int pageSize);
}
