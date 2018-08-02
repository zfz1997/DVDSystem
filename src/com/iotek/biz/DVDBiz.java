package com.iotek.biz;

import java.util.List;

import com.iotek.entity.DVD;

public interface DVDBiz {
    public boolean addDVD(DVD dvd);//���DVD
    public boolean delDVD(int did);//ɾ��DVD
    public boolean modifyDVD(DVD dvd);//�޸�DVD
    public List<DVD> queryAllDVDS();//��ѯ���е�DVD��Ϣ
    public List<DVD> ranking_top_five();//�鿴ǰ5�����ܻ�ӭ��DVD(����DVD)
    public List<DVD> queryDVDByName(String dname);//����DVD����������ѯDVD
    public DVD queryDVDById(int did);//����ID��ѯdvd
    public int lendDVD(int did,int uid);//��dvd��ź��û��������DVD
    public int returnDVD(int rid);//��DVD�Ĺ���
    public List<DVD> canLendDVD();//�ɽ�DVD
    public List<DVD> hasLendedDVD();//���ɽ�DVD
}
