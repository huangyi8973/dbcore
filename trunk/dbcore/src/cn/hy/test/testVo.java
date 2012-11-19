package cn.hy.test;

import java.util.Arrays;
import java.util.List;

import cn.hy.vo.IBaseVO;
import cn.hy.vo.UserVO;

public class testVo {



	public static void main(String[] args) {
		IBaseVO vo=new UserVO();
		String[] fields=vo.getFields();
		IBaseVO vo2=new UserVO();
		String[] fields2=vo.getFields();
		String[] rightFields=new String[]{
				"pk_user","vusercode","vusername","vpassword"
		};
		List<String> l1=Arrays.asList(fields);
		List<String> l2=Arrays.asList(rightFields);
		if(l1.containsAll(l2)){
			System.out.println(true);
		}else{
			System.out.println(false);
		}
	};

}
