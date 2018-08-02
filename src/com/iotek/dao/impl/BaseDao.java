package com.iotek.dao.impl;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class BaseDao {
     private static final String DRIVER="com.mysql.jdbc";
     private static final String URL="jdbc:mysql://127.0.0.1:3306/data?useUnicode=true&characterEncoding=utf-8&useSSL=false";
     String user="root";
     String password="zhangfeng8751";
     /**
      * 与数据库建立连接
      * @return 数据库连接对象
      */
     public Connection getConn() {
    	    Connection conn=null;
    	    try {
    	    conn= (Connection) DriverManager.getConnection(URL,user,password);
    	    }catch(SQLException e) {
    	    	e.printStackTrace();
    	    }
    	    return conn;
     }
     /**
      * 释放相应的资源
      * @param rs
      * @param pstmt
      * @param conn
      */
     public void closeAll(ResultSet rs,PreparedStatement pstmt,Connection conn) {
    	 try{
    		 if(rs!=null) { 
    			 rs.close();
    	 }
    		
    	 
    	 if(pstmt!=null) {
    		 pstmt.close();
    	 }
    	 if(conn!=null) {
    		 conn.close();
    	 }
     }catch(SQLException e) {
    	 e.printStackTrace();
     }
}
     /**
      * 此方法可以完成增删改所有的操作
      * @param sql
      * @param params
      * @return true或false
      */
     public boolean operUpdate(String sql,List<Object> params) {
    	  int res=0;//影响的行数
    	  Connection conn=null;
    	  PreparedStatement pstmt=null;
    	  ResultSet rs=null;
    	  try {
			conn=getConn();//建立数据库连接
			  pstmt=(PreparedStatement) conn.prepareStatement(sql);//装载sql语句
			  if(params!=null) {
				  //加入有?,在执行之前把?占位符替换掉
				  for(int i=0;i<params.size();i++) {
					  pstmt.setObject(i+1, params.get(i));
				  }
			  }
			  res=pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeAll(rs,pstmt,conn);
		}
    	  return res>0? true:false;
     }
     /**
      * 使用泛型方法和反射机制进行封装
      * @param sql
      * @param params
      * @param cls
      * @return
      */
     @SuppressWarnings("deprecation")
	public <T> List<T> operQuery(String sql,List<Object> params,Class<T> cls)throws Exception{
    	 
    	 Connection conn=null;
   	     PreparedStatement pstmt=null;
   	     ResultSet rs=null;
   	     List<T> data=new ArrayList<T>();
   	  try {
			conn=getConn();//建立数据库连接
			  pstmt=(PreparedStatement) conn.prepareStatement(sql);//装载sql语句
			  if(params!=null) {
				  //加入有?,在执行之前把?占位符替换掉
				  for(int i=0;i<params.size();i++) {
					  pstmt.setObject(i+1, params.get(i));
				  }
			  }
			  rs=pstmt.executeQuery();
			  //把查询出来的记录封装成对应的实体类对象
			  java.sql.ResultSetMetaData rsd=rs.getMetaData();//得到记录集元数据对象
			  //通过此对象可以得到表的结构，包括列名列的个数列的数据类型
			  while(rs.next()) {
				  T m=cls.newInstance();
				  for(int i=0;i<rsd.getColumnCount();i++) {
					  String col_name=rsd.getColumnName(i+1);//获得列名
					  Object value=rs.getObject(col_name);//获得列所对应的值
					  java.lang.reflect.Field field=cls.getDeclaredField(col_name);
					  field.setAccessible(true);//给私有属性设置可访问权
					  field.set(m,value);//给对象的私有属性赋值
				  }
				  data.add(m);
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeAll(rs,pstmt,conn);
		}return data;
     }
     
     
}
