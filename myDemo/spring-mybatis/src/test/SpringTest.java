import com.lhh.dao.UserDao;
import com.lhh.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author lihonghao
 * @date 2021/4/14 19:43
 */
@RunWith(SpringRunner.class)
@SpringJUnitConfig(locations = {"classpath:ApplicationContext.xml"})
public class SpringTest {
	@Autowired(required = false)
	private UserDao userDao;
	@Autowired
	private User user;
	@Test
	public void test01(){
		List<User> users = userDao.getUsers();
		for (User user : users) {
			System.out.println(user.getName());
		}
	}
	@Test
	public void test02(){
		System.out.println(user.getAge());
	}
}
