package cn.hy.vo;

/**
 * VO接口
 * @version V5.0
 * @author huangy
 * @date   2012-11-15
 */
public interface IBaseVO {
	/**
	 * 获得表名称
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午5:56:14
	 */
	String getTableName();
	
	/**
	 * 获得字段值
	 * @param columnName
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午5:56:29
	 */
	Object getValue(String columnName);
	
	/**
	 * 设置字段值
	 * @param columnName
	 * @param value
	 * @author huangy
	 * @date 2012-11-15 上午5:56:45
	 */
	void setValue(String columnName,Object value);
	/**
	 * 获得主键
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午5:56:55
	 */
	String getPrimaryKey();
	/**
	 * 获得所有字段
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午5:57:26
	 */
	String[] getFields();
}
