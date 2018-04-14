package demo;

import com.jfinal.core.Controller;

public class UserController extends Controller {

	public void index(){
		String hhh="成功";
		renderText("Hello JFinal World.");
		setAttr("ceshi", hhh);
		redirect("user.jsp");
	}
}
