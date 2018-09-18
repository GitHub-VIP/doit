package com.qf.shop.search.service.impl;

import com.qf.shop.search.dao.SearchIndexDao;
import com.qf.shop.search.pojo.dto.TbItemIndexResult;
import com.qf.shop.search.service.SearchIndexService;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: DHC
 * Date: 2018/9/14
 * Time: 14:18
 * Version:V1.0
 */
@Service
public class SearchIndexServiceImpl implements SearchIndexService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SearchIndexDao searchIndexDao;

    @Override
    public TbItemIndexResult searchIndex(String keyword, int pageIndex, int pageSize) {
        TbItemIndexResult result = null;
        try {
            //创建一个solr的查询对象
            SolrQuery query = new SolrQuery();
            //给查询对象设置查询条件
            //设置关键字
            query.setQuery(keyword);
            //设置页码
            if (pageIndex <= 0) {
                pageIndex = 1;
            }
            //设置start rows
            query.setStart((pageIndex - 1) * pageSize);
            query.setRows(pageSize);
            //设置查询的内容关键字
            query.set("df", "item_keywords");
            //设置是否高亮
            query.setHighlight(true);
            query.addHighlightField("item_title");
            query.setHighlightSimplePre("<em style='color:red'>");
            query.setHighlightSimplePost("</em>");
            //执行查询
            result = searchIndexDao.search(query,pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
}
