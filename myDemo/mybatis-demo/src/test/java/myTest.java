import com.lhh.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author lihonghao
 * @date 2021/4/13 19:27
 */
public class myTest {
	@Test
	public void test01() throws IOException {
		String config = "mybatis-conf.xml";
		InputStream inputStream = Resources.getResourceAsStream(config);
		SqlSessionFactoryBuilder builder  = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(inputStream);

		// 获取sqlsession对象
		SqlSession sqlSession = factory.openSession();
		String sqlId = "com.lhh.dao"+"."+"getUsers";
		List<User> userList = sqlSession.selectList(sqlId);
		for (User user : userList) {
			System.out.println(user.getName());
		}

	}
}
