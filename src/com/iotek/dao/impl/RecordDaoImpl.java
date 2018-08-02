package com.iotek.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.iotek.dao.RecordDao;
import com.iotek.entity.Record;
import com.iotek.entity.Record2;

public class RecordDaoImpl extends BaseDao implements RecordDao {

	@Override
	public Record queryRecordById(int rid) {
		String sql="select id,uid,did,lendTime,returnTime from records where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(rid);
		List<Record> rList=null;
		try {
			rList=this.operQuery(sql, params, Record.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(rList.size()>0) {
			return rList.get(0);
		}
		return null;
	}

	@Override
	public boolean saveRecord(Record record) {
		String sql="insert into records(uid,did,lendTime,returnTime)values(?,?,?,?)";
		List<Object> params=new ArrayList<Object>();
		params.add(record.getUid());
		params.add(record.getDid());
		params.add(record.getLendTime());
		params.add(record.getReturnTime());
		return this.operUpdate(sql, params);
	}

	@Override
	public boolean updateRecord(Record record) {
		String sql="update records set uid=?,did=?,lendTime=?,returnTime=? where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(record.getUid());
		params.add(record.getDid());
		params.add(record.getLendTime());
		params.add(record.getReturnTime());
		params.add(record.getId());
		return this.operUpdate(sql, params);
	}

	@Override
	public List<Record2> queryAllRecords() {
		List<Record2> data=null;
		String sql="select r.id,d.id as did,u.uname,d.dname,r.lendTime,r.returnTime from"
				+"users u,dvds d,records r where u.id=r.uid and d.id=r.did";
		try {
			data=this.operQuery(sql, null, Record2.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
				
		return data;
	}

	@Override
	public List<Record2> queryRecordByUname(String uname) {
		List<Record2> data=null;
		String sql="select r.id,d.id as did,u.uname,d.dname,r.lendTime,r.returnTime from users u,dvds d,records r where u.id=r.uid and d.id=r.did and uname=?";
		List<Object>params=new ArrayList<Object>();
		params.add(uname);
		try {
			data=this.operQuery(sql, params, Record2.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public List<Record2> queryRecordByDname(String dname) {
		List<Record2> data=null;
		String sql="select r.id,d.id as did,u.uname,d.dname,r.lendTime,r.returnTime from users u,dvds d,records r where u.id=r.uid and d.id=r.did and dname=?";
		List<Object>params=new ArrayList<Object>();
		params.add(dname);
		try {
			data=this.operQuery(sql, params, Record2.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
    /**
     * 查看用户的借还记录（已归还或者未归还记录）
     */
	@Override
	public List<Record2> queryUserRecordByReturnTime(boolean flag, String uname) {
		List<Record2> rList=null;
		String sql=null;
		if(flag) {
			sql="select r.id,d.id as did,u.uname,d.dname,r.lendTime,r.returnTime from users u,dvds d,records r where u.id=r.uid and d.id=r.did and returnTime is not null and uname=?";
		}else {
			sql="select r.id,d.id as did,u.uname,d.dname,r.lendTime,r.returnTime from users u,dvds d,records r where u.id=r.uid and d.id=r.did and returnTime is null and uname=?";
		}
		List<Object> params=new ArrayList<Object>();
		params.add(uname);
		try {
			rList=this.operQuery(sql, params, Record2.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rList;
	}

}
