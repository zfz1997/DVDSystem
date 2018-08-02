package com.iotek.dao;

import java.util.List;

import com.iotek.entity.DVD;

public interface DVDDao {
     public boolean saveDVD(DVD dvd);//���DVD
     public boolean updateDVD(DVD dvd);//����DVD
     public boolean deleteDVD(int did);//ɾ��ָ����DVD
     public List<DVD> queryDVDs();//��ѯ���е�DVD
     public List<DVD> queryDVDByName(String dname);//����ָ�����ֵ�DVD
     public List<DVD> querySortDVDByLimit(int index,int number);//����ָ����ʼλ�ã�ǰ����DVD
     public DVD queryDVDById(int did);//����dvd�������ѯDVD
     public List<DVD> queryDVDByStatus(int status);//����״̬��ѯDVD
     
}
