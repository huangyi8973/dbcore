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
	private static Environment _instance=null;
	private Environment(){
		
	}
	/**
	 * 获取系统环境实例
	 * @return
	 * @author huangy
	 * @date 2012-11-18 上午5:59:47
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
	 * 获得当前时间<br/>
	 * 格式：2012-11-18 14:08:00
	 * @return
	 * @author huangy
	 * @date 2012-11-18 上午6:07:51
	 */
	public String getCurDateTime(){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}
	
	
}
