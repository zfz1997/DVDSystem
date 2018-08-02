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
import com.iotek.biz.impl.DVDBizImpl;
import com.iotek.entity.DVD;
import com.iotek.entity.User;

public class UserQueryRentDVDView extends JInternalFrame {

	
	private static final long serialVersionUID = -110469428283980901L;
    private JPanel paneltable=null;//用来保存Jtable的一个面板
    private JTable table=null;//声明Jtable
    private JPanel panelButton=null;//按钮面板
    private JButton btn_search=null;
    private JButton btn_rent=null;
    private JButton btn_exit=null;
    private JComboBox<String> cb_type=null;
    private JLabel lb_type=null;
    private InfoTableDVDList DVD=null;
    private List<DVD> DVDList=null;
    private DVDBiz dvdBiz=null;
    private User user=null;
    private JPanel panelContent=null;
    private JLabel lb_uid=null;
    private JTextField tf_uid=null;
    private JLabel lb_did=null;
    private JTextField tf_did=null;
    
    public UserQueryRentDVDView(User user){
    	init();
    	registerListener();
    	dvdBiz=new DVDBizImpl();
    	this.user=user;
    }
    private void init(){
    	this.setTitle("DVD信息查询");
    	this.setSize(800,500);
    	this.setIconifiable(true);//窗体可最小化
    	this.setClosable(true);//窗体可关闭
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	table=new JTable();
    	refreshTable(new ArrayList<DVD>());
    	paneltable=new JPanel(new BorderLayout());//创建面板
    	//给面板设置边框
    	paneltable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(null,null),"查询信息"));
    	paneltable.add(new JScrollPane(table),BorderLayout.CENTER);
    	this.add(paneltable,BorderLayout.CENTER);
  
    	panelContent=new JPanel(new GridLayout(1,6));
    	lb_uid=new JLabel("用户id");
    	tf_uid=new JTextField();
    	lb_did=new JLabel("影碟id");
    	tf_did=new JTextField();
    	
    	panelContent.add(lb_uid);
    	panelContent.add(tf_uid);
    	panelContent.add(lb_did);
    	panelContent.add(tf_did);
    	
    	paneltable.add(panelContent,BorderLayout.SOUTH);
    	this.add(paneltable,BorderLayout.CENTER);
    	
    	
    	
    	panelButton=new JPanel(new GridLayout(7, 1,10,30));
    	panelButton.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(null,null),"查询条件"));
    	this.add(panelButton, BorderLayout.EAST);
    	lb_type=new JLabel("查询类型");
    	panelButton.add(lb_type);
    	cb_type=new JComboBox<String>(new String[] {"全部DVD","可借DVD","已借DVD","热门DVD"});
    	panelButton.add(cb_type);
    	btn_search=new JButton("查询");
    	panelButton.add(btn_search);
    	btn_rent=new JButton("租DVD");
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
				int flag=JOptionPane.showConfirmDialog(UserQueryRentDVDView.this, 
						"是否确定借DVD","确认信息",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					int flag2=dvdBiz.lendDVD(Integer.parseInt(tf_did.getText()),Integer.parseInt(tf_uid.getText()));
					if(flag2==0) {
						JOptionPane.showMessageDialog(
								UserQueryRentDVDView.this, "没有找到要借的DVD");
					}else if(flag2==1) {
						JOptionPane.showMessageDialog(
								UserQueryRentDVDView.this, "对不起，该DVD已经借出");
					}else if(flag2==2) {
						JOptionPane.showMessageDialog(
								UserQueryRentDVDView.this, "借DVD成功");
					}else {
						JOptionPane.showMessageDialog(
								UserQueryRentDVDView.this, "借DVD失败，请联系管理员");
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
    			String did=table.getValueAt(row, 0).toString();
    			String uid=Integer.toString(user.getId());
    			tf_uid.setText(uid);
    			tf_did.setText(did);
    		}
		});
    	btn_exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int flag=JOptionPane.showConfirmDialog(UserQueryRentDVDView.this, 
						"是否确定退出","确认信息",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					UserQueryRentDVDView.this.dispose();
				}
				
			}
		});
    	btn_search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index=cb_type.getSelectedIndex();
				if(index==0) {
					DVDList=dvdBiz.queryAllDVDS();
					refreshTable(DVDList);
				}
				else if(index==1) {
					DVDList=dvdBiz.canLendDVD();
					refreshTable(DVDList);
				}
				else if(index==2) {
					DVDList=dvdBiz.hasLendedDVD();
					refreshTable(DVDList);
				}
				else {
					DVDList=dvdBiz.ranking_top_five();
					refreshTable(DVDList);
				}
			}
		});
    }
    private class InfoTableDVDList implements TableModel{
    	private List<DVD> DVDList=null;
    	public InfoTableDVDList(List<DVD> DVDList) {
    		this.DVDList=DVDList;
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
			return 4;
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if(columnIndex==0) {
				return "影碟ID";
			}else if(columnIndex==1) {
				return "影碟名字";
			}else if(columnIndex==2) {
				return "借出次数";
			}else if(columnIndex==3){
			return "状态";
			}else
			{
				return "出错";
			}
		}
		//JTable显示的数据行数
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return DVDList.size();
		}
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			DVD Dvd=DVDList.get(rowIndex);
			if(columnIndex==0) {
				return Dvd.getId();
			}else if(columnIndex==1) {
				return Dvd.getDname();
			}else if(columnIndex==2) {
				return Dvd.getDcount();
			}else if(columnIndex==3){
				return ""+(Dvd.getStatus()==1?"可借":"已借");
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
    private void refreshTable(List<DVD> DVDList) {
    	DVD=new InfoTableDVDList(DVDList);
    	table.setModel(DVD);
    }
}
