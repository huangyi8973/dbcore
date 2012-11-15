package cn.hy.db.sql;

import cn.hy.utils.ArrayUtils;
import cn.hy.utils.StringUtils;
import cn.hy.vo.IBaseVO;

public class SqlHelper {

	/**
	 * 获得插入的SQL语句
	 * 
	 * @param vo
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午5:51:42
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
	 * 获得更新语句的SQL，改语句只根据PK进行数据更新
	 * @param vo
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午6:58:50
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
	 * 获得查询语句
	 * @param vo
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午7:04:35
	 */
	public static String getSelectSql(IBaseVO vo){
		StringBuilder sql = new StringBuilder();
		String[] strFields = vo.getFields();
		sql.append("select ").append(StringUtils.arrayToString(strFields, ",")).append(" from ").append(vo.getTableName());
		return sql.toString();
	}
}
