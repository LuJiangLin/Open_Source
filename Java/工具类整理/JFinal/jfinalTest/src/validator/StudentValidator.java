package validator;


import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

import model.Classes;

public class StudentValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		 //验证表单域name，返回信息key,返回信息value
		validateRequiredString("student.studentname", "studentnameMsg", "请输入学生名称！");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("student.studentname");//将提交的值再传回页面以便保持原先输入的值
		List<Classes> list=Classes.dao.find("select * from classes");
//		转发到前台页面
		c.setAttr("stuList", list);
		c.render("add.jsp");
	}

}
