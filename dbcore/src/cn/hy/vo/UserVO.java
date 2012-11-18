package cn.hy.vo;

import cn.hy.db.annotation.Column;


public class UserVO extends BaseVO {

	//by:huangy 2012-11-18 
	private static final long serialVersionUID = -7336354933530777846L;
	@Column
	private String pk_user;
	@Column
	private String vusercode;
	@Column
	private String vusername;
	@Column
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
	
}
