package cn.hy.vo;


public class UserVO extends BaseVO {

	private String pk_user;
	private String vusercode;
	private String vusername;
	private String vpassword;
	public String getPk_user() {
		return pk_user;
	}
	public void setPk_user(String pk_user) {
		this.pk_user = pk_user;
	}
	public String getVusercode() {
		return vusercode;
	}
	public void setVusercode(String vusercode) {
		this.vusercode = vusercode;
	}
	public String getVusername() {
		return vusername;
	}
	public void setVusername(String vusername) {
		this.vusername = vusername;
	}
	public String getVpassword() {
		return vpassword;
	}
	public void setVpassword(String vpassword) {
		this.vpassword = vpassword;
	}
	public String getTableName() {
		
		return "s_user";
	}
	
	public String getPrimaryKey() {
		
		return "pk_user";
	}
	public static void main(String[] args){
		IBaseVO vo=new UserVO();
		vo.setValue("vpassword", "aaa");
		System.out.println(vo.getValue("vpassword"));
		vo.setValue("aa", "bbb");
		System.out.println(vo.getValue("vpassword"));
	}
	
}
