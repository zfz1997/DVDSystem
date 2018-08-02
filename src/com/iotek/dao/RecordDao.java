package com.iotek.dao;

import java.util.List;

import com.iotek.entity.Record;
import com.iotek.entity.Record2;

public interface RecordDao {
    public Record queryRecordById(int rid);//�鿴ָ��id��DVD�軹��¼
    //����Record��¼
    public boolean saveRecord(Record record);
    //����Record��¼
    public boolean updateRecord(Record record);
    
    public List<Record2> queryAllRecords();//��ѯ���е�DVD
    
    public List<Record2> queryRecordByUname(String uname);//�鿴ָ���û���dvd�軹��¼
    
    public List<Record2> queryRecordByDname(String dname);//�鿴ָ��dvd�Ľ軹��¼
    
    //�鿴�û��Ĺ黹��¼(�ѹ黹��δ�黹)
    public List<Record2> queryUserRecordByReturnTime(boolean flag,String uname);
}
