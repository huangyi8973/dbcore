package cn.hy.db.sql;

import cn.hy.db.SqlParameter;
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
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @date 2012-11-15 ����5:51:42
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
	 * ��ø�������SQL�������ֻ����PK�������ݸ���
	 * 
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
	 * 
	 * @param cls VO��
	 * @return
	 * @author huangy
	 * @date 2012-11-16 ����3:10:44
	 */
	public static String getSelectSql(Class<? extends IBaseVO> cls) {
		return getSelectSql(cls, null, null, false);
	}

	public static String getSelectSql(Class<? extends IBaseVO> cls,String wherePart){
		return getSelectSql(cls, wherePart, null, false);
	}
	/**
	 * ��ò�ѯ���
	 * @param cls VO��
	 * @param wherePart ����������Ϊ��
	 * @param orderPart ��������������Ϊ��
	 * @param isAsc �Ƿ�Ϊ�������������������Ϊ�գ�������������
	 * @return
	 * @author huangy
	 * @date 2012-11-18 ����1:27:16
	 */
	public static String getSelectSql(Class<? extends IBaseVO> cls, String wherePart, String orderPart, boolean isAsc) {
		IBaseVO vo = null;
		StringBuilder sql = new StringBuilder();
		try {
			vo = cls.newInstance();
			String[] strFields = vo.getFields();
			sql.append("select ").append(StringUtils.arrayToString(strFields, ",")).append(" from ")
				.append(vo.getTableName());
			// where������Ϊ�գ�����where����
			if (!StringUtils.isEmpty(wherePart)) {
				sql.append(" where ").append(wherePart);
			}
			// order by ��Ϊ�գ��������������
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
	 * ���ɾ������SQL
	 * 
	 * @param cls
	 * @return
	 * @author huangy
	 * @date 2012-11-18 ����1:19:00
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
	 * ��ȡ��������ɾ����SQL���
	 * @param cls
	 * @param wherePart
	 * @return
	 * @author huangy
	 * @date 2012-11-18 ����1:54:33
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
	 * ���������SQL��䣨��������ֵ)
	 * @param sql
	 * @param par
	 * @return
	 * @author huangy
	 * @date 2012-11-18 ����2:40:34
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
			//���ֽ���ַ����Ȳ���ֵ��1��ʱ��������������һ���ַ���
			sb.append(token[token.length-1]);
		}
		return sb.toString();
	}

	/**
	 * ���ݲ��������ͻ�ò���ֵ��SQLд��
	 * @param object
	 * @return
	 * @author huangy
	 * @date 2012-11-18 ����2:40:52
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
