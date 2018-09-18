package com.qf.shop.manager.web;

import com.qf.shop.common.core.JsonUtils;
import com.qf.shop.common.core.PropKit;
import com.qf.shop.common.core.StrKit;
import com.qf.shop.common.fdfs.FastDFSFile;
import com.qf.shop.common.fdfs.FastDFSUtils;
import com.qf.shop.common.pojo.dto.ItemResult;
import com.qf.shop.common.pojo.dto.MessageResult;
import com.qf.shop.common.pojo.dto.PageInfo;
import com.qf.shop.manager.pojo.vo.TbItemCustom;
import com.qf.shop.manager.pojo.vo.TbItemQuery;
import com.qf.shop.manager.service.ItemIndexService;
import com.qf.shop.manager.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: DHC
 * Date: 2018/8/29
 * Time: 14:09
 * Version:V1.0
 */
@Controller
public class ManagerItemAction {
    //初始化logger
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //依赖注入service
    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemIndexService itemIndexService;

    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;


    @ResponseBody
    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public ItemResult<TbItemCustom> listItemsByPage(PageInfo page, TbItemQuery query) {
        ItemResult<TbItemCustom> result = null;
        try {
            result = itemService.listItemsByPage(page, query);
        } catch (Exception e) {
            //通过logback将异常打印日志中,ConsoleAppender FileAppender
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    //数组，List
    @ResponseBody
    @RequestMapping(value = "/item/batch", method = RequestMethod.POST)
    public Object updateItemsByIds(@RequestParam("ids[]") List<Long> ids) {
        int i = 0;
        try {
            i = itemService.updateItemsByIds(ids);
        } catch (Exception e) {
            //通过logback将异常打印日志中,ConsoleAppender FileAppender
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }

    @ResponseBody
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public String uploadImage(@RequestParam MultipartFile file) {
        //入参的变量名称一定要跟前台页面的控件名name一致
        Map<String, Object> map = new HashMap<String, Object>();
        String jsonString = null;
        try {
            //1 获取fastdfsfile
            FastDFSFile fastDFSFile = new FastDFSFile(file.getBytes(), file.getOriginalFilename(), file.getSize());
            //2 调用fastdfsutils.upload
            //如果调用uploadFile成功，返回的字符串就是如下格式：group1/M00/00/00/xxxxx.jpg
            String filePath = FastDFSUtils.uploadFile(fastDFSFile);
            //http://101.132.38.253
            String basePath = PropKit.use("fdfs_client.conf").get("fdfs_server");
            if (StrKit.notBlank(filePath)) {
                //上传成功
                map.put("code", 0);
                map.put("msg", "success");
                Map<String, Object> dataMap = new HashMap<String, Object>();
                dataMap.put("src", basePath + "/" + filePath);
                map.put("data", dataMap);
            } else {
                //上传失败
                map.put("code", 1);
                map.put("msg", "failed");
                Map<String, Object> dataMap = new HashMap<String, Object>();
                dataMap.put("src", "");
                map.put("data", dataMap);
            }
            //3 返回JSON
            jsonString = JsonUtils.objectToJson(map);
        } catch (Exception e) {
            //通过logback将异常打印日志中,ConsoleAppender FileAppender
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return jsonString;
    }

    @ResponseBody
    @RequestMapping(value = "/item/indexlib/import", method = RequestMethod.POST)
//    @PostMapping("/item/indexlib/import")
    public MessageResult importIndex() {
        MessageResult messageResult = new MessageResult();
        messageResult.setSuccess(false);
        messageResult.setMsg("failed");
        try {
            itemIndexService.importIndex();
            messageResult.setSuccess(true);
            messageResult.setMsg("success");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return messageResult;
    }

    //发布消息
    @ResponseBody
    @RequestMapping(value = "/item",method = RequestMethod.POST)
    public void saveItem(){
        //1.发布(保存)商品
        long itemId = 562379l;
        //2.发布消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(itemId + "");
                return textMessage;
            }
        });


    }



}
