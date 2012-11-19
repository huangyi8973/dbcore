package cn.hy.db;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 主键生成器，使用UUID生成32位主键
 * @version V5.0
 * @author huangy
 * @date   2012-11-18
 */
public class PrimaryKeyCreater {

	private PrimaryKeyCreater(){};
	public static String getKey(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	public static void main(String[] args){
		Set<String> set=new HashSet<String>();
		for(int i=0;i<100;i++){
			set.add(PrimaryKeyCreater.getKey());
		}
		System.out.println(set.size());
	}
}
