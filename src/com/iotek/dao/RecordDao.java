package com.iotek.dao;

import java.util.List;

import com.iotek.entity.Record;
import com.iotek.entity.Record2;

public interface RecordDao {
    public Record queryRecordById(int rid);//查看指定id的DVD借还记录
    //保存Record记录
    public boolean saveRecord(Record record);
    //更新Record记录
    public boolean updateRecord(Record record);
    
    public List<Record2> queryAllRecords();//查询所有的DVD
    
    public List<Record2> queryRecordByUname(String uname);//查看指定用户的dvd借还记录
    
    public List<Record2> queryRecordByDname(String dname);//查看指定dvd的借还记录
    
    //查看用户的归还记录(已归还，未归还)
    public List<Record2> queryUserRecordByReturnTime(boolean flag,String uname);
}
