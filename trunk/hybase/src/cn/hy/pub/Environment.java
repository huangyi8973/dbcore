package cn.hy.pub;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 系统环境类
 * @version V5.0
 * @author huangy
 * @date   2012-11-18
 */
public class Environment {
	private Environment(){
		
	}
	
	
	/**
	 * 获得当前时间<br/>
	 * 格式：2012-11-18 14:08:00
	 * @return
	 * @author huangy
	 * @date 2012-11-18 上午6:07:51
	 */
	public static String getCurDateTime(){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}
	
	
}
