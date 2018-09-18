package com.qf.shop.portal.service.impl;

import com.qf.shop.common.jedis.JedisClient;
import com.qf.shop.common.core.JsonUtils;
import com.qf.shop.common.core.StrKit;
import com.qf.shop.portal.dao.TbContentMapper;
import com.qf.shop.portal.pojo.po.TbContent;
import com.qf.shop.portal.pojo.po.TbContentExample;
import com.qf.shop.portal.service.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: DHC
 * Date: 2018/9/11
 * Time: 16:18
 * Version:V1.0
 */
@Service
public class ContentServiceImpl implements ContentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TbContentMapper contentDao;
    @Autowired
    private JedisClient jedisClient;

    @Override
    public List<TbContent> listContentsByCid(Long cid) {
        List<TbContent> contentList = null;
        //1 去缓存服务器中查询
        try {
            String jsonString = jedisClient.hget("CONTENTLIST", cid + "");
            if(StrKit.notBlank(jsonString)){
                //缓存中有数据
                contentList = JsonUtils.jsonToList(jsonString, TbContent.class);
                return contentList;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        //2 真实的业务逻辑，去数据库中查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        contentList = contentDao.selectByExample(example);
        //3 存放一份到缓存服务器
        try {
            jedisClient.hset("CONTENTLIST", cid + "", JsonUtils.objectToJson(contentList));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return contentList;
    }
}
