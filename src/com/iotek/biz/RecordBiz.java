package com.iotek.biz;

import java.util.List;

import com.iotek.entity.Record;
import com.iotek.entity.Record2;

public interface RecordBiz {
    //�鿴ָ���û������޼�¼ 
	public List<Record2> queryUserRecords(String uname);
	//�鿴ָ��DVD�����޼�¼
	public List<Record2> queryDVDRecords(String dname);
	
	//�鿴ָ���û����ѹ黹��¼
	public List<Record2> queryHasReturnedRecords(String uname);
	//�鿴ָ���û���δ�黹��¼
	public List<Record2> queryNoReturnedRecords(String uname);
	//�鿴���еļ�¼
	public List<Record2> queryAllRecords();
}
