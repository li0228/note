import com.lhh.dao.UserDao;
import com.lhh.pojo.User;
import com.lhh.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author lihonghao
 * @date 2021/4/12 20:44
 */
public class UserTest {

	@Autowired
	private UserService userService;

	@Test
	public void test(){
		List<User> users = userService.getUsers();
		System.out.println(users);
	}
}
