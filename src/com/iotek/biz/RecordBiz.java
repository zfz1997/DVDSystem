package com.iotek.biz;

import java.util.List;

import com.iotek.entity.Record;
import com.iotek.entity.Record2;

public interface RecordBiz {
    //查看指定用户的租赁记录 
	public List<Record2> queryUserRecords(String uname);
	//查看指定DVD的租赁记录
	public List<Record2> queryDVDRecords(String dname);
	
	//查看指定用户的已归还记录
	public List<Record2> queryHasReturnedRecords(String uname);
	//查看指定用户的未归还记录
	public List<Record2> queryNoReturnedRecords(String uname);
	//查看所有的记录
	public List<Record2> queryAllRecords();
}
