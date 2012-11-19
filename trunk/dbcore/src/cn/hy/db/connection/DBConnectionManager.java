package cn.hy.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import cn.hy.pub.Environment;

/**
 * ���ӳ�
 * 
 * @version V5.0
 * @author huangy
 * @date 2012-11-18
 */
public class DBConnectionManager {

	private String dbUrl = "jdbc:db2://127.0.0.1:50000/cms";

	private String dbUserName = "huangy";

	private String dbPassword = "xmjmhy";

	// ���ӳ�ʵ��
	private static DBConnectionManager _instance = null;

	// ���������
	private int MAX_CONNECTION = 10;

	// ��ʼ������
	private int INIT_CONNECTION = 2;

	// ���ݿ���������
	private Vector<Connection> _connPool;

	private Vector<Integer> _connStatus;

	// ���ӱ�־
	private static int USED = 0; //ʹ����

	private static int FREE = 1; //����

	// �������ݿ�������ֻ�ڵ�һ�μ������ʱ�����
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
	 * ��ʼ�����ӳ�
	 * 
	 * @author huangy
	 * @date 2012-11-18 ����6:11:41
	 */
	private void initPool() {
		_connPool = new Vector<Connection>();
		_connStatus = new Vector<Integer>();
		System.out.println(String.format("[%s] ��ʼ�����ӳ�", Environment.getCurDateTime()));
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
		System.out.println(String.format("[%s] ����ʱ��%s ms", Environment.getCurDateTime(), end - start));

	}

	public static DBConnectionManager getInstance() {
		if (_instance == null) {
			synchronized (DBConnectionManager.class) {
				// ˫�ؼ��
				if (_instance == null) {
					_instance = new DBConnectionManager();
				}
			}
		}
		return _instance;
	}

	/**
	 * �������
	 * 
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @date 2012-11-18 ����7:02:20
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
				System.out.println("���Ӳ����ˣ����������ӣ���ǰ��������" + _connPool.size());

			}
		}
		long end = System.currentTimeMillis();
		System.out.println(String.format("[%s] ��ȡ����,����ʱ��%s", Environment.getCurDateTime(), end - start));
		return conn;
	}

	/**
	 * �ͷ�����
	 * 
	 * @param conn
	 * @author huangy
	 * @date 2012-11-18 ����6:43:09
	 */
	public synchronized void free(Connection conn) {
		System.out.println(String.format("[%s] �ͷ�����", Environment.getCurDateTime()));
		int index = _connPool.indexOf(conn);
		_connStatus.set(index, FREE);
	}

	/**
	 * �رղ��Ƴ���������
	 * @author huangy
	 * @date 2012-11-18 ����10:34:50
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
