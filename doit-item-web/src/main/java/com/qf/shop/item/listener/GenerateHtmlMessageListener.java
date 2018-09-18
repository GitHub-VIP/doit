package com.qf.shop.item.listener;

import com.qf.shop.item.dao.TbItemIndexMapper;
import com.qf.shop.item.pojo.vo.TbItemIndex;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * User: DHC
 * Date: 2018/9/18
 * Time: 9:29
 * Version:V1.0
 */
public class GenerateHtmlMessageListener implements MessageListener {

    @Autowired
    private TbItemIndexMapper itemIndexDao;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Override
    public void onMessage(Message message) {
        try {
            //1 获取消息
            TextMessage textMessage = (TextMessage) message;
            long itemId = Long.parseLong(textMessage.getText());
            //2 通过ID查询对象
            TbItemIndex itemIndex = itemIndexDao.listItemsById(itemId);
            Map<String, Object> dataModel = new HashMap<String, Object>();
            dataModel.put("item", itemIndex);
            //3 产生静态页面
            //获取配置对象
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            //获取模板对象
            Template template = configuration.getTemplate("index.ftl");
            //获取输出流
            Writer out = new FileWriter("d:/web/" + itemId + ".html");
            template.process(dataModel,out);
            //释放资源
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
