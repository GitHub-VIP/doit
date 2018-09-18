package com.qf.shop.portal.service;

import com.qf.shop.portal.pojo.po.TbContent;

import java.util.List;

/**
 * User: DHC
 * Date: 2018/9/11
 * Time: 16:16
 * Version:V1.0
 */
public interface ContentService {
    List<TbContent> listContentsByCid(Long cid);
}
