package com.sc.redis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * @author Mr.Ѧ
 * ʱ�䣺
 * ���ݣ�Redis ����Demo
 */
public class RedisTest {

	public static void main(String[] args) {
//		new RedisTest().RedisDemo();
//		new RedisTest().Redis1();
//		new RedisTest().Redis2();
//		new RedisTest().Redis3();
//		new RedisTest().Redis4();
		new RedisTest().Redis5();
	}
	
	public void RedisDemo(){
		Jedis  jr = new Jedis("192.168.1.26",6379); //redis�����ַ�Ͷ˿ں�
        String key = "mKey";
        jr.set(key, "hello,redis!");
        String v = new String(jr.get(key));
        String k2 = "count";
        jr.incr(k2);
        jr.incr(k2);
        System.out.println(v);
        System.out.println(new String(jr.get(k2)));
		
	}
	
	public void Redis1()
	{
		Jedis jr = new Jedis("127.0.0.1");
		System.out.println("���ӳɹ���������������..."+jr.ping());
	}
	
	public void Redis2()
	{
		Jedis jr = new Jedis("127.0.0.1");
		System.out.println("���ӳɹ���������������...");
		//���� redis �ַ�������
        jr.set("key", "redis��������");
        // ��ȡ�洢�����ݲ����
        System.out.println("redis �洢���ַ���Ϊ: "+ jr.get("key"));
	}
	
	public void Redis3()
	{
		Jedis jr = new Jedis("127.0.0.1");
		System.out.println("���ӳɹ���������������...");
		//�洢���ݵ��б���
        jr.lpush("site-list", "Runoob");
        jr.lpush("site-list", "Google");
        jr.lpush("site-list", "Taobao");
        // ��ȡ�洢�����ݲ����
        List<String> list = jr.lrange("site-list", 0 ,2);
        for(int i=0; i<list.size(); i++) 
        {
            System.out.println("�б���Ϊ: "+list.get(i));
        }
	}
	
	
	
	public void Redis4()
	{
		Jedis jr = new Jedis("127.0.0.1");
		System.out.println("���ӳɹ���������������...");

        // ��ȡ���ݲ����
        Set<String> keys = jr.keys("*"); 
        Iterator<String> it=keys.iterator() ;   
        while(it.hasNext())
        {   
            String key = it.next();   
            System.out.println("key�е����ݣ�"+key);   
        }
        System.out.println("-------��ǰ�洢������"+jr.get("name"));
	}
	
	public void Redis5()
	{
		Jedis jr = new Jedis();
		jr.set("key001", "����1");
		System.out.println(jr.get("key001"));
		jr.incr("key002");
		System.out.println(jr.get("key002")+"   "+jr.ping());	
		//�洢���ݵ��б���
        jr.lpush("site-list", "Runoob");
        jr.lpush("site-list", "Google");
        jr.lpush("site-list", "Taobao");
        // ��ȡ�洢�����ݲ����
        List<String> list = jr.lrange("site-list", 0 ,2);
        for(int i=0; i<list.size(); i++) 
        {
            System.out.println("�б���Ϊ: "+list.get(i));
        }
        
        jr.append("key003", "׷������3");
        
        Set<String> keys = jr.keys("*");
        Iterator<String> it = keys.iterator();
        while(it.hasNext()){
        	String key = it.next();
        	System.out.println("keyֵ "+key);
        }
        System.out.println("-------��ǰ�洢������"+jr.get("key003"));
        
        
        
        
	}
	
	
}
