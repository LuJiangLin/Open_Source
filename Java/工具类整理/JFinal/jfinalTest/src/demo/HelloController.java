package demo;  
    import com.jfinal.core.Controller;  
    public class HelloController extends Controller {  
        public void index() {  
           renderText("Hello JFinal World.你好，世界！我来帮你测试一下！");  
//           render("index.jsp");
        }  
        
        public void page()
        {
        	System.out.println("进来了...");
        	render("index.jsp");
        }
    }  