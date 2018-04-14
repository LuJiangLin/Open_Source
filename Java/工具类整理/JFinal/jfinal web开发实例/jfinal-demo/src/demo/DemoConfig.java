package demo;

import model.Classes;
import model.Student;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

public class DemoConfig extends JFinalConfig{
	
	public void configConstant(Constants me){
		me.setDevMode(true);
	    me.setViewType(ViewType.JSP);
	}


	public void configRoute(Routes me) {
//		me.add("/hello", HelloController.class);
//		me.add("/user", UserController.class,"/");
		//me.add("/", StudentController.class);
        me.add("/student", StudentController.class);
        me.add("/classes", ClassesController.class);
	}

	public void configPlugin(Plugins me) {
		
		C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://192.168.1.2:3306/testschool", "root", "123456");
		me.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		me.add(arp);
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		arp.addMapping("student", "studentid", Student.class);
        arp.addMapping("classes", "classesid", Classes.class);
	}

	public void configInterceptor(Interceptors me) {}

	public void configHandler(Handlers me) {}

}
