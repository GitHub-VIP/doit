package com.qf.shop.manager.service.impl;

import com.qf.shop.common.pojo.dto.ItemResult;
import com.qf.shop.common.pojo.dto.PageInfo;
import com.qf.shop.manager.dao.TbItemCustomMapper;
import com.qf.shop.manager.dao.TbItemMapper;
import com.qf.shop.manager.pojo.po.TbItem;
import com.qf.shop.manager.pojo.po.TbItemExample;
import com.qf.shop.manager.pojo.vo.TbItemCustom;
import com.qf.shop.manager.pojo.vo.TbItemQuery;
import com.qf.shop.manager.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    //初始化logger
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //依赖注入DAO层接口(第一个缺少的分页查询,第二个缺少求数量)
    @Autowired
    private TbItemCustomMapper itemCustomDao;
    @Autowired
    private TbItemMapper itemDao;

    @Override
    public ItemResult<TbItemCustom> listItemsByPage(PageInfo page, TbItemQuery query) {

        ItemResult<TbItemCustom> result = new ItemResult<>();
        //c查询正确的情况下返回0,否则返回非0
        result.setCode(0);
        result.setMsg("success");

        try {
            //方法一:@Param("query")  @Param("page")
            //调用DAO层接口查询商品的总数量

            //方法二   解决DAO层多参数传递问题
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("page",page);
            map.put("query",query);
            //调用DAO层接口查询商品的总数量
            long count = itemCustomDao.countItems(map);
            //调用DAO层接口将符合条件的集合查询出来
            List<TbItemCustom> list = itemCustomDao.listItemsByPage(map);
            result.setCount(count);
            result.setData(list);

            /*long count = itemCustomDao.countItems(query);
            //调用DAO层接口将符合条件的集合查询出来
            List<TbItemCustom> list = itemCustomDao.listItemsByPage(page,query);
            result.setCount(count);
            result.setData(list);*/

        } catch (Exception e) {
            result.setCode(1);
            result.setMsg("failed");
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }


        return result;
    }

    @Override
    public int updateItemsByIds(List<Long> ids) {

        int i = 0;
        try {

            //封装一个商品对象,携带了删除状态
            TbItem record = new TbItem();
            record.setStatus((byte) 3);
            //使用example   创建模板
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();

            //设值
            criteria.andIdIn(ids);
            //真正执行修改操作
            i = itemDao.updateByExampleSelective(record,example);

        }catch (Exception e){

            logger.error(e.getMessage(),e);
            e.printStackTrace();

        }

        return i;
    }
}
