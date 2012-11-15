package cn.hy.utils;


/**
 * String������
 * @version V5.0
 * @author huangy
 * @date   2012-11-15
 */
public class StringUtils {

	/**
	 * �ַ�������ĸ��д
	 * @param str
	 * @return
	 * @author huangy
	 * @date 2012-11-15 ����3:56:43
	 */
	public static String firstLetterToUpper(String str){
		char[] buffer=str.toCharArray();
		if(buffer.length==0){
			return str;
		}
		buffer[0]=Character.toUpperCase(buffer[0]);
		return String.valueOf(buffer);
	}
	
	/**
	 * �ַ������鰴�ָ�����һ���ַ���<br/>
	 * eg.["aaa","bbb"],","->"aaa,bbb"<br/>
	 * ["aaa","bbb"],"-"->"aaa-bbb"
	 * @param ary �ַ�������
	 * @param seperation �ָ��
	 * @return �ϳɵ��ַ���
	 * @author huangy
	 * @date 2012-11-15 ����6:10:50
	 */
	public static String arrayToString(String[] ary,String seperation){
		return arrayToString(ary, seperation, null, null);
	}
	/**
	 * �ַ������鰴�ָ�����һ���ַ���<br/>
	 * arrayToString(["aaa","bbb"],",","#","$")<br/>
	 * ���Ϊ��#aaa$,#bbb$
	 * @param ary �ַ�������
	 * @param seperation �ָ�����
	 * @param prefix ǰ׺
	 * @param suffix ��׺
	 * @return
	 * @author huangy
	 * @date 2012-11-15 ����6:30:43
	 */
	public static String arrayToString(String[] ary,String seperation,String prefix ,String suffix){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<ary.length;i++){
			if(prefix!=null){
				sb.append(prefix);
			}
			sb.append(ary[i]);
			if(suffix!=null){
				sb.append(suffix);
			}
			if(i!=ary.length-1){
				//ֻ�����һ���ַ����üӷָ���ţ�����������
				sb.append(seperation);
			}
		}
		return sb.toString();
	}
}
