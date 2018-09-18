package com.qf.shop.search.listener;

import com.qf.shop.search.dao.TbItemIndexMapper;
import com.qf.shop.search.pojo.vo.TbItemIndex;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private TbItemIndexMapper itemIndexDao;

    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {

        try {
            //1.获取消息
            TextMessage textMessage = (TextMessage)message;
            long itemId = Long.parseLong(textMessage.getText());
            //2.通过ID查询对象
            TbItemIndex itemIndex = itemIndexDao.listItemsById(itemId);
            //3.将对象添加到索引库
            SolrInputDocument document = new SolrInputDocument();

            document.addField("id",itemIndex.getId());
            document.addField("item_title",itemIndex.getTitle());
            document.addField("item_sell_point",itemIndex.getSellPoint());
            document.addField("item_price",itemIndex.getPrice());
            document.addField("item_image",itemIndex.getImage());
            document.addField("item_category_name",itemIndex.getCatName());

            //添加到索引库
            solrServer.add(document);
            //提交,注入solrServer
            solrServer.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
