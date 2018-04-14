package com.lt.website.controller.admin;

import com.lt.core.constants.ResultStatus;
import com.lt.domain.sys.Finance;
import com.lt.domain.sys.Salary;
import com.lt.domain.sys.SysUser;
import com.lt.webcore.service.sys.FinanceService;
import com.lt.webcore.service.sys.SalaryService;
import com.lt.webcore.service.sys.impl.FinanceServiceImpl;
import com.lt.website.excelinput.ExcelProductEntity;
import com.lt.website.excelinput.ReadExcel;
import com.lt.website.utils.ImportExcleUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.薛 on 2017/9/4 0004.
 * 教职工薪资信息
 */
@Controller
@RequestMapping("/admin/finance")
public class FinanceController {
    private Logger log = Logger.getLogger(FinanceController.class);
    @Autowired
    FinanceService financeService;//教职工工资管理
    @Autowired
    SalaryService salaryService;//工资分布

    //  教职工薪资信息管理主页面信息
    @RequestMapping("")
    public ModelAndView toIndex() {
        ModelAndView mv = new ModelAndView("/admin/finance_list");
        return mv;
    }


    //分页显示教职工薪资信息信息
    @ResponseBody
    @RequestMapping("/searchFinance")
    public Object searchFinance(Integer departmentid,Integer tid,String name, Integer page, Integer rows) {
        return financeService.selectForPage(departmentid, tid,name, page, rows);
    }


//    添加教职工薪资信息
    @ResponseBody
    @RequestMapping("/addFinance")
    public Object addFinance(Finance finance)
    {
        Map map = new HashMap();
        SysUser user = financeService.selectByName(finance.getName());
//        根据部门ID查询显示部门名称
        String dname = financeService.selectByDid(user.getDepartmentid());
//        根据等级ID查询显示等级名称
        String tname = financeService.selectByTid(user.getTid());
//        根据部门ID和等级ID查询显示工资分布
        Salary salary = financeService.selectByIds(user.getDepartmentid(),user.getTid());
        finance.setDname(dname);//部门名称
        finance.setDepartmentid(user.getDepartmentid());
        finance.setTname(tname);//等级名称
        finance.setTid(user.getTid());
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        finance.setTime(dateFormater.format(date));//时间
        finance.setBase(salary.getBase());//基础薪资
        finance.setMoney1(salary.getMoney1());//课时费
        finance.setMoney2(salary.getMoney2());//配额课时费
        finance.setMoney3(salary.getMoney3());//薪资补助
        finance.setMoney4(salary.getMoney4());//其他补助
//        教师薪资=基础薪资+（课时费*课时）+（配额课时*配额课时费）+薪资补助+其他补助
        finance.setMoney5(finance.getBase()+
                (finance.getMoney1()*finance.getTime1())+
                        (finance.getMoney2()*finance.getTime2())
                        +finance.getMoney3()+finance.getMoney4());
        int flag = financeService.addFinance(finance);
        if(flag==1)
        {
            map.put("status", ResultStatus.RESULT_STATUS_SUCCESS);
            map.put("errorMsg", "薪资信息添加成功！");
        }else
        {
            map.put("status", ResultStatus.RESULT_STATUS_FAILURE);
            map.put("errorMsg", "薪资信息添加失败！");
        }
        return map;
    }


//    根据姓名和两个课时  吧信息插入数据库中
    public boolean insert(String name,double time1,Double time2)
    {
        boolean flag = false;
        Finance finance = new Finance();
//        根据姓名查询用户信息
        SysUser user = financeService.selectByName(name);
//        根据部门ID查询显示部门名称
        String dname = financeService.selectByDid(user.getDepartmentid());
//        根据等级ID查询显示等级名称
        String tname = financeService.selectByTid(user.getTid());
//        根据部门ID和等级ID查询显示工资分布
        Salary salary = financeService.selectByIds(user.getDepartmentid(),user.getTid());
        finance.setDname(dname);//部门名称
        finance.setDepartmentid(user.getDepartmentid());
        finance.setTname(tname);//等级名称
        finance.setTid(user.getTid());
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        finance.setTime(dateFormater.format(date));//时间
        finance.setBase(salary.getBase());//基础薪资
        finance.setMoney1(salary.getMoney1());//课时费
        finance.setMoney2(salary.getMoney2());//配额课时费
        finance.setMoney3(salary.getMoney3());//薪资补助
        finance.setMoney4(salary.getMoney4());//其他补助
        finance.setTime1(time1);
        finance.setTime2(time2);
        finance.setName(name);
//        教师薪资=基础薪资+（课时费*课时）+（配额课时*配额课时费）+薪资补助+其他补助
        finance.setMoney5(finance.getBase()+
                (finance.getMoney1()*finance.getTime1())+
                (finance.getMoney2()*finance.getTime2())
                +finance.getMoney3()+finance.getMoney4());
        int n = financeService.addFinance(finance);
        if(n!=0)
        {
            flag = true;
        }
        return flag;
    }











//批量导入数据信息
    @ResponseBody
    @RequestMapping(value = "/batchimport", method = RequestMethod.POST)
    public Object batchimport(@RequestParam(value = "filename", required = false) MultipartFile file,
                              HttpServletRequest request)throws Exception {
        log.info("AddController ..batchimport() start");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map resultMap = new HashMap();
        //判断文件是否为空
        if (file == null) return null;
        //获取文件名
        String name = file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size = file.getSize();
        if (name == null || ("").equals(name) && size == 0) return null;

        //批量导入。参数：文件名，文件。
        boolean b = batchImport(name, file);
        if (b) {
            resultMap.put("status", ResultStatus.RESULT_STATUS_SUCCESS);
            MultipartFile file1 = multipartRequest.getFile("filename");
            System.out.println("通过传统方式form表单提交方式导入excel文件！");
            InputStream in =null;
            List<List<Object>> listob = null;
            if(file.isEmpty()){
                throw new Exception("文件不存在！");
            }
            in = file.getInputStream();
            listob = ImportExcleUtil.getBankListByExcel(in,file.getOriginalFilename());
            for (int i = 0; i < listob.size(); i++) {
                List<Object> lo = listob.get(i);
                String n = String.valueOf(lo.get(0));//姓名
                String u = String.valueOf(lo.get(1));//课时
                String m = String.valueOf(lo.get(2));//配额课时
                double t1 = Float.parseFloat(u);
                double t2 = Float.parseFloat(m);
                boolean flag = insert(n,t1,t2);
                if(!flag){
                    resultMap.put("status", ResultStatus.RESULT_STATUS_FAILURE);
                    resultMap.put("error_msg", "导入失败！请先检查导入的数据是否符合数据规范(姓名"+n+"存在否？)！");
                }
            }
        } else {
            resultMap.put("status", ResultStatus.RESULT_STATUS_FAILURE);
            resultMap.put("error_msg", "导入失败！请先检查导入的数据是否符合数据规范！");
        }
        return resultMap;
    }

    public boolean batchImport(String name, MultipartFile file) {
        try {
            boolean b = bulkimport(name, file);
            return b;
        } catch (Exception e) {
            Logger.getLogger(FinanceController.class).error(e.getMessage());
            return false;
        }
    }

//判断传过来的文件部位空
public boolean bulkimport(String name, MultipartFile file)
    {
        boolean flag = false;
        if(name!=null && file!=null)
        {
            flag = true;
        }
        return flag;
    }





}




