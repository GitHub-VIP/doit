package com.qf.shop.search.web;

import com.qf.shop.common.core.PropKit;
import com.qf.shop.common.core.StrKit;
import com.qf.shop.search.pojo.dto.TbItemIndexResult;
import com.qf.shop.search.service.SearchIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * User: DHC
 * Date: 2018/5/8
 * Time: 10:03
 * Version:V1.0
 */
@Controller
public class SearchIndexAction {

    @Autowired
    private SearchIndexService searchIndexService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String search(String keyword, @RequestParam(defaultValue = "1") int pageIndex, Model model){
        //pageIndex当前页的页码
        //pageSize每页显示的条数
        if(StrKit.notBlank(keyword)) {
            //读取类路径下的配置文件，每页显示条数为60条
            int pageSize = PropKit.use("file.properties").getInt("search.pagesize");
            //服务层查询
            TbItemIndexResult result = searchIndexService.searchIndex(keyword, pageIndex, pageSize);
            //存放作用域中
            model.addAttribute("list", result.getList());
        }
        //转发到下一个页面
        return "index";
    }
}
