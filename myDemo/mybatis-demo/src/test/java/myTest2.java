import com.lhh.dao.UserDao;
import com.lhh.pojo.User;
import com.lhh.utils.MybatisUtil;
import com.lhh.vo.QueryParam;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihonghao
 * @date 2021/4/14 12:03
 */
public class myTest2 {
	@Test
	public void test(){
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		String sqlId = "com.lhh.dao.UserDao"+"."+"getUsers";
		 UserDao dao = sqlSession.getMapper(UserDao.class);
//		QueryParam queryParam  = new QueryParam();
//		queryParam.setParamId(5);
//		List<User> userList = dao.getUserById(queryParam);
//		for (User user : userList) {
//			System.out.println(user.getName());
//		}

		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(4);
		list.add(5);
		List<User> userByIds = dao.getUserByIds(list);
		for (User user : userByIds) {
			System.out.println(user.getName());
		}

		sqlSession.close();

	}
}
