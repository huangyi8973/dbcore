package cn.hy.utils;

/**
 * ��ѧ����
 * @version V5.0
 * @author huangy
 * @date   2012-11-19
 */
public class MathUtils {

	/**
	 * ���������ϼ�
	 * @param num
	 * @return
	 * @author huangy
	 * @date 2012-11-19 ����4:35:50
	 */
	public static int sum(int[] num){
		int result=0;
		for(int n:num){
			result+=n;
		}
		return result;
	}
}
