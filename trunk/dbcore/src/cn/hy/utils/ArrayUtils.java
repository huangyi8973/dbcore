package cn.hy.utils;


/**
 * 数组的工具类
 * @version V5.0
 * @author huangy
 * @date   2012-11-15
 */
public class ArrayUtils{

	private ArrayUtils(){}
	/**
	 * 用value值填充数组，其他类型的可以使用Arrays类
	 * @param ary
	 * @param value
	 * @author huangy
	 * @date 2012-11-15 上午6:45:19
	 */
	public static void fill(String[] ary,String value){
		if(!isEmpty(ary)){
			for(int i=0;i<ary.length;i++){
				ary[i]=value;
			}
		}
	}
	/**
	 * 判断数组是否为空
	 * @param ary
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午6:46:05
	 */
	public static boolean isEmpty(Object[] ary){
		return ary==null||ary.length==0;
	}
}
