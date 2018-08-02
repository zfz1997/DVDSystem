package com.iotek.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.iotek.biz.DVDBiz;
import com.iotek.biz.RecordBiz;
import com.iotek.biz.impl.DVDBizImpl;
import com.iotek.biz.impl.RecordBizImpl;
import com.iotek.entity.Record2;
import com.iotek.entity.User;

public class UserQueryDVDRecordView extends JInternalFrame{

	
	private static final long serialVersionUID = 8806725341151613867L;
	
    private JPanel paneltable=null;//用来保存Jtable的一个面板
    private JTable table=null;//声明Jtable
    private JPanel panelButton=null;//按钮面板
    private JButton btn_search=null;
    private JButton btn_rent=null;
    private JButton btn_exit=null;
    private JComboBox<String> cb_type=null;
    private JLabel lb_type=null;
    private DVDInfoTableRecord dvdInfoTableRecord=null;
    private List<Record2> record2List=null;
    private User user=null;
    private RecordBiz recordBiz=null;
    private String rid=null;
    private DVDBiz dvdBiz=null;
    public UserQueryDVDRecordView(User user){
    	init();
    	this.user=user;
    	registerListener();
    	recordBiz=new RecordBizImpl();
    	dvdBiz=new DVDBizImpl();
    }
    private void init(){
    	this.setTitle("DVD租赁记录查询");
    	this.setSize(800,500);
    	this.setIconifiable(true);//窗体可最小化
    	this.setClosable(true);//窗体可关闭
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	record2List=new ArrayList<Record2>();
    	table=new JTable();
    	refreshTable(record2List);
    	paneltable=new JPanel(new BorderLayout());//创建面板
    	//给面板设置边框
    	paneltable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(null,null),"本人租赁记录查询"));
    	paneltable.add(new JScrollPane(table),BorderLayout.CENTER);
    	this.add(paneltable,BorderLayout.CENTER);
    	
    	panelButton=new JPanel(new GridLayout(7, 1,10,30));
    	panelButton.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(null,null),"查询条件"));
    	this.add(panelButton, BorderLayout.EAST);
    	lb_type=new JLabel("查询类型");
    	panelButton.add(lb_type);
    	cb_type=new JComboBox<String>(new String[] {"全部租赁记录","已归还记录","未归还记录"});
    	panelButton.add(cb_type);
    	btn_search=new JButton("查询");
    	panelButton.add(btn_search);
    	btn_rent=new JButton("还DVD");
    	btn_rent.setEnabled(false);//默认不可用
    	panelButton.add(btn_rent);
    	panelButton.add(new JLabel());
    	panelButton.add(new JLabel());
    	btn_exit=new JButton("退出窗口");
    	panelButton.add(btn_exit);
    	this.setVisible(true);
    	
    	
    }     
    private void registerListener() {
    	btn_rent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int flag2=JOptionPane.showConfirmDialog(UserQueryDVDRecordView.this, 
						"是否确定归还此DVD","确认信息",JOptionPane.YES_NO_OPTION);
				if(flag2==JOptionPane.YES_OPTION) { 
				int flag=dvdBiz.returnDVD(Integer.parseInt(rid));
				if(flag==1) {
					JOptionPane.showMessageDialog(
							UserQueryDVDRecordView.this, "输入错误");
				}else if(flag==2) {
					JOptionPane.showMessageDialog(
							UserQueryDVDRecordView.this, "DVD已经归还过了");
				}else if(flag==3) {
					JOptionPane.showMessageDialog(
							UserQueryDVDRecordView.this, "归还成功");
				}else {
					JOptionPane.showMessageDialog(
							UserQueryDVDRecordView.this, "归还失败，请联系管理员");
				}
				}
			}
		});
    	table.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			if(table.getSelectedRow()!=-1) {
    				btn_rent.setEnabled(true);
    				
    			}
    			int row=table.getSelectedRow();//得到你所选中那行的下标
    		    rid=table.getValueAt(row, 0).toString();
    			
    		
    		}
		});
    	btn_exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int flag=JOptionPane.showConfirmDialog(UserQueryDVDRecordView.this, 
						"是否确定退出","确认信息",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					UserQueryDVDRecordView.this.dispose();
					
				
			}
			}
		});
    	btn_search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index=cb_type.getSelectedIndex();
				if(index==0) {
					record2List=recordBiz.queryUserRecords(user.getUname());
					refreshTable(record2List);
				}else if(index==1) {
					record2List=recordBiz.queryHasReturnedRecords(user.getUname());
					refreshTable(record2List);
				}else if(index==2){
					record2List=recordBiz.queryNoReturnedRecords(user.getUname());
					refreshTable(record2List);
				}
			}
		});
    }
    private class DVDInfoTableRecord implements TableModel{
    	private List<Record2> record2List=null;
    	public DVDInfoTableRecord(List<Record2> record2List) {
    		this.record2List=record2List;
    	}
		@Override
		public void addTableModelListener(TableModelListener arg0) {
			// TODO Auto-generated method stub
			
		}
		//JTable列的数据类型
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// TODO Auto-generated method stub
			return String.class;
		}
		//JTable数据的列数
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 6;
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if(columnIndex==0) {
				return "记录ID";
			}else if(columnIndex==1) {
				return "影碟ID";
			}else if(columnIndex==2) {
				return "用户名字";
			}else if(columnIndex==3) {
				return "影碟名字";
			}else if(columnIndex==4){
				return "借出时间";
			}else if(columnIndex==5) {
			    return "归还时间";
			}
			else{
			
			return "出错";
			}
		}
		//JTable显示的数据行数
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return record2List.size();
		}
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Record2 record2=record2List.get(rowIndex);
			if(columnIndex==0) {
				return record2.getId();
			}else if(columnIndex==1) {
				return record2.getDid();
			}else if(columnIndex==2) {
				return record2.getUname();
			}else if(columnIndex==3) {
				return record2.getDname();
			}else if(columnIndex==4) {
				return record2.getLendTime();
			}else if(columnIndex==5) {
				return record2.getReturnTime();
			}
				
			else {
				return "出错";
			}
			
		}
		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public void removeTableModelListener(TableModelListener arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void setValueAt(Object arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    //刷新JTable并显示数据
    private void refreshTable(List<Record2> record2List) {
    	dvdInfoTableRecord=new DVDInfoTableRecord(record2List);
    	table.setModel(dvdInfoTableRecord);
    }
}
