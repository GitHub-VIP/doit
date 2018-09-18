package com.qf.shop.common.testTbContent;

public class TestContent {


    //解决查询缓存问题与同步缓存问题
    /*public List<TbContent> getContentListByCid(Long cid){

        try {
            //查询缓存,如果存在直接加载
            jedisClient.hget("CONTENT_LIST",Long.toString(cid));
            if(StringUtils.isNotBlank(json)){
                List<TbContent> list = JsonUtils.jsonToList(json,TbContent.class);
                return list;
            }
        }catch (Exception e){

            e.printStackTrace();
        }
        //如果缓存中没有
        TbContExample example = new TbContentExample();
        TbContExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> clist = contentMapper.selectByExample(example);

        try {
            //将查询出的数据存放到缓存中
            jedisClient.hset("CONTENT_LIST",Long.toString(cid),JsonUtils.objectToJson(clist));
            return clist;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //改数据规则（缓存同步问题）
    public List<TbContent> getContentListByCid(Long cid){

        try {
            //查询缓存,如果存在直接加载
            jedisClient.hget("CONTENT_LIST",content.getId().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        content.setCreated(new Date());
        content.setUpdated(new Date());
        int count =contentMapper.insert(content);

        return count;
    }*/



}
