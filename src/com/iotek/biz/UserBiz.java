package com.iotek.biz;

import com.iotek.entity.User;

public interface UserBiz {
    //�û���¼,���صľ��ǵ�¼�û�����Ϣ(����)  
	public User login(User user);
	//ע���û�
    public int registerUser(User user);
}
