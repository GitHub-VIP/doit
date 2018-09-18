package com.qf.shop.manager.pojo.vo;


import com.qf.shop.manager.pojo.po.TbItem;

public class TbItemCustom extends TbItem {

    //商品名称
    private String catName;

    //正常|下架
    private String statusName;

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
