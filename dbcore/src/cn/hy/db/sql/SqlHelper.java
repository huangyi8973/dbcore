package cn.hy.db.sql;

import cn.hy.db.SqlParameter;
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
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @date 2012-11-15 上午5:51:42
	 */
	public static String getInsertSql(Class<? extends IBaseVO> cls) throws InstantiationException,
			IllegalAccessException {
		IBaseVO vo = cls.newInstance();
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
	 * 
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
	 * 
	 * @param cls VO类
	 * @return
	 * @author huangy
	 * @date 2012-11-16 上午3:10:44
	 */
	public static String getSelectSql(Class<? extends IBaseVO> cls) {
		return getSelectSql(cls, null, null, false);
	}

	public static String getSelectSql(Class<? extends IBaseVO> cls,String wherePart){
		return getSelectSql(cls, wherePart, null, false);
	}
	/**
	 * 获得查询语句
	 * @param cls VO类
	 * @param wherePart 条件，可以为空
	 * @param orderPart 排序条件，可以为空
	 * @param isAsc 是否为升序排序，如果排序条件为空，这个可以随便填
	 * @return
	 * @author huangy
	 * @date 2012-11-18 下午1:27:16
	 */
	public static String getSelectSql(Class<? extends IBaseVO> cls, String wherePart, String orderPart, boolean isAsc) {
		IBaseVO vo = null;
		StringBuilder sql = new StringBuilder();
		try {
			vo = cls.newInstance();
			String[] strFields = vo.getFields();
			sql.append("select ").append(StringUtils.arrayToString(strFields, ",")).append(" from ")
				.append(vo.getTableName());
			// where条件不为空，加上where条件
			if (!StringUtils.isEmpty(wherePart)) {
				sql.append(" where ").append(wherePart);
			}
			// order by 不为空，添加上排序条件
			if (!StringUtils.isEmpty(orderPart)) {
				sql.append(" order by ").append(orderPart).append(isAsc ? " asc" : " desc");
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return sql.toString();
	}

	/**
	 * 获得删除语句的SQL
	 * 
	 * @param cls
	 * @return
	 * @author huangy
	 * @date 2012-11-18 下午1:19:00
	 */
	public static String getDeleteSql(Class<? extends IBaseVO> cls) {
		IBaseVO vo = null;
		StringBuilder sql = new StringBuilder();
		try {
			vo = cls.newInstance();
			sql.append(" where ").append(vo.getPrimaryKey())
				.append("=?");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return getDeleteSql(cls, sql.toString());
	}
	/**
	 * 获取根据条件删除的SQL语句
	 * @param cls
	 * @param wherePart
	 * @return
	 * @author huangy
	 * @date 2012-11-18 下午1:54:33
	 */
	public static String getDeleteSql(Class<? extends IBaseVO> cls,String wherePart){
		IBaseVO vo = null;
		StringBuilder sql = new StringBuilder();
		try {
			vo = cls.newInstance();
			sql.append("delete from ").append(vo.getTableName()).append(" where ").append(wherePart);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return sql.toString();
	}
	/**
	 * 获得完整的SQL语句（包含参数值)
	 * @param sql
	 * @param par
	 * @return
	 * @author huangy
	 * @date 2012-11-18 下午2:40:34
	 */
	public static String getCompletelySql(String sql,SqlParameter par){
		if(par==null||par.getParameters().size()==0){
			return sql;
		}
		String[] token=sql.split("\\?");
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<par.getParameters().size();i++){
			sb.append(token[i]);
			sb.append(getParameterValue(par.get(i)));
		}
		if(token.length>par.getParameters().size()){
			//当分解的字符串比参数值多1的时候，在这里加上最后一个字符串
			sb.append(token[token.length-1]);
		}
		return sb.toString();
	}

	/**
	 * 根据参数的类型获得参数值的SQL写法
	 * @param object
	 * @return
	 * @author huangy
	 * @date 2012-11-18 下午2:40:52
	 */
	private static String getParameterValue(Object object) {
		String strResult=null;
		if(String.class.getName().equals(object.getClass().getName())){
			strResult="'"+(String)object+"'";
		}
		else if(Integer.class.getName().equals(object.getClass().getName())){
			strResult=String.valueOf(object);
		}
		else if(Double.class.getName().equals(object.getClass().getName())){
			strResult=String.valueOf(object);
		}
		else if(Float.class.getName().equals(object.getClass().getName())){
			strResult=String.valueOf(object);
		}
		
		return strResult;
	}
}
