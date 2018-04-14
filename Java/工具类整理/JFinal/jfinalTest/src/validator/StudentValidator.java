package validator;


import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

import model.Classes;

public class StudentValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		 //��֤����name��������Ϣkey,������Ϣvalue
		validateRequiredString("student.studentname", "studentnameMsg", "������ѧ�����ƣ�");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("student.studentname");//���ύ��ֵ�ٴ���ҳ���Ա㱣��ԭ�������ֵ
		List<Classes> list=Classes.dao.find("select * from classes");
//		ת����ǰ̨ҳ��
		c.setAttr("stuList", list);
		c.render("add.jsp");
	}

}
