package com.wheat.feishuweb.controller;

import com.github.pagehelper.PageInfo;
import com.wheat.feishuweb.pojo.UserAttendanceData;
import com.wheat.feishuweb.service.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

/**
 * @projectName: test
 * @package: com.wheat.feishuweb.controller
 * @className: FeishuController
 * @author: Wheat
 * @description:
 *
 * @date: 2023/1/29 15:35
 * @version: 1.0
 */
@Controller
public class FeishuController {

    @Autowired
    private ServiceTest serviceTest;

    @GetMapping("/")
    public String index(Model model, HttpSession session, String startDate, String endDate) throws Exception {
//        扩大 请求域范围 以向浏览器保存两个时间
        session.setAttribute("startDate", startDate);
        session.setAttribute("endDate", endDate);
        if (startDate != null && endDate != null){
            startDate=startDate.replaceAll("-","");
            endDate=endDate.replaceAll("-","");
        }
        Boolean flag = serviceTest.getDataByMonth(startDate, endDate);
        if (flag == false){
            model.addAttribute("msg","查询时间请保持在一个月内！");
        }
        PageInfo pageInfo = serviceTest.getDataByPage(1);
        model.addAttribute("pageInfo", pageInfo);
        return "dataPage";
    }

//    按姓名查询
    @GetMapping("/name")
    public String getDataByName(Model model, String name) throws Exception {
        if (!name.equals("") && name != null){
            PageInfo<UserAttendanceData> pageInfo = serviceTest.getDataByName(name, 1);
            model.addAttribute("pageInfo", pageInfo);
        }else {
            model.addAttribute("msg", "查无此人！");
        }
        return "dataPage";
    }

//    分页查询
    @GetMapping("/data/page/{pageNum}")
    public String getDataByPage(Model model, @PathVariable("pageNum")Integer pageNum) throws Exception {
        PageInfo pageInfo = serviceTest.getDataByPage(pageNum);
        model.addAttribute("pageInfo",pageInfo);
        return "dataPage";
    }

//    获取员工id并存储
    @GetMapping("/getUserId")
    public String getUserId() throws Exception {
        serviceTest.saveIds();
        return "success";
    }

//    数据按升序或降序排序
    @GetMapping("/sort/{sortType}/{sortCol}/{pageNum}")
    public String getDataByDesc(Model model, @PathVariable("sortType") String sortType,
                                @PathVariable("sortCol") String sortCol,
                                @PathVariable("pageNum") Integer pageNum) throws Exception {
        PageInfo<UserAttendanceData> pageInfo = serviceTest.getDataByDesc(sortType,sortCol, pageNum);
        model.addAttribute("sortCol", sortCol);
        model.addAttribute("sortType", sortType);
        model.addAttribute("pageInfo",pageInfo);
        return "sortPage";
    }

}
