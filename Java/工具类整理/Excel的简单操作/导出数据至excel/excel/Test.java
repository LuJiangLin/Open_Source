package com.anssy.excel;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		PoiExcelExport pee = new PoiExcelExport("F:/test.xls","sheet1");
		//����
        List<Man> dataList = new ArrayList();
        Man man1 = new Man("����",20,"��",(float)10000.8);
        Man man2 = new Man("����",21,"��",(float)11000.8);
        Man man3 = new Man("����",22,"Ů",(float)1200.8);
        Man man4 = new Man("����",23,"��",(float)13000.8);
        Man man5 = new Man("����",24,"��",(float)14000.8);
        Man man6 = new Man();
        man6.setName("�ϰ�");
        dataList.add(man1);dataList.add(man2);dataList.add(man3);dataList.add(man4);dataList.add(man5);
        dataList.add(man6);
        //����
        String titleColumn[] = {"name","sex","idCard","salary",""};
        String titleName[] = {"����","�Ա�","���֤��","��н","��н"};
        int titleSize[] = {13,13,13,13,13};
        //�������� set������ȫ������
        String colFormula[] = new String[5];
        colFormula[4] = "D@*12";   //���õ�5�еĹ�ʽ
        pee.setColFormula(colFormula);
        pee.setAddress("A:D");  //�Զ�ɸѡ 
        
        pee.wirteExcel(titleColumn, titleName, titleSize, dataList);
	}

}
