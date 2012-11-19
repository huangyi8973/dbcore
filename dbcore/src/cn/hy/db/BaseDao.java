package cn.hy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.hy.db.connection.DBConnectionManager;
import cn.hy.db.sql.SqlHelper;
import cn.hy.pub.Environment;
import cn.hy.utils.MathUtils;
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

	/**
	 * 查询所有VO
	 * 
	 * @param cls
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @throws IllegalAccessException
	 * @author huangy
	 * @date 2012-11-18 下午1:39:15
	 */
	public static List<IBaseVO> queryVos(Class<? extends IBaseVO> cls) throws SQLException, Exception,
			IllegalAccessException {
		return queryByCondition(cls, null);
	}

	/**
	 * 根据PK查询
	 * 
	 * @param cls
	 * @param primaryKeyValue
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 * @author huangy
	 * @date 2012-11-18 下午1:39:05
	 */
	public static IBaseVO queryByPrimaryKey(Class<? extends IBaseVO> cls, String primaryKeyValue)
			throws InstantiationException, IllegalAccessException, SQLException {
		IBaseVO vo = cls.newInstance();
		String wherePart = vo.getPrimaryKey() + "='" + primaryKeyValue + "'";
		List<IBaseVO> result = queryByCondition(cls, wherePart);
		return result.size() == 0 ? null : result.get(0);
	}

	/**
	 * 根据条件查询VO
	 * 
	 * @param cls
	 * @param wherePart
	 * @return VO集合
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @author huangy
	 * @date 2012-11-18 下午1:39:54
	 */
	public static List<IBaseVO> queryByCondition(Class<? extends IBaseVO> cls, String wherePart) throws SQLException,
			InstantiationException, IllegalAccessException {
		// 1.建立连接
		Connection conn = DBConnectionManager.getInstance().getConnection();
		String sql = SqlHelper.getSelectSql(cls, wherePart);
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.clearParameters();
		// 2.执行SQL
		long start = System.currentTimeMillis();
		ResultSet rs = ps.executeQuery();
		long end = System.currentTimeMillis();
		System.out.println(String.format(	"[%s] 执行SQL：%s", Environment.getCurDateTime(),
											SqlHelper.getCompletelySql(sql, null)));
		System.out.println(String.format("[%s] 执行时间：%sms", Environment.getCurDateTime(), end - start));
		// 3.转存数据到VO
		List<IBaseVO> result = convert2Vo(rs, cls);
		System.out.println(String.format("[%s] 查询结果：%d条", Environment.getCurDateTime(), result.size()));
		// 4.关闭连接，记录等
		close(conn, ps, rs);
		return result;
	}

	/**
	 * 插入VO
	 * 
	 * @param vo
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 * @author huangy
	 * @date 2012-11-18 下午12:31:15
	 */
	public static int insertVo(IBaseVO vo) throws InstantiationException, IllegalAccessException, SQLException {
		String sql = SqlHelper.getInsertSql(vo.getClass());
		SqlParameter par = new SqlParameter();
		// 第一个默认为主键，生成主键值
		par.addObject(PrimaryKeyCreater.getKey());
		String[] fields = vo.getFields();
		for (int i = 1; i < fields.length; i++) {
			par.addObject(vo.getValue(fields[i]));
		}
		int row = exec(sql, par)[0];
		return row;
	}

	public static int[] insertVoList(List<IBaseVO> voList) throws SQLException, InstantiationException,
			IllegalAccessException {
		int[] row = new int[] { 0 };
		if (voList != null && voList.size() > 0) {
			SqlParameter[] parAry = new SqlParameter[voList.size()];
			String sql = SqlHelper.getInsertSql(voList.get(0).getClass());
			int index = 0;
			for (IBaseVO vo : voList) {
				SqlParameter par = new SqlParameter();
				// 第一个默认为主键，生成主键值
				par.addObject(PrimaryKeyCreater.getKey());
				String[] fields = vo.getFields();
				for (int i = 1; i < fields.length; i++) {
					par.addObject(vo.getValue(fields[i]));
				}
				parAry[index++] = par;
			}
			row = exec(sql, parAry);
		}
		return row;
	}

	/**
	 * 删除VO;
	 * 
	 * @param vo
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @date 2012-11-18 下午1:06:11
	 */
	public static int deleteVo(IBaseVO vo) throws SQLException {
		String sql = SqlHelper.getDeleteSql(vo.getClass());
		SqlParameter par = new SqlParameter();
		par.addObject(vo.getPrimaryKeyValue());
		return exec(sql, par)[0];
	}

	/**
	 * 根据条件删除数据(带参数)
	 * 
	 * @param cls
	 * @param wherePart
	 * @param par
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @date 2012-11-18 下午1:59:59
	 */
	public static int deleteByCondition(Class<? extends IBaseVO> cls, String wherePart, SqlParameter par)
			throws SQLException {
		String sql = SqlHelper.getDeleteSql(cls, wherePart);
		return exec(sql, par)[0];
	}

	/**
	 * 根据条件删除数据
	 * 
	 * @param cls
	 * @param wherePart
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @date 2012-11-18 下午2:00:41
	 */
	public static int deleteByCondition(Class<? extends IBaseVO> cls, String wherePart) throws SQLException {
		String sql = SqlHelper.getDeleteSql(cls, wherePart);
		return exec(sql);
	}

	/**
	 * 执行插入，更新，删除的SQL语句
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @date 2012-11-18 下午1:59:20
	 */
	public static int exec(String sql) throws SQLException {
		return exec(sql, null)[0];
	}

	public static int[] exec(String sql, SqlParameter... parList) throws SQLException {
		Connection conn = DBConnectionManager.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		long start = 0l;
		long end = 0l;
		int[] rows;
		ps.clearParameters();
		if (parList != null && parList.length > 0) {
			// 多个参数时，批量执行
			for (SqlParameter par : parList) {
				for (int i = 0; i < par.size(); i++) {
					ps.setObject(i + 1, par.get(i));
				}
				ps.addBatch();
				System.out.println(String.format(	"[%s] 执行SQL：%s", Environment.getCurDateTime(),
													SqlHelper.getCompletelySql(sql, par)));
			}
			start = System.currentTimeMillis();
			rows = ps.executeBatch();
			end = System.currentTimeMillis();
		} else {
			// 没有参数的时候，只执行一条
			start = System.currentTimeMillis();
			int r = ps.executeUpdate();
			rows = new int[] { r };
			end = System.currentTimeMillis();
			System.out.println(String.format("[%s] 执行SQL：%s", Environment.getCurDateTime(), sql));
		}
		System.out.println(String.format("[%s] 执行时间：%sms", Environment.getCurDateTime(), end - start));
		System.out.println(String.format("[%s] 查询结果：%d条", Environment.getCurDateTime(), MathUtils.sum(rows)));
		close(conn, ps, null);
		return rows;
	}

	/**
	 * 关闭连接，记录集等
	 * 
	 * @param conn
	 * @param ps
	 * @param rs
	 * @author huangy
	 * @date 2012-11-16 上午3:17:12
	 */
	private static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			// 关闭记录集
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭PreparedStatement
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// 关闭连接
				if (conn != null) {
					DBConnectionManager.getInstance().free(conn);

				}
			}
		}
	}

	/**
	 * 把数据转存到VO中
	 * 
	 * @param rs
	 * @param cls
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @date 2012-11-16 上午3:18:56
	 */
	private static List<IBaseVO> convert2Vo(ResultSet rs, Class<? extends IBaseVO> cls) throws SQLException,
			InstantiationException, IllegalAccessException {
		ResultSetMetaData meta = null;

		List<IBaseVO> list = new ArrayList<IBaseVO>();
		if (rs != null) {
			meta = rs.getMetaData();
			while (rs.next()) {
				IBaseVO vo = cls.newInstance();
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					// 获取字段，填充到VO
					vo.setValue(meta.getColumnName(i), rs.getObject(i));
				}
				list.add(vo);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		// 0.准备动作
		Connection conn = null;
		PreparedStatement psta = null;
		ResultSet rs = null;
		// 1.建立连接
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			// conn = DBConnectionManager.getInstance().getConnection();
			conn = DriverManager.getConnection("jdbc:db2://127.0.0.1:50000/cms", "huangy", "xmjmhy");
			String sql = SqlHelper.getSelectSql(UserVO.class);
			psta = conn.prepareStatement(sql);
			System.out.println("sql:" + sql);
			rs = psta.executeQuery();
			psta.close();
			rs.close();
			conn.close();
			// //把数据填充到VO里去
			// ResultSetMetaData meta = rs.getMetaData();
			// List<IBaseVO> voList=new ArrayList<IBaseVO>();
			// while(rs.next()){
			// UserVO vo=new UserVO();
			// for(int i=1;i<=meta.getColumnCount();i++){
			// //获取字段，填充到VO
			// vo.setValue(meta.getColumnName(i), rs.getObject(i));
			// }
			// voList.add(vo);
			// }
			// System.out.println(voList.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭记录集
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// 关闭PreparedStatement
				try {
					if (psta != null)
						psta.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					// 关闭连接
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
