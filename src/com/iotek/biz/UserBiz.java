package com.iotek.biz;

import com.iotek.entity.User;

public interface UserBiz {
    //用户登录,返回的就是登录用户的信息(对象)  
	public User login(User user);
	//注册用户
    public int registerUser(User user);
}
