package cn.hy.utils;


/**
 * ����Ĺ�����
 * @version V5.0
 * @author huangy
 * @date   2012-11-15
 */
public class ArrayUtils{

	private ArrayUtils(){}
	/**
	 * ��valueֵ������飬�������͵Ŀ���ʹ��Arrays��
	 * @param ary
	 * @param value
	 * @author huangy
	 * @date 2012-11-15 ����6:45:19
	 */
	public static void fill(String[] ary,String value){
		if(!isEmpty(ary)){
			for(int i=0;i<ary.length;i++){
				ary[i]=value;
			}
		}
	}
	/**
	 * �ж������Ƿ�Ϊ��
	 * @param ary
	 * @return
	 * @author huangy
	 * @date 2012-11-15 ����6:46:05
	 */
	public static boolean isEmpty(Object[] ary){
		return ary==null||ary.length==0;
	}
}
