package com.anssy.utils;
/*
 * �� �� ��:  MSWordPoi4.java
 * ��    Ȩ:  Sunny Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * ��    ��:  <����>
 * �� �� ��:  Mr.Ѧ
 * �޸�ʱ��:  2017-9-21
 * ���ٵ���:  <���ٵ���>
 * �޸ĵ���:  <�޸ĵ���>
 * �޸�����:  <�޸�����>
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
        map.put("${t1}", "XXX���޹�˾");//��������
        map.put("${t2}", "XXX����");//�跽����
        map.put("${t3}", "XRFGB/JK4556");//��ͬ��
        map.put("${t4}", "��Ĥ���ϰ�");//Ʒ��
        map.put("${t5}", "1220X2440X18MM");//���
        map.put("${t6}", "1220X2440X18MM");//���-�ܼ�
        map.put("${t7}", "4730");//����
        map.put("${t8}", "4730");//����-�ܼ�
        map.put("${t9}", "253.44");//���
        map.put("${t10}", "253.44");//���-�ܼ�
        map.put("${t11}", "101.00");//����
        map.put("${t12}", "101.00");//����-�ܼ�
        map.put("${t13}", "477730.00");//�ܼ�
        map.put("${t14}", "477730.00");//�ܼ�-�ܼ�
        map.put("${t15}", new MoneyUtil().toChinese("477730.00"));//�ϼ������(��д)
        map.put("${t16}", "18 MM");//��ע:
        map.put("${t17}", "110");//����
        map.put("${t18}", "2017��9��22��");//ʱ��
        map.put("${t19}", "���Ƹ���ͷ");//�ͻ���
        map.put("${t20}", "����");//���䷽ʽ
        map.put("${t21}", "2017��9��20��");//��������
        map.put("${t22}", "2017��9��21��");//�跽����
//        ģ��·��-��ȡ������·��
        String srcPath = "F:\\������ͬ.doc";
        readwriteWord(srcPath, map);
    }
    
    /**
    * ʵ�ֶ�word��ȡ���޸Ĳ���
    * 
    * @param filePath
    *            wordģ��·��������
    * @param map
    *            ���������ݣ������ݿ��ȡ
    */
    public static void readwriteWord(String filePath, Map<String, String> map)
    {
        // ��ȡwordģ��
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
        
        //��ȡword�ı�����
        Range range = hdt.getRange();
        TableIterator tableIt = new TableIterator(range); 
        //�����ĵ��еı��
        int ii = 0;
        while (tableIt.hasNext()) {  
            Table tb = (Table) tableIt.next();  
            ii++;
            //�����У�Ĭ�ϴ�0��ʼ
            for (int i = 0; i < tb.numRows(); i++) {  
                TableRow tr = tb.getRow(i);  
                //ֻ��ǰ8�У����ⲿ��
                if(i >=8) break;
                //�����У�Ĭ�ϴ�0��ʼ
                for (int j = 0; j < tr.numCells(); j++) {  
                    TableCell td = tr.getCell(j);//ȡ�õ�Ԫ��
                    //ȡ�õ�Ԫ�������
                    for(int k=0;k<td.numParagraphs();k++){  
                        Paragraph para =td.getParagraph(k);  
                        String s = para.text();  
                    } //end for   
                }   //end for
            }   //end for
        } //end while
        //System.out.println(range.text());
        
        // �滻�ı�����
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            range.replaceText(entry.getKey(), entry.getValue());
        }
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        String fileName = "������ͬ_"+ System.currentTimeMillis();
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
        // ����ֽ���
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