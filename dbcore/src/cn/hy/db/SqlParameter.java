package cn.hy.db;

import java.util.ArrayList;
import java.util.List;

public class SqlParameter {

	//���ڴ�Ų���
	private List<Object> _parameterList;
	public SqlParameter(){
		_parameterList=new ArrayList<Object>();
	}
	public void addObject(Object value){
		_parameterList.add(value);
	}
	public List<Object> getParameters(){
		return _parameterList;
	}
}
