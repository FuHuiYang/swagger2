package com.yang.controller;

import com.yang.bean.SZIndex;
import com.yang.mybatis.mapper3.SZIndexMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.controller
 * @Description: 上证
 * @date Date : 2020年03月17日 16:15
 */
@Controller
@RequestMapping("/sz")
public class SZController {

    @Autowired
    SZIndexMapper szIndexMapper;

    @RequestMapping("/indext")
    public String indext() {
        return "index";
    }

    @RequestMapping("/selectSZIndex")
    @ResponseBody
    public List<SZIndex> selectSZIndex(@RequestParam String startdate,@RequestParam String enddate){
        List<SZIndex> szIndexList = szIndexMapper.selectSZIndex(startdate,enddate);
        return szIndexList;
    }

    @RequestMapping("/pullSZData")
    public  void pullSZData(@RequestParam String year,@RequestParam String quarter,@RequestParam String number) throws Exception {
        Document parse =null;
        parse = Jsoup.connect("http://quotes.money.163.com/trade/lsjysj_zhishu_000001.html?year="+year+"&season="+quarter).get();
        Elements bigElement = parse.select(".inner_box");
        Elements trs = bigElement.tagName("table").select("tr");
        List <SZIndex> szlist =new ArrayList();
        int size=number==null?trs.size():Integer.valueOf(number);
        for (int i=1;i<=size;i++){
            SZIndex sz= new SZIndex();
            Elements tds = trs.get(i).select("td");
            sz.setCode("'000001");
            sz.setCodename("上证");
            sz.setCloseTime(DateUtils.parseDate(tds.get(0).text(),"yyyyMMdd"));
            sz.setStartPrice(new BigDecimal(tds.get(1)==null?null:tds.get(1).text().replaceAll(",","")));//开盘
            sz.setMaxPrice(new BigDecimal(tds.get(2)==null?null:tds.get(2).text().replaceAll(",","")));//最高
            sz.setMinPrice(new BigDecimal(tds.get(3)==null?null:tds.get(3).text().replaceAll(",","")));//最低
            sz.setClosePrice(new BigDecimal(tds.get(4)==null?null:tds.get(4).text().replaceAll(",","")));//收盘
            sz.setQuota(new BigDecimal(tds.get(5)==null?null:tds.get(5).text().replaceAll(",","")));//涨跌额
            sz.setRate(new BigDecimal(tds.get(6)==null?null:tds.get(6).text().replaceAll(",","")));//涨跌幅
            sz.setVolume(new BigDecimal(tds.get(7)==null?null:tds.get(7).text().replaceAll(",","")));//成交量
            sz.setAmount(new BigDecimal(tds.get(8)==null?null:tds.get(8).text().replaceAll(",","")));//成交额
            szlist.add(sz);
        }
        szIndexMapper.insertSZList(szlist);
    }
}
