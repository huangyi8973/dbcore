package cn.hy.pub;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ϵͳ������
 * @version V5.0
 * @author huangy
 * @date   2012-11-18
 */
public class Environment {
	private static Environment _instance=null;
	private Environment(){
		
	}
	/**
	 * ��ȡϵͳ����ʵ��
	 * @return
	 * @author huangy
	 * @date 2012-11-18 ����5:59:47
	 */
	public static Environment getInstance(){
		if(_instance==null){
			synchronized (Environment.class) {
				if(_instance==null){
					_instance=new Environment();
				}
			}
		}
		return _instance;
	}
	
	/**
	 * ��õ�ǰʱ��<br/>
	 * ��ʽ��2012-11-18 14:08:00
	 * @return
	 * @author huangy
	 * @date 2012-11-18 ����6:07:51
	 */
	public String getCurDateTime(){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}
	
	
}
