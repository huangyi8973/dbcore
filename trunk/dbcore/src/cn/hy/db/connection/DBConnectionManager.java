package cn.hy.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import cn.hy.pub.Environment;

/**
 * 连接池
 * 
 * @version V5.0
 * @author huangy
 * @date 2012-11-18
 */
public class DBConnectionManager {

	private String dbUrl = "jdbc:db2://127.0.0.1:50000/cms";

	private String dbUserName = "huangy";

	private String dbPassword = "xmjmhy";

	// 连接池实例
	private static DBConnectionManager _instance = null;

	// 最大连接数
	private int MAX_CONNECTION = 10;

	// 初始连接数
	private int INIT_CONNECTION = 2;

	// 数据库连接容器
	private Vector<Connection> _connPool;

	private Vector<Integer> _connStatus;

	// 连接标志
	private static int USED = 0; //使用中

	private static int FREE = 1; //空闲

	// 加载数据库驱动，只在第一次加载类的时候加载
	static {
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private DBConnectionManager() {
		initPool();
	}

	/**
	 * 初始化连接池
	 * 
	 * @author huangy
	 * @date 2012-11-18 上午6:11:41
	 */
	private void initPool() {
		_connPool = new Vector<Connection>();
		_connStatus = new Vector<Integer>();
		System.out.println(String.format("[%s] 初始化连接池", Environment.getCurDateTime()));
		long start = System.currentTimeMillis();
		for (int i = 0; i < INIT_CONNECTION; i++) {
			try {
				Connection conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
				_connPool.add(conn);
				_connStatus.add(FREE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(String.format("[%s] 共耗时：%s ms", Environment.getCurDateTime(), end - start));

	}

	public static DBConnectionManager getInstance() {
		if (_instance == null) {
			synchronized (DBConnectionManager.class) {
				// 双重检查
				if (_instance == null) {
					_instance = new DBConnectionManager();
				}
			}
		}
		return _instance;
	}

	/**
	 * 获得连接
	 * 
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @date 2012-11-18 上午7:02:20
	 */
	public synchronized Connection getConnection() throws SQLException {
		long start = System.currentTimeMillis();
		Connection conn = null;
		int index = _connStatus.indexOf(FREE);
		if (index != -1) {
			_connStatus.set(index, USED);
			conn = _connPool.get(index);
		}
		if (conn == null) {
			if (_connPool.size() < MAX_CONNECTION) {
				conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
				_connPool.add(conn);
				_connStatus.add(USED);
				System.out.println("连接不够了，建立新连接，当前连接数：" + _connPool.size());

			}
		}
		long end = System.currentTimeMillis();
		System.out.println(String.format("[%s] 获取连接,共耗时：%s", Environment.getCurDateTime(), end - start));
		return conn;
	}

	/**
	 * 释放连接
	 * 
	 * @param conn
	 * @author huangy
	 * @date 2012-11-18 上午6:43:09
	 */
	public synchronized void free(Connection conn) {
		System.out.println(String.format("[%s] 释放连接", Environment.getCurDateTime()));
		int index = _connPool.indexOf(conn);
		_connStatus.set(index, FREE);
	}

	/**
	 * 关闭并移除所有连接
	 * @author huangy
	 * @date 2012-11-18 上午10:34:50
	 */
	public synchronized void closeAll() {
		try {
			for (Connection conn : _connPool) {
				conn.close();
			}
			_connPool.clear();
			_connStatus.clear();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
