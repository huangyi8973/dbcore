package cn.hy.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.hy.db.BaseDao;
import cn.hy.db.annotation.Column;
import cn.hy.db.annotation.PrimaryKey;
import cn.hy.utils.StringUtils;

/**
 * VO基类
 * 
 * @version V5.0
 * @author huangy
 * @date 2012-11-15
 */
public abstract class BaseVO implements IBaseVO, Serializable {

	// by:huangy 2012-11-18
	private static final long serialVersionUID = -975279535721610769L;

	private String[] _fields;

	/**
	 * 获取值
	 * 
	 * @see cn.hy.vo.IBaseVO#getValue(java.lang.String)
	 * @param columnName 字段名
	 * @return 字段值
	 * @author huangy
	 * @date 2012-11-15 上午4:42:48
	 */
	public Object getValue(String columnName) {
		columnName = columnName.toLowerCase();
		Class<?> c = this.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(columnName)) {
				// 有这个字段，则获取这个字段的值
				String methodName = "get" + StringUtils.firstLetterToUpper(field.getName());
				try {
					Method method = c.getMethod(methodName);
					return method.invoke(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 设置值
	 * 
	 * @see cn.hy.vo.IBaseVO#setValue(java.lang.String, java.lang.Object)
	 * @param columnName 字段名称
	 * @param 字段值
	 * @author huangy
	 * @date 2012-11-15 上午4:44:03
	 */
	public void setValue(String columnName, Object value) {
		columnName = columnName.toLowerCase();
		Class<?> c = this.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(columnName)) {
				// 有这个字段，则获取这个字段的值
				String methodName = "set" + StringUtils.firstLetterToUpper(field.getName());
				try {
					// 按照函数签名的参数获取函数
					Method method = c.getMethod(methodName, field.getType());
					method.invoke(this, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获得VO的字段
	 * 
	 * @see cn.hy.vo.IBaseVO#getFields()
	 * @return
	 * @author huangy
	 * @date 2012-11-18 上午4:16:15
	 */
	public String[] getFields() {
		if (_fields == null) {
			// 同步VO字段缓存
			Class<?> c = this.getClass();
			Field[] fields = c.getDeclaredFields();
			synchronized (BaseDao.class) {
				String primaryKey=null;//保存主键
				List<String> fieldNames = new ArrayList<String>();
				for (int i = 0; i < fields.length; i++) {
					if (fields[i].isAnnotationPresent(Column.class)) {
						fieldNames.add(fields[i].getName());
					}
					else if(fields[i].isAnnotationPresent(PrimaryKey.class)){
						primaryKey=fields[i].getName();
					}
				}
				//把主键放在第一个位置
				fieldNames.add(0, primaryKey);
				_fields = fieldNames.toArray(new String[0]);
			}

		}
		return _fields;
	}

	public String getPrimaryKeyValue() {
		
		return (String)getValue(getPrimaryKey());
	}
}
