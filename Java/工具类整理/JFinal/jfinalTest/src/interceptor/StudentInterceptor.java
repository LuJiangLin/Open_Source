package interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class StudentInterceptor implements Interceptor{

	public void intercept(ActionInvocation ai) {
		System.out.println("====================����ǰ�������жϣ�====================");
//		�������дһ��������
		ai.invoke();
		System.out.println("��������  "+ai.getMethodName()+"      ������  "+ai.getMethod());
		System.out.println("====================���غ󣨷�������====================");
	}

}
