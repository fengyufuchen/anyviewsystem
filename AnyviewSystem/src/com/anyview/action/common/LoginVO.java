package com.anyview.action.common;
/**
 * 
* @ClassName: LoginVO
* @Description: login Value Object
* @author xhn
* @date 2012-10-20 下午08:46:48
*
 */
public class LoginVO {

	private int unId;
	private String username;
	private String password;
	private int rank;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRank(String rank) {
		try{
			this.rank = Integer.valueOf(rank);
		}catch(NumberFormatException e){
			System.out.println("类型转换出错");
			this.rank = 0;
		}
	}
	public int getRank() {
		return rank;
	}
	public int getUnId() {
		return unId;
	}
	public void setUnId(int unId) {
		this.unId = unId;
	}
	
}
