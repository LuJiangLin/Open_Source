package model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Model;

import interceptor.StudentInterceptor;

@SuppressWarnings("serial")
public class Classes extends Model<Classes>{

	public static final Classes dao=new Classes();
}
