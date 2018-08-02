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
		DVD dvd=dvdDao.queryDVDById(did);//��ѯ
		if(dvd==null) {
			return 0;//û���ҵ�Ҫ���DVD
		}else {
			if(dvd.getStatus()==0) {
				return 1;//���ɽ�(�Ѿ����)
			}else {
				dvd.setStatus(0);//����״̬�������Ѿ����
				dvd.setDcount(dvd.getDcount()+1);
			boolean flag1=dvdDao.updateDVD(dvd);//����DVD
			Record record=new Record(uid,dvd.getId(),new SimpleDateFormat("yyyy-MM-dd").format(new Date()),null);
			boolean flag2=recordDao.saveRecord(record);
			if(flag1&&flag2) {
				return 2;//����ɹ�
			}else {
				return 3;//���ʧ��
			}
			}
		}
		
		
	}

	@Override
	public int returnDVD(int rid) {
		Record record=recordDao.queryRecordById(rid);
		if(record==null) {
			return 1;//���벻��ȷ
		}else if(record.getReturnTime()!=null) {
			return 2;//dvd�Ѿ��黹��
		}else {
			record.setReturnTime(new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date()));
			boolean flag1=recordDao.updateRecord(record);//���¼�¼��
			DVD dvd=dvdDao.queryDVDById(record.getDid());//�ҵ���Ӧ��DVD
			dvd.setStatus(1);//�ɽ�״̬
			boolean flag2=dvdDao.updateDVD(dvd);//����DVD
			if(flag1&&flag2) {
				return 3;//�黹�ɹ�
			}else {
				return 4;//�黹ʧ��
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
