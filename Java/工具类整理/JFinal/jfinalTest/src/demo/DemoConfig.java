package demo;
import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

import model.Classes;
import model.Student;  
public class DemoConfig extends JFinalConfig {  
	
//	����������Ϣ
    public void configConstant(Constants constants) {  
//    	PropKit.use("jdbc.txt"); // ���ݿ������ļ����������ܷŵ����ﶼ���ԣ�û��·��
    	
    	constants.setDevMode(true);
//    	�����ʽ
    	constants.setEncoding("utf-8");
    	constants.setViewType(ViewType.JSP);// Ĭ����freemark
//    	����ҳ���������Ϣ
//    	constants.setError401View("401.html");
//    	constants.setError403View("403.html");
    	constants.setError404View("404.html");
    	constants.setError500View("500.html");
    }  
    
//    ���·�ɵ�����
    public void configRoute(Routes routes) {  
//    	����·��1
    	routes.add("/index", HelloController.class);
//		routes.add("/user", UserController.class,"/");
		//me.add("/", StudentController.class);
    	routes.add("/student", StudentController.class);
    	routes.add("/classes", ClassesController.class);	
    }  
    
//    ���ݿ�����
    public void configPlugin(Plugins plugins) {
    	C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://192.168.1.26:3306/test", "root", "test");
    	plugins.add(cp);
//    	����ActiveRecord���
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		plugins.add(arp);
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		arp.addMapping("student", "studentid", Student.class);//ӳ��
        arp.addMapping("classes", "classesid", Classes.class);
    }  
//    ����������
    public void configInterceptor(Interceptors interceptors) {}  
//    ���ô�����
    public void configHandler(Handlers handlers) {}  
    
    
    
}