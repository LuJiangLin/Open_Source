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
//		���ϲ�ѯ
		List<Student> list=Student.dao.find("select student.studentname,student.studentid,student.studentage,student.studentsex,classes.classesname from student,classes where student.classesid=classes.classesid");
		setAttr("studentList", list);
		render("/index.jsp");
	}
	
	public void add(){
//		��ӵ�ʱ���ѯһ�°༶���еİ༶��Ϣ���༶id���û����а༶id�������
		List<Classes> list=Classes.dao.find("select * from classes");
//		ת����ǰ̨ҳ��
		setAttr("stuList", list);
		render("add.jsp");
	}
	
	public void delete(){
		 // ��ȡ������ΪstudentID��ֵ
		//Student.dao.deleteById(getPara("studentid"));
        // Student.dao.deleteById(getPara("studentID"));
        // ��ȡurl�����е�һ��ֵ
		Student.dao.deleteById(getParaToInt());
		redirect("/student");
	}
	
	public void update(){
		Student student=getModel(Student.class);
		student.update();
		//forwardAction("/student");//ʹ��forwardActionֻ�Ǻ����ת�����Ӵ�action��ת��ָ����action��������ַ�������ӻ���ָ��Ĵ�action
		redirect("/student");//ʹ��redirect������Ӧ�����һ�����������ٷ���һ���µ�����
	}
	
	public void get(){
//		����������ѯ
		Student student=Student.dao.findById(getParaToInt());
		setAttr("student", student);
		render("update.jsp");
	}
	
	@Before(StudentValidator.class)
	public void save(){
		//�����е�����ת��student��������ڶ�������student����ǰ����е�student�����Ը������֣���ȻҪ��ǰ�����ı���һ��
		Student student=getModel(Student.class,"student");
		if(Ognl.isNotEmpty(student.getStr("studentname")) && Ognl.isNotEmpty(student.getInt("classesid")) && Ognl.isNotEmpty(student.getInt("studentage")))
		{
			boolean flag = student.save();
			if(flag){
				redirect("/student");
			}else
			{
				renderText("�����Ϣ����");
			}
		}else
		{
			renderText("��Ϣ����Ϊ�գ�");
		}
		
		
//		student.set("studentid", "mysequence,nextval").save();//id��������ʹ��
//		forwardAction("/student");
	}
}
