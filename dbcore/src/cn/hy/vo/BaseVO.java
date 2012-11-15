package cn.hy.vo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.hy.utils.StringUtils;

/**
 * VO基类
 * @version V5.0
 * @author huangy
 * @date   2012-11-15
 */
public abstract class BaseVO implements IBaseVO {

	/**
	 * 获取值
	 * @see cn.hy.vo.IBaseVO#getValue(java.lang.String)
	 * @param columnName 字段名
	 * @return 字段值
	 * @author huangy
	 * @date 2012-11-15 上午4:42:48
	 */
	public Object getValue(String columnName) {
		Class<?> c=this.getClass();
		Field[] fields=c.getDeclaredFields();
		for(Field field:fields){
			if(field.getName().equals(columnName)){
				//有这个字段，则获取这个字段的值
				String methodName="get"+StringUtils.firstLetterToUpper(field.getName());
				try {
					Method method=c.getMethod(methodName);
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
	 * @see cn.hy.vo.IBaseVO#setValue(java.lang.String, java.lang.Object)
	 * @param columnName 字段名称
	 * @param 字段值
	 * @author huangy
	 * @date 2012-11-15 上午4:44:03
	 */
	public void setValue(String columnName, Object value) {
		Class<?> c=this.getClass();
		Field[] fields=c.getDeclaredFields();//TODO 这里类的信息可以缓存起来
		for(Field field:fields){
			if(field.getName().equals(columnName)){
				//有这个字段，则获取这个字段的值
				String methodName="set"+StringUtils.firstLetterToUpper(field.getName());
				try {
					//按照函数签名的参数获取函数
					Method method=c.getMethod(methodName,field.getType());
					method.invoke(this,value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public String[] getFields() {
		Class<?> c=this.getClass();
		Field[] fields=c.getDeclaredFields();
		String[] fieldNameAry=new String[fields.length];
		for(int i=0;i<fields.length;i++){
			fieldNameAry[i]=fields[i].getName();
		}
		return fieldNameAry;
	}

}
