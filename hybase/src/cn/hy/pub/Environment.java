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
	private Environment(){
		
	}
	
	
	/**
	 * ��õ�ǰʱ��<br/>
	 * ��ʽ��2012-11-18 14:08:00
	 * @return
	 * @author huangy
	 * @date 2012-11-18 ����6:07:51
	 */
	public static String getCurDateTime(){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}
	
	
}
