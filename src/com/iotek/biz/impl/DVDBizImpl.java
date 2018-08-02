package com.iotek.biz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.iotek.biz.DVDBiz;
import com.iotek.dao.DVDDao;
import com.iotek.dao.RecordDao;
import com.iotek.dao.impl.DVDDaoImpl;
import com.iotek.dao.impl.RecordDaoImpl;
import com.iotek.entity.DVD;
import com.iotek.entity.Record;

public class DVDBizImpl implements DVDBiz {
    private DVDDao dvdDao=null;
    private RecordDao recordDao=null;
    public DVDBizImpl() {
    	dvdDao=new DVDDaoImpl();
    	recordDao=new RecordDaoImpl();
    }
	@Override
	public boolean addDVD(DVD dvd) {
		// TODO Auto-generated method stub
		return dvdDao.saveDVD(dvd);
	}

	@Override
	public boolean delDVD(int did) {
		// TODO Auto-generated method stub
		return dvdDao.deleteDVD(did);
	}

	@Override
	public boolean modifyDVD(DVD dvd) {
		// TODO Auto-generated method stub
		return dvdDao.updateDVD(dvd);
	}

	@Override
	public List<DVD> queryAllDVDS() {
		// TODO Auto-generated method stub
		return dvdDao.queryDVDs();
	}

	@Override
	public List<DVD> ranking_top_five() {
		// TODO Auto-generated method stub
		return dvdDao.querySortDVDByLimit(0, 5);
	}

	@Override
	public List<DVD> queryDVDByName(String dname) {
		// TODO Auto-generated method stub
		return dvdDao.queryDVDByName(dname);
	}

	@Override
	public DVD queryDVDById(int did) {
		// TODO Auto-generated method stub
		return dvdDao.queryDVDById(did);
	}

	@Override
	public int lendDVD(int did, int uid) {
		DVD dvd=dvdDao.queryDVDById(did);//查询
		if(dvd==null) {
			return 0;//没有找到要借的DVD
		}else {
			if(dvd.getStatus()==0) {
				return 1;//不可借(已经借出)
			}else {
				dvd.setStatus(0);//更新状态，代表已经借出
				dvd.setDcount(dvd.getDcount()+1);
			boolean flag1=dvdDao.updateDVD(dvd);//更新DVD
			Record record=new Record(uid,dvd.getId(),new SimpleDateFormat("yyyy-MM-dd").format(new Date()),null);
			boolean flag2=recordDao.saveRecord(record);
			if(flag1&&flag2) {
				return 2;//借出成功
			}else {
				return 3;//借出失败
			}
			}
		}
		
		
	}

	@Override
	public int returnDVD(int rid) {
		Record record=recordDao.queryRecordById(rid);
		if(record==null) {
			return 1;//输入不正确
		}else if(record.getReturnTime()!=null) {
			return 2;//dvd已经归还了
		}else {
			record.setReturnTime(new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date()));
			boolean flag1=recordDao.updateRecord(record);//更新记录表
			DVD dvd=dvdDao.queryDVDById(record.getDid());//找到对应的DVD
			dvd.setStatus(1);//可借状态
			boolean flag2=dvdDao.updateDVD(dvd);//更新DVD
			if(flag1&&flag2) {
				return 3;//归还成功
			}else {
				return 4;//归还失败
			}
		}
		
	}

	@Override
	public List<DVD> canLendDVD() {
		// TODO Auto-generated method stub
		return dvdDao.queryDVDByStatus(1);
	}

	@Override
	public List<DVD> hasLendedDVD() {
		// TODO Auto-generated method stub
		return dvdDao.queryDVDByStatus(0);
	}

}
