package com.iotek.dao;

import java.util.List;

import com.iotek.entity.DVD;

public interface DVDDao {
     public boolean saveDVD(DVD dvd);//添加DVD
     public boolean updateDVD(DVD dvd);//更新DVD
     public boolean deleteDVD(int did);//删除指定的DVD
     public List<DVD> queryDVDs();//查询所有的DVD
     public List<DVD> queryDVDByName(String dname);//查找指定名字的DVD
     public List<DVD> querySortDVDByLimit(int index,int number);//查找指定起始位置，前几的DVD
     public DVD queryDVDById(int did);//根据dvd编号来查询DVD
     public List<DVD> queryDVDByStatus(int status);//根据状态查询DVD
     
}
