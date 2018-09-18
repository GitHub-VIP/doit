package com.qf.shop.search.dao;

import com.qf.shop.search.pojo.dto.TbItemIndexResult;
import com.qf.shop.search.pojo.vo.TbItemIndex;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: DHC
 * Date: 2018/9/14
 * Time: 14:46
 * Version:V1.0
 */
@Repository
public class SearchIndexDao {

    @Autowired
    private SolrServer solrServer;

    public TbItemIndexResult search(SolrQuery query, int pageSize) {
        TbItemIndexResult result = new TbItemIndexResult();
        try {
            //获取QueryResponse
            QueryResponse response = solrServer.query(query);
            //获取DocumentList
            SolrDocumentList documentList = response.getResults();
            //获取到符合条件的总记录数
            long recordCount = documentList.getNumFound();
            // recordCount11  pageSize2  ===>  总页数6
            //退一进一法
            long totalPages = (recordCount + pageSize - 1) / pageSize;
            result.setRecordCount(recordCount);
            result.setTotalPages(totalPages);
            //先获取高亮集合
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //1
            //获取list，result.setList(list);
            List<TbItemIndex> list = new ArrayList<TbItemIndex>();

            for (SolrDocument document : documentList) {
                TbItemIndex it = new TbItemIndex();
                it.setId((String) document.get("id"));
                it.setImage((String) document.get("item_image"));
                it.setPrice((Long) document.get("item_price"));
                it.setCatName((String) document.get("item_category_name"));
                it.setSellPoint((String) document.get("item_sell_point"));
                //2
                List<String> stringList = highlighting.get(document.get("id")).get("item_title");
                String title = "";
                if (stringList != null && !stringList.isEmpty()) {
                    //有高亮
                    title = stringList.get(0);
                } else {
                    //无高亮
                    title = (String) document.get("item_title");
                }
                it.setTitle(title);
                list.add(it);
            }
            result.setList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
