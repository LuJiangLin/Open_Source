package demo;  
    import com.jfinal.core.Controller;  
    public class HelloController extends Controller {  
        public void index() {  
           renderText("Hello JFinal World.��ã����磡�����������һ�£�");  
//           render("index.jsp");
        }  
        
        public void page()
        {
        	System.out.println("������...");
        	render("index.jsp");
        }
    }  