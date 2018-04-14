package interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class StudentInterceptor implements Interceptor{

	public void intercept(ActionInvocation ai) {
		System.out.println("====================拦截前（可做判断）====================");
//		这里可以写一个拦截器
		ai.invoke();
		System.out.println("方法名：  "+ai.getMethodName()+"      方法：  "+ai.getMethod());
		System.out.println("====================拦截后（方法处理）====================");
	}

}
