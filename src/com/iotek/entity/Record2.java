package com.iotek.entity;
//ָ���û���¼��ѯ
public class Record2 {
   private int id;//��¼id
   private int did;//Ӱ��id
   private String uname;//�û���
   private String dname;//Ӱ������
   private String lendTime;//���ʱ��
   private String returnTime;//�黹ʱ��
   public Record2() {
	   
   }
public Record2(String uname, String dname, String lendTime, String returnTime) {
	super();
	this.uname = uname;
	this.dname = dname;
	this.lendTime = lendTime;
	this.returnTime = returnTime;
}
public Record2(int id, String uname, String dname, String lendTime, String returnTime) {
	super();
	this.id = id;
	this.uname = uname;
	this.dname = dname;
	this.lendTime = lendTime;
	this.returnTime = returnTime;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getDid() {
	return did;
}
public void setDid(int did) {
	this.did = did;
}
public String getUname() {
	return uname;
}
public void setUname(String uname) {
	this.uname = uname;
}
public String getDname() {
	return dname;
}
public void setDname(String dname) {
	this.dname = dname;
}
public String getLendTime() {
	return lendTime;
}
public void setLendTime(String lendTime) {
	this.lendTime = lendTime;
}
public String getReturnTime() {
	return returnTime;
}
public void setReturnTime(String returnTime) {
	this.returnTime = returnTime;
}
   
}
