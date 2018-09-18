package com.qf.shop.manager.service.impl;

import com.qf.shop.manager.dao.TbItemIndexMapper;
import com.qf.shop.manager.pojo.vo.TbItemIndex;
import com.qf.shop.manager.service.ItemIndexService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemIndexServiceImpl implements ItemIndexService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbItemIndexMapper itemIndexDao;

    @Autowired
    private SolrServer solrServer;

    @Override
    public void importIndex() {

        try {
            //1 采集数据  注入dao层接口
            List<TbItemIndex> list = itemIndexDao.listItemsByCondition();
            //2 遍历数据集产生documentList,注入solrServer
            for (TbItemIndex itemIndex :list){
                //创建一个文档
                SolrInputDocument document = new SolrInputDocument();
                //逐个添加字段到文档中
                document.addField("id",itemIndex.getId());
                document.addField("item_title",itemIndex.getTitle());
                document.addField("item_sell_point",itemIndex.getSellPoint());
                document.addField("item_price",itemIndex.getPrice());
                document.addField("item_image",itemIndex.getImage());
                document.addField("item_category_name",itemIndex.getCatName());

                //添加到索引库
                solrServer.add(document);

            }

            //提交 注入solrServer
            solrServer.commit();

        }catch (Exception e){
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }

    }
}
