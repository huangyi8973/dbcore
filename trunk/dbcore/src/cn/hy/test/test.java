package cn.hy.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.hy.db.BaseDao;
import cn.hy.utils.MD5Util;
import cn.hy.vo.IBaseVO;
import cn.hy.vo.UserVO;


public class test {
	
	public static void main(String[] args) throws SQLException, IllegalAccessException, Exception{
		
//		UserVO vo1=new UserVO();
//		vo1.setVusername("aaa");
//		vo1.setVusercode("aaa");
//		vo1.setVpassword(MD5Util.Parse("aaa"));
//		UserVO vo2=new UserVO();
//		vo2.setVusername("bbb");
//		vo2.setVusercode("bbb");
//		vo2.setVpassword(MD5Util.Parse("bbb"));
//		List<IBaseVO> userList=new ArrayList<IBaseVO>();
//		userList.add(vo1);
//		userList.add(vo2);
//		BaseDao.insertVoList(userList);
//		BaseDao.deleteByCondition(UserVO.class, "pk_user in('b249956f0d82463c85faf3564464a162','a6ee1dad63a84dcd92cdcb13aa855f3d')");
//		deleteAll();
		addUser();
		select();
	}
	public static void select() throws SQLException, IllegalAccessException, Exception{
		List<IBaseVO> vos=BaseDao.queryVos(UserVO.class);
		for(IBaseVO vo:vos){
			System.out.println(String.format("pk:%s\tvusercode:%s\tvusername:%s\tvpassword:%s", vo.getValue("pk_user"),vo.getValue("vusercode"),vo.getValue("vusername"),vo.getValue("vpassword")));
		}
	}
	public static void deleteAll(){
		try {
			BaseDao.deleteByCondition(UserVO.class, "pk_user is not null");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void addUser() throws InstantiationException, IllegalAccessException, SQLException{
		List<IBaseVO> list = new ArrayList<IBaseVO>();
		UserVO user3=new UserVO();
		user3.setVusername("王五");
		user3.setVusercode("wangwu");
		user3.setVpassword(MD5Util.Parse("wangwu"));
		UserVO user1=new UserVO();
		user1.setVusername("张三");
		user1.setVusercode("zhangsan");
		user1.setVpassword(MD5Util.Parse("zhangsan"));
		UserVO user2=new UserVO();
		user2.setVusername("李四");
		user2.setVusercode("lisi");
		user2.setVpassword(MD5Util.Parse("lisi"));
		list.add(user1);
		list.add(user2);
		list.add(user3);
		BaseDao.insertVoList(list);
	}
}
