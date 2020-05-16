package org.qinzi.card.po;

import java.io.Serializable;

/**
 * Created by chenguifeng on 2020/5/16.
 */
public class User implements Serializable{
	private String username;
	private String password;
	private String sysUserName;
	private String sysUserAvatar;

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

	public String getSysUserName() {
		return sysUserName;
	}

	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}

	public String getSysUserAvatar() {
		return sysUserAvatar;
	}

	public void setSysUserAvatar(String sysUserAvatar) {
		this.sysUserAvatar = sysUserAvatar;
	}
}
