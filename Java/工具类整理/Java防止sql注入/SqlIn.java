package com.anssy.ognl;

/**
 * @author Mr.Ѧ
 *  Java��ֹsqlע��
 */
public class SqlIn {

	public static void main(String[] args) {
		String str = "script-8iashdansdtasdsql select * from sys_user";
		System.out.println("ԭʼ�ַ���"+str+"      ����֮����ַ�����"+new SqlIn().filterSQLInjection(str));
	}
	
	public final static String filterSQLInjection(String s) {
	    if (s == null || "".equals(s)) {
	        return "";
	    }
	    try {
	        s = s.trim().replaceAll("</?[s,S][c,C][r,R][i,I][p,P][t,T]>?", "");//script
	        s = s.trim().replaceAll("[a,A][l,L][e,E][r,R][t,T]\\(", "").replace("\"", "");// alert
	        s = s.trim().replace("\\.swf", "").replaceAll("\\.htc", "");
	        s = s.trim().replace("\\.php\\b", "").replaceAll("\\.asp\\b", "");
	        s = s.trim().replace("document\\.", "").replaceAll("[e,E][v,V][a,A][l,L]\\(", "");
	        s = s.trim().replaceAll("'", "").replaceAll(">", "");
	        s = s.trim().replaceAll("<", "").replaceAll("=", "");
	        s = s.trim().replaceAll(" [o,O][r,R]", "");
	        s = s.trim().replaceAll("etc/", "").replaceAll("cat ", "");
	        s = s.trim().replaceAll("/passwd ", "");
	        s = s.trim().replaceAll("sleep\\(", "").replaceAll("limit ", "").replaceAll("LIMIT ", "");
	        s = s.trim().replaceAll("[d,D][e,E][l,L][e,E][t,T][e,E] ", "");// delete
	        s = s.trim().replaceAll("[s,S][e,E][l,L][e,E][c,C][t,T] ", "");// select;
	        s = s.trim().replaceAll("[u,U][p,P][d,D][a,A][t,T][e,E] ", "");// update
	        s = s.trim().replaceAll("[d,D][e,E][l,L][a,A][y,Y] ", "").replaceAll("waitfor ", "");
	        s = s.trim().replaceAll("print\\(", "").replaceAll("md5\\(", "");
	        s = s.trim().replaceAll("cookie\\(", "").replaceAll("send\\(", "");
	        s = s.trim().replaceAll("response\\.", "").replaceAll("write\\(", "")
	                .replaceAll("&", "");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "";
	    }
	    return s;
	} 
	
	
}
