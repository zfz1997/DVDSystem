package com.iotek.biz.impl;

import java.util.List;

import com.iotek.biz.RecordBiz;
import com.iotek.dao.RecordDao;
import com.iotek.dao.impl.RecordDaoImpl;
import com.iotek.entity.Record;
import com.iotek.entity.Record2;

public class RecordBizImpl implements RecordBiz {
    private RecordDao recordDao=null;
    public RecordBizImpl() {
    	recordDao=new RecordDaoImpl();
    }
	@Override
	public List<Record2> queryUserRecords(String uname) {
		// TODO Auto-generated method stub
		return recordDao.queryRecordByUname(uname);
	}

	public List<Record2> queryDVDRecords(String dname) {
		// TODO Auto-generated method stub
		return recordDao.queryRecordByDname(dname);
	}

	public List<Record2> queryHasReturnedRecords(String uname) {
		// TODO Auto-generated method stub
		return recordDao.queryUserRecordByReturnTime(true, uname);
	}

	public List<Record2> queryNoReturnedRecords(String uname) {
		// TODO Auto-generated method stub
		return recordDao.queryUserRecordByReturnTime(false, uname);
	}

	public List<Record2> queryAllRecords() {
		// TODO Auto-generated method stub
		return recordDao.queryAllRecords();
	}

}
