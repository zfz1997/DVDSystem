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
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.iotek.biz.DVDBiz;
import com.iotek.biz.RecordBiz;
import com.iotek.biz.impl.DVDBizImpl;
import com.iotek.biz.impl.RecordBizImpl;
import com.iotek.entity.Record2;

public class AdminQueryDVDRecordView extends JInternalFrame{

	
	private static final long serialVersionUID = 473243144770347755L;
	 private JPanel paneltable=null;//用来保存Jtable的一个面板
	    private JTable table=null;//声明Jtable
	    private JPanel panelButton=null;//按钮面板
	    private JButton btn_search=null;
	    private JButton btn_rent=null;
	    private JButton btn_exit=null;
	    private JComboBox<String> cb_type=null;
	    private JLabel lb_type=null;
	    private JTextField tf_search=null;
	    private InfoTableModelByUser byUser=null;
	   
	    private List<Record2> record2List=null;
	    
	    private RecordBiz recordBiz=null;
	    private String rid=null;
	    private DVDBiz dvdBiz=new DVDBizImpl();
	    public AdminQueryDVDRecordView(){
	    	init();
	    	registerListener();
	    	recordBiz=new RecordBizImpl();
	    }
	    private void init(){
	    	this.setTitle("DVD租赁记录查询");
	    	this.setSize(800,500);
	    	this.setIconifiable(true);//窗体可最小化
	    	this.setClosable(true);//窗体可关闭
	    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    	table=new JTable();
	    	refreshTable(new ArrayList<Record2>());
	    	
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
	    	cb_type=new JComboBox<String>(new String[] {"指定用户租赁记录","指定DVD租赁记录"});
	    	panelButton.add(cb_type);
	    	tf_search=new JTextField();
	    	panelButton.add(tf_search);
	    	btn_search=new JButton("查询");
	    	panelButton.add(btn_search);
	    	btn_rent=new JButton("还DVD");
	    	btn_rent.setEnabled(false);//默认不可用
	    	panelButton.add(btn_rent);
	    	
	    	panelButton.add(new JLabel());
	    	btn_exit=new JButton("退出窗口");
	    	panelButton.add(btn_exit);
	    	this.setVisible(true);
	    	
	    	
	    } 
	    private void registerListener() {
	    	btn_rent.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int flag=JOptionPane.showConfirmDialog(AdminQueryDVDRecordView.this, 
							"是否确定归还DVD","确认信息",JOptionPane.YES_NO_OPTION);
					if(flag==JOptionPane.YES_OPTION) {
						int flag2=dvdBiz.returnDVD(Integer.parseInt(rid));
						if(flag2==1) {
							JOptionPane.showMessageDialog(
									AdminQueryDVDRecordView.this, "输入错误");
						}else if(flag2==2) {
							JOptionPane.showMessageDialog(
									AdminQueryDVDRecordView.this, "dvd已经归还了");
						}else if(flag2==3) {
							JOptionPane.showMessageDialog(
									AdminQueryDVDRecordView.this, "归还成功");
						}else {
							JOptionPane.showMessageDialog(
									AdminQueryDVDRecordView.this, "归还失败，请联系管理员！");
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
	    	btn_search.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int index=cb_type.getSelectedIndex();
					String content=tf_search.getText().trim();
					if(content.equals("")) {
						JOptionPane.showMessageDialog(
								AdminQueryDVDRecordView.this, "查询内容不能为空");
						return;
					}
					if(record2List!=null) {
						record2List.clear();
					}
					
					if(index==0) {
						record2List=recordBiz.queryUserRecords(content);
						refreshTable(record2List);
					}else if(index==1) {
	                  
	                	   record2List=recordBiz.queryDVDRecords(content);
	                	   refreshTable(record2List);
	                	   
                      }
				}
			});
	    	btn_exit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int flag=JOptionPane.showConfirmDialog(AdminQueryDVDRecordView.this, 
							"是否确定退出","确认信息",JOptionPane.YES_NO_OPTION);
					if(flag==JOptionPane.YES_OPTION) {
						AdminQueryDVDRecordView.this.dispose();
					}
					
				}
			});
	    	
	    }//指定用户
	    private class InfoTableModelByUser implements TableModel{
	    	private List<Record2> Record2List=null;
	    	public InfoTableModelByUser(List<Record2> Record2List) {
	    		this.Record2List=Record2List;
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
				return 5;
			}
			
			@Override
			public String getColumnName(int columnIndex) {
				if(columnIndex==0) {
					return "记录ID";
				}else if(columnIndex==1) {
					return "用户名";
				}else if(columnIndex==2) {
					return "影碟名字";
				}else if(columnIndex==3){
				return "借出时间";
				}else if(columnIndex==4) {
					return "归还时间";
				}else
				{
					return "出错";
				}
			}
			//JTable显示的数据行数
			@Override
			public int getRowCount() {
				// TODO Auto-generated method stub
				return Record2List.size();
			}
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Record2 record2=Record2List.get(rowIndex);
				if(columnIndex==0) {
					return record2.getId();
				}else if(columnIndex==1) {
					return record2.getUname();
				}else if(columnIndex==2) {
					return record2.getDname();
				}else if(columnIndex==3){
					return record2.getLendTime();
				}else if(columnIndex==4) {
					return record2.getReturnTime();
				}else {
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
	    private void refreshTable(List<Record2> Record2List) {
	    	byUser=new InfoTableModelByUser(Record2List);
	    	table.setModel(byUser);
	    }
}
	    