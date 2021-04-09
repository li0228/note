import com.lhh.HelloSpring;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lihonghao
 * @date 2021/4/3 17:37
 */
public class MyTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		// 我们的对象都在spring中，我们要使用，直接去里面拿
		HelloSpring helloSpring = (HelloSpring)context.getBean("nijljkjk");
		System.out.println(helloSpring.getName());
	}
}
