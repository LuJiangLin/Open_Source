package demo;

import interceptor.StudentInterceptor;

import java.util.List;

import validator.StudentValidator;
import model.Classes;
import model.Student;
import utils.Ognl;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class StudentController extends Controller{

	@Before(StudentInterceptor.class)
	public void index(){
		//renderText("");
		//List<Student> list=Student.dao.find("select * from student");
//		复合查询
		List<Student> list=Student.dao.find("select student.studentname,student.studentid,student.studentage,student.studentsex,classes.classesname from student,classes where student.classesid=classes.classesid");
		setAttr("studentList", list);
		render("/index.jsp");
	}
	
	public void add(){
//		添加的时候查询一下班级表中的班级信息（班级id是用户表中班级id的外键）
		List<Classes> list=Classes.dao.find("select * from classes");
//		转发到前台页面
		setAttr("stuList", list);
		render("add.jsp");
	}
	
	public void delete(){
		 // 获取表单域名为studentID的值
		//Student.dao.deleteById(getPara("studentid"));
        // Student.dao.deleteById(getPara("studentID"));
        // 获取url请求中第一个值
		Student.dao.deleteById(getParaToInt());
		redirect("/student");
	}
	
	public void update(){
		Student student=getModel(Student.class);
		student.update();
		//forwardAction("/student");//使用forwardAction只是后端跳转，及从此action跳转到指定的action，这样地址栏的链接还是指向的此action
		redirect("/student");//使用redirect是先响应浏览器一个链接让其再发起一个新的请求
	}
	
	public void get(){
//		根据主键查询
		Student student=Student.dao.findById(getParaToInt());
		setAttr("student", student);
		render("update.jsp");
	}
	
	@Before(StudentValidator.class)
	public void save(){
		//将表单中的数据转成student对象，这里第二个参数student就是前面表单中的student，可以更换名字，当然要跟前面表单里的保持一致
		Student student=getModel(Student.class,"student");
		if(Ognl.isNotEmpty(student.getStr("studentname")) && Ognl.isNotEmpty(student.getInt("classesid")) && Ognl.isNotEmpty(student.getInt("studentage")))
		{
			boolean flag = student.save();
			if(flag){
				redirect("/student");
			}else
			{
				renderText("添加信息错误！");
			}
		}else
		{
			renderText("信息不能为空！");
		}
		
		
//		student.set("studentid", "mysequence,nextval").save();//id非自增长使用
//		forwardAction("/student");
	}
}
