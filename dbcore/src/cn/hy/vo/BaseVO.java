package cn.hy.vo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.hy.utils.StringUtils;

/**
 * VO����
 * @version V5.0
 * @author huangy
 * @date   2012-11-15
 */
public abstract class BaseVO implements IBaseVO {

	/**
	 * ��ȡֵ
	 * @see cn.hy.vo.IBaseVO#getValue(java.lang.String)
	 * @param columnName �ֶ���
	 * @return �ֶ�ֵ
	 * @author huangy
	 * @date 2012-11-15 ����4:42:48
	 */
	public Object getValue(String columnName) {
		columnName=columnName.toLowerCase();
		Class<?> c=this.getClass();
		Field[] fields=c.getDeclaredFields();
		for(Field field:fields){
			if(field.getName().equals(columnName)){
				//������ֶΣ����ȡ����ֶε�ֵ
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
	 * ����ֵ
	 * @see cn.hy.vo.IBaseVO#setValue(java.lang.String, java.lang.Object)
	 * @param columnName �ֶ�����
	 * @param �ֶ�ֵ
	 * @author huangy
	 * @date 2012-11-15 ����4:44:03
	 */
	public void setValue(String columnName, Object value) {
		columnName=columnName.toLowerCase();
		Class<?> c=this.getClass();
		Field[] fields=c.getDeclaredFields();//TODO ���������Ϣ���Ի�������
		for(Field field:fields){
			if(field.getName().equals(columnName)){
				//������ֶΣ����ȡ����ֶε�ֵ
				String methodName="set"+StringUtils.firstLetterToUpper(field.getName());
				try {
					//���պ���ǩ���Ĳ�����ȡ����
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
