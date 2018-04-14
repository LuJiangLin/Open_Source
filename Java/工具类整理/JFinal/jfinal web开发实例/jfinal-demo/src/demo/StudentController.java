package demo;

import interceptor.StudentInterceptor;

import java.util.List;

import validator.StudentValidator;
import model.Student;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class StudentController extends Controller{

	@Before(StudentInterceptor.class)
	public void index(){
		//renderText("");
		//List<Student> list=Student.dao.find("select * from student");
		List<Student> list=Student.dao.find("select student.studentname,student.studentid,student.studentage,student.studentsex,classes.classesname from student,classes where student.classesid=classes.classesid");
		setAttr("studentList", list);
		render("/index.jsp");
	}
	
	public void add(){
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
		Student student=Student.dao.findById(getParaToInt());
		setAttr("student", student);
		render("index2.jsp");
	}
	
	@Before(StudentValidator.class)
	public void save(){
		Student student=getModel(Student.class);
		student.save();
		redirect("/student");
		//student.set("studentid", "mysequence,nextval").save();
		//forwardAction("/student");
	}
}
