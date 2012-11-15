package cn.hy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.hy.db.sql.SqlHelper;
import cn.hy.vo.IBaseVO;
import cn.hy.vo.UserVO;

/**
 * 数据库连接
 * 
 * @version V5.0
 * @author huangy
 * @date 2012-11-15
 */
public class BaseDao {

	// 只在第一次加载类的时候加载驱动
	static {
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// 0.准备动作
		String dbUrl = "jdbc:db2://127.0.0.1:50000/cms";
		String dbUserName = "huangy";
		String dbPassword = "xmjmhy";
		Connection conn = null;
		PreparedStatement psta = null;
		ResultSet rs = null;
		// 1.建立连接
		try {
			conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
			// String sql =
			// "insert into s_user(pk_user,vusercode,vusername,vpassword) values(?,?,?,?)";
			
			// psta = conn.prepareStatement(sql);
			// UUID pk = UUID.randomUUID();
			// String strPk = pk.toString().replaceAll("-", "");
			// psta.setString(1, strPk);
			// psta.setString(2, "jimmy");
			// psta.setString(3, "大嘴");
			// psta.setString(4, MD5Util.Parse("1"));
			// System.out.println(psta.executeUpdate());
			IBaseVO vo=new UserVO();
			String sql = SqlHelper.getSelectSql(vo);
			psta = conn.prepareStatement(sql);
			rs = psta.executeQuery();
			//把数据填充到VO里去
			ResultSetMetaData meta = rs.getMetaData();
//			for (int i = 1; i <=meta.getColumnCount(); i++) {
//				System.out.println(meta.getColumnTypeName(i));
//			}
			List<IBaseVO> voList=new ArrayList<IBaseVO>();
			while(rs.next()){
				UserVO vo=new UserVO();
				for(int i=1;i<=meta.getColumnCount();i++){
					//获取字段，填充到VO
					vo.
				}
				for(int c=1;c<=meta.getColumnCount();c++)
				System.out.println(rs.getObject(c));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				//关闭记录集
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				//关闭PreparedStatement
				try {
					if (psta != null)
						psta.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					//关闭连接
					if (conn != null) {
						try {
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}

					}
				}
			}
		}

	}
}
