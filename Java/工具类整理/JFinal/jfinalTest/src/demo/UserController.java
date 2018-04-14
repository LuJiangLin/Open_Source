package demo;

import com.jfinal.core.Controller;

public class UserController extends Controller {

	public void index(){
		String hhh="³É¹¦";
		renderText("Hello JFinal World.");
		setAttr("ceshi", hhh);
		redirect("user.jsp");
	}
}
