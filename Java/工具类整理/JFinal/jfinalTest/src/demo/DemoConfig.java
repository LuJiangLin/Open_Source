package demo;
import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

import model.Classes;
import model.Student;  
public class DemoConfig extends JFinalConfig {  
	
//	调用配置信息
    public void configConstant(Constants constants) {  
//    	PropKit.use("jdbc.txt"); // 数据库配置文件，发觉不管放到哪里都可以，没有路径
    	
    	constants.setDevMode(true);
//    	编码格式
    	constants.setEncoding("utf-8");
    	constants.setViewType(ViewType.JSP);// 默认是freemark
//    	错误页面的配置信息
//    	constants.setError401View("401.html");
//    	constants.setError403View("403.html");
    	constants.setError404View("404.html");
    	constants.setError500View("500.html");
    }  
    
//    添加路由的配置
    public void configRoute(Routes routes) {  
//    	配置路由1
    	routes.add("/index", HelloController.class);
//		routes.add("/user", UserController.class,"/");
		//me.add("/", StudentController.class);
    	routes.add("/student", StudentController.class);
    	routes.add("/classes", ClassesController.class);	
    }  
    
//    数据库连接
    public void configPlugin(Plugins plugins) {
    	C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://192.168.1.26:3306/test", "root", "test");
    	plugins.add(cp);
//    	配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		plugins.add(arp);
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		arp.addMapping("student", "studentid", Student.class);//映射
        arp.addMapping("classes", "classesid", Classes.class);
    }  
//    配置拦截器
    public void configInterceptor(Interceptors interceptors) {}  
//    配置处理器
    public void configHandler(Handlers handlers) {}  
    
    
    
}