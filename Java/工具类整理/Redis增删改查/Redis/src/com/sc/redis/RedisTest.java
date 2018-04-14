package com.sc.redis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * @author Mr.薛
 * 时间：
 * 内容：Redis 缓存Demo
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
		Jedis  jr = new Jedis("192.168.1.26",6379); //redis服务地址和端口号
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
		System.out.println("连接成功，服务正在运行..."+jr.ping());
	}
	
	public void Redis2()
	{
		Jedis jr = new Jedis("127.0.0.1");
		System.out.println("连接成功，服务正在运行...");
		//设置 redis 字符串数据
        jr.set("key", "redis缓存数据");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jr.get("key"));
	}
	
	public void Redis3()
	{
		Jedis jr = new Jedis("127.0.0.1");
		System.out.println("连接成功，服务正在运行...");
		//存储数据到列表中
        jr.lpush("site-list", "Runoob");
        jr.lpush("site-list", "Google");
        jr.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = jr.lrange("site-list", 0 ,2);
        for(int i=0; i<list.size(); i++) 
        {
            System.out.println("列表项为: "+list.get(i));
        }
	}
	
	
	
	public void Redis4()
	{
		Jedis jr = new Jedis("127.0.0.1");
		System.out.println("连接成功，服务正在运行...");

        // 获取数据并输出
        Set<String> keys = jr.keys("*"); 
        Iterator<String> it=keys.iterator() ;   
        while(it.hasNext())
        {   
            String key = it.next();   
            System.out.println("key中的数据："+key);   
        }
        System.out.println("-------以前存储的数据"+jr.get("name"));
	}
	
	public void Redis5()
	{
		Jedis jr = new Jedis();
		jr.set("key001", "数据1");
		System.out.println(jr.get("key001"));
		jr.incr("key002");
		System.out.println(jr.get("key002")+"   "+jr.ping());	
		//存储数据到列表中
        jr.lpush("site-list", "Runoob");
        jr.lpush("site-list", "Google");
        jr.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = jr.lrange("site-list", 0 ,2);
        for(int i=0; i<list.size(); i++) 
        {
            System.out.println("列表项为: "+list.get(i));
        }
        
        jr.append("key003", "追加数据3");
        
        Set<String> keys = jr.keys("*");
        Iterator<String> it = keys.iterator();
        while(it.hasNext()){
        	String key = it.next();
        	System.out.println("key值 "+key);
        }
        System.out.println("-------以前存储的数据"+jr.get("key003"));
        
        
        
        
	}
	
	
}
