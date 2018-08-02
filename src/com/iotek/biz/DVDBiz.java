package com.iotek.biz;

import java.util.List;

import com.iotek.entity.DVD;

public interface DVDBiz {
    public boolean addDVD(DVD dvd);//添加DVD
    public boolean delDVD(int did);//删除DVD
    public boolean modifyDVD(DVD dvd);//修改DVD
    public List<DVD> queryAllDVDS();//查询所有的DVD信息
    public List<DVD> ranking_top_five();//查看前5张最受欢迎的DVD(热门DVD)
    public List<DVD> queryDVDByName(String dname);//根据DVD的名字来查询DVD
    public DVD queryDVDById(int did);//根据ID查询dvd
    public int lendDVD(int did,int uid);//按dvd编号和用户编号来租DVD
    public int returnDVD(int rid);//还DVD的功能
    public List<DVD> canLendDVD();//可借DVD
    public List<DVD> hasLendedDVD();//不可借DVD
}
