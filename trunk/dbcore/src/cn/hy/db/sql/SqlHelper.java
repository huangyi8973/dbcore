package cn.hy.db.sql;

import cn.hy.utils.ArrayUtils;
import cn.hy.utils.StringUtils;
import cn.hy.vo.IBaseVO;

public class SqlHelper {

	/**
	 * ��ò����SQL���
	 * 
	 * @param vo
	 * @return
	 * @author huangy
	 * @date 2012-11-15 ����5:51:42
	 */
	public static String getInsertSql(IBaseVO vo) {
		StringBuilder sql = new StringBuilder();
		String[] strFields = vo.getFields();
		String[] values = new String[strFields.length];
		ArrayUtils.fill(values, "?");
		sql.append("insert into ").append(vo.getTableName()).append("(")
			.append(StringUtils.arrayToString(strFields, ",")).append(")").append(" values(")
			.append(StringUtils.arrayToString(values, ",")).append(")");
		return sql.toString();
	}

	/**
	 * ��ø�������SQL�������ֻ����PK�������ݸ���
	 * @param vo
	 * @return
	 * @author huangy
	 * @date 2012-11-15 ����6:58:50
	 */
	public static String getUpdateSql(IBaseVO vo) {
		StringBuilder sql = new StringBuilder();
		String[] strFields = vo.getFields();
		sql.append("update ").append(vo.getTableName()).append(" set ")
			.append(StringUtils.arrayToString(strFields, ",", null, "=?")).append(" where ").append(vo.getPrimaryKey())
			.append("=?");
		return sql.toString();
	}
	
	/**
	 * ��ò�ѯ���
	 * @param cls VO��
	 * @return
	 * @author huangy
	 * @date 2012-11-16 ����3:10:44
	 */
	public static String getSelectSql(Class<? extends IBaseVO> cls){
		IBaseVO vo=null;
		StringBuilder sql = new StringBuilder();
		try {
			vo = cls.newInstance();
			String[] strFields = vo.getFields();
			sql.append("select ").append(StringUtils.arrayToString(strFields, ",")).append(" from ").append(vo.getTableName());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return sql.toString();
		
	}
}
