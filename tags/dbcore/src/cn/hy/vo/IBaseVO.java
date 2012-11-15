package cn.hy.vo;

/**
 * VO�ӿ�
 * @version V5.0
 * @author huangy
 * @date   2012-11-15
 */
public interface IBaseVO {
	/**
	 * ��ñ�����
	 * @return
	 * @author huangy
	 * @date 2012-11-15 ����5:56:14
	 */
	String getTableName();
	
	/**
	 * ����ֶ�ֵ
	 * @param columnName
	 * @return
	 * @author huangy
	 * @date 2012-11-15 ����5:56:29
	 */
	Object getValue(String columnName);
	
	/**
	 * �����ֶ�ֵ
	 * @param columnName
	 * @param value
	 * @author huangy
	 * @date 2012-11-15 ����5:56:45
	 */
	void setValue(String columnName,Object value);
	/**
	 * �������
	 * @return
	 * @author huangy
	 * @date 2012-11-15 ����5:56:55
	 */
	String getPrimaryKey();
	/**
	 * ��������ֶ�
	 * @return
	 * @author huangy
	 * @date 2012-11-15 ����5:57:26
	 */
	String[] getFields();
}
