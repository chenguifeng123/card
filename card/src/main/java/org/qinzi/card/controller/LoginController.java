package org.qinzi.card.controller;

import org.qinzi.card.exception.GlobalProcessException;
import org.qinzi.card.po.User;
import org.qinzi.card.web.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenguifeng on 2020/5/16.
 */
@RestController
public class LoginController {

	private String userName = "root";
	private String pd = "root123";

	@RequestMapping("/login")
	public Result login(@RequestBody User user){
		if(user == null || !userName.equals(user.getUsername()) || !pd.equals(user.getPassword()))
			throw new GlobalProcessException("用户登录失败");
		user.setSysUserName(userName);
		user.setSysUserAvatar("user.png");
		return Result.ok(user);
	}
}
