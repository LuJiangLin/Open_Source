package com.anssy.utils;
/*
 * 文 件 名:  MSWordPoi4.java
 * 版    权:  Sunny Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Mr.薛
 * 修改时间:  2017-9-21
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 *
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

public class MSWordPoi4
{
    
    /**
    * @param args
    */
    public static void main(String[] args)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("${t1}", "XXX有限公司");//供方名称
        map.put("${t2}", "XXX集团");//需方名称
        map.put("${t3}", "XRFGB/JK4556");//合同号
        map.put("${t4}", "覆膜胶合板");//品名
        map.put("${t5}", "1220X2440X18MM");//规格
        map.put("${t6}", "1220X2440X18MM");//规格-总计
        map.put("${t7}", "4730");//数量
        map.put("${t8}", "4730");//数量-总计
        map.put("${t9}", "253.44");//体积
        map.put("${t10}", "253.44");//体积-总计
        map.put("${t11}", "101.00");//单价
        map.put("${t12}", "101.00");//单价-总计
        map.put("${t13}", "477730.00");//总价
        map.put("${t14}", "477730.00");//总价-总计
        map.put("${t15}", new MoneyUtil().toChinese("477730.00"));//合计人民币(大写)
        map.put("${t16}", "18 MM");//备注:
        map.put("${t17}", "110");//共计
        map.put("${t18}", "2017年9月22日");//时间
        map.put("${t19}", "连云港码头");//送货地
        map.put("${t20}", "汽运");//运输方式
        map.put("${t21}", "2017年9月20日");//供方日期
        map.put("${t22}", "2017年9月21日");//需方日期
//        模板路径-读取服务器路径
        String srcPath = "F:\\购货合同.doc";
        readwriteWord(srcPath, map);
    }
    
    /**
    * 实现对word读取和修改操作
    * 
    * @param filePath
    *            word模板路径和名称
    * @param map
    *            待填充的数据，从数据库读取
    */
    public static void readwriteWord(String filePath, Map<String, String> map)
    {
        // 读取word模板
//         String fileDir = new
//         File(base.getFile(),"http://www.cnblogs.com/http://www.cnblogs.com/../doc/").getCanonicalPath();
        FileInputStream in = null;
        try
        {
            in = new FileInputStream(new File(filePath));
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }
        HWPFDocument hdt = null;
        try
        {
            hdt = new HWPFDocument(in);
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        Fields fields = hdt.getFields();
        Iterator<Field> it = fields.getFields(FieldsDocumentPart.MAIN).iterator();
//        while (it.hasNext())
//        {
//            System.out.println(it.next().getType());
//        }
        
        //读取word文本内容
        Range range = hdt.getRange();
        TableIterator tableIt = new TableIterator(range); 
        //迭代文档中的表格
        int ii = 0;
        while (tableIt.hasNext()) {  
            Table tb = (Table) tableIt.next();  
            ii++;
            //迭代行，默认从0开始
            for (int i = 0; i < tb.numRows(); i++) {  
                TableRow tr = tb.getRow(i);  
                //只读前8行，标题部分
                if(i >=8) break;
                //迭代列，默认从0开始
                for (int j = 0; j < tr.numCells(); j++) {  
                    TableCell td = tr.getCell(j);//取得单元格
                    //取得单元格的内容
                    for(int k=0;k<td.numParagraphs();k++){  
                        Paragraph para =td.getParagraph(k);  
                        String s = para.text();  
                    } //end for   
                }   //end for
            }   //end for
        } //end while
        //System.out.println(range.text());
        
        // 替换文本内容
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            range.replaceText(entry.getKey(), entry.getValue());
        }
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        String fileName = "购货合同_"+ System.currentTimeMillis();
        fileName += ".doc";
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream("F:\\" +fileName, true);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {
            hdt.write(ostream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // 输出字节流
        try
        {
            out.write(ostream.toByteArray());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            ostream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}