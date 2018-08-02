package com.iotek.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import com.iotek.util.DVDUtil;

public class AdminDVDOperationView extends JInternalFrame{

	
	private static final long serialVersionUID = 7075566484521398119L;
	 private JPanel paneltable=null;//��������Jtable��һ�����
	    private JTable table=null;//����Jtable
	    private JPanel panelButton=null;//��ť���
	    private JPanel panelContent=null;//�������
	    private JButton btn_search=null;
	    private JButton btn_exit=null;
	    private JLabel lb_type=null;
	    private JComboBox<String> cb_type=null;
	    private JTextField tf_search=null;
	    //��������label
	    private JLabel lb_l=null;
	    
	    //���ɾ���޸���ʹ�õĿؼ�
	    private JLabel lb_name=null;
	    private JLabel lb_count=null;
	    private JLabel lb_status=null;
	    
	    private JTextField tf_name=null;
	    private JTextField tf_count=null;
	    private JComboBox<String> cb_status=null;
	    
	    private JButton btn_add=null;
	    private JButton btn_update=null;
	    private JButton btn_del=null;
	    
	    private DVDBiz dvdBiz=null;
	    private List<DVD> dvdList=null;
	    private DVDInfoTableModel infoTableModel=null;
	    public AdminDVDOperationView(){
	    	init();
	    	registerListener();
	    	dvdBiz=new DVDBizImpl();
	    }
	    private void init(){
	    	this.setTitle("����ԱDVD����");
	    	this.setSize(800,500);
	    	this.setIconifiable(true);//�������С��
	    	this.setClosable(true);//����ɹر�
	    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    	dvdList=new ArrayList<DVD>();
	    	table=new JTable();
	    	//��JTable������ģ�ͳ�������
	    	refreshTable(dvdList);
	    	paneltable=new JPanel(new BorderLayout());//�������
	    	//��������ñ߿�
	    	paneltable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(null,null),"DVD��¼��ѯ"));
	    	paneltable.add(new JScrollPane(table),BorderLayout.CENTER);
    	
	    	panelContent=new JPanel(new GridLayout(1,6));
	    	lb_name=new JLabel("DVD����");
	    	tf_name=new JTextField();
	    	lb_count=new JLabel("�������");
	    	tf_count=new JTextField();
	    	lb_status=new JLabel("DVD״̬");
	    	cb_status=new JComboBox<String>(new String[] {"�ѽ�","�ɽ�"});
	    	panelContent.add(lb_name);
	    	panelContent.add(tf_name);
	    	panelContent.add(lb_count);
	    	panelContent.add(tf_count);
	    	panelContent.add(lb_status);
	    	panelContent.add(cb_status);
	    	paneltable.add(panelContent,BorderLayout.SOUTH);
	    	this.add(paneltable,BorderLayout.CENTER);
	    	
	    	panelButton=new JPanel(new GridLayout(9, 1,10,10));
	    	panelButton.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(null,null),"��ѯ����"));
	    	this.add(panelButton, BorderLayout.EAST);
	    	lb_type=new JLabel("��ѯ����");
	    	panelButton.add(lb_type);
	    	cb_type=new JComboBox<String>(new String[] {"����DVD","DVD���","DVD����"});
	    	tf_search=new JTextField(8);
	    	tf_search.setEditable(false);
	    	
	    	panelButton.add(cb_type);
	    	panelButton.add(tf_search);
	    	btn_search=new JButton("��ѯ");
	    	panelButton.add(btn_search);
	    	btn_add=new JButton("���DVD");
//	    	btn_rent.setEnabled(false);//Ĭ�ϲ�����
	    	panelButton.add(btn_add);
	    	btn_update=new JButton("����DVD");
	    	btn_update.setEnabled(false);
	    	panelButton.add(btn_update);
	    	btn_del=new JButton("ɾ��DVD");
	    	panelButton.add(btn_del);
	    	btn_del.setEnabled(false);
	    	
	    	panelButton.add(new JLabel());
	    	btn_exit=new JButton("�˳�����");
	    	panelButton.add(btn_exit);
	    	this.setVisible(true);
	    	
	    	
	    }
	    private void registerListener() {
	    	btn_exit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int flag=JOptionPane.showConfirmDialog(AdminDVDOperationView.this, 
							"�Ƿ�ȷ���˳�","ȷ����Ϣ",JOptionPane.YES_NO_OPTION);
					if(flag==JOptionPane.YES_OPTION) {
						AdminDVDOperationView.this.dispose();
					}
				}
			});
	    	btn_del.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int row=table.getSelectedRow();
					int did=(Integer)table.getValueAt(row,0);
					int flag=JOptionPane.showConfirmDialog(AdminDVDOperationView.this, 
							"�Ƿ�ȷ��ɾ����DVD","ȷ����Ϣ",JOptionPane.YES_NO_OPTION);
					if(flag==JOptionPane.YES_OPTION) {
						boolean res=dvdBiz.delDVD(did);
					
					if(res) {
						JOptionPane.showMessageDialog(
								AdminDVDOperationView.this, "ɾ���ɹ�");
						
					}else {
						JOptionPane.showMessageDialog(
								AdminDVDOperationView.this, "ɾ��ʧ��");
				}
					}
				}
			});
	    	btn_update.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String dname=tf_name.getText().trim();
					String dcount=tf_count.getText().trim();
					int status=cb_status.getSelectedIndex();
					if(dname.equals("")) {
						JOptionPane.showMessageDialog(
								AdminDVDOperationView.this, "DVD���ֲ���Ϊ��");
						return;
					}
					if(dcount.equals("")) {
						JOptionPane.showMessageDialog(
								AdminDVDOperationView.this, "�����������Ϊ��");
						return;
					}
					if(!DVDUtil.isNumber(dcount)) {
						JOptionPane.showMessageDialog(
								AdminDVDOperationView.this, "DVD�������ֻ��Ϊ����");
						return;
					}
					int flag=JOptionPane.showConfirmDialog(AdminDVDOperationView.this, 
							"�Ƿ�ȷ������DVD","ȷ����Ϣ",JOptionPane.YES_NO_OPTION);
					if(flag==JOptionPane.YES_OPTION) {
						int row=table.getSelectedRow();
						boolean res=dvdBiz.modifyDVD(new DVD(
								(Integer)table.getValueAt(row, 0),dname
								,new Integer(dcount),status));
						if(res) {
							JOptionPane.showMessageDialog(
									AdminDVDOperationView.this, "���³ɹ�");
							return;
						}else {
							JOptionPane.showMessageDialog(
									AdminDVDOperationView.this, "����ʧ�ܣ�����ϵ����Ա");
							return;
						}
					}
				}
			});
	    	table.addMouseListener(new MouseAdapter() {
	    		public void mouseClicked(MouseEvent e) {
	    			if(table.getSelectedRow()!=-1) {
	    				btn_del.setEnabled(true);
	    				btn_update.setEnabled(true);
	    			}
	    			int row=table.getSelectedRow();//�õ�����ѡ�����е��±�
	    			String dname=table.getValueAt(row, 1).toString();
	    			String dcount=table.getValueAt(row, 2).toString();
	    			String status=table.getValueAt(row, 3).toString();
	    			tf_name.setText(dname);
	    			tf_count.setText(dcount);
	    			cb_status.setSelectedItem(status);
	    		}
			});
	    	cb_type.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					String item=e.getItem().toString();
					tf_search.setText("");
					if(item.equals("����DVD")){
						tf_search.setEditable(false);
					}else {
						tf_search.setEditable(true);
					}
					
				}
			});
	    	btn_search.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int index=cb_type.getSelectedIndex();
					
					String content=tf_search.getText().trim();
					
					if(index!=0&&content.equals("")) {
						JOptionPane.showMessageDialog(
								AdminDVDOperationView.this, "��ѯ���ݲ���Ϊ��");
						return;
					}
					//��������ݣ���ֹ���ݵ��ۼ�
					if(dvdList!=null) {
						dvdList.clear();
					}
					if(index==0) {
						dvdList=dvdBiz.queryAllDVDS();
					}else if(index==1) {
	                   if(DVDUtil.isNumber(content)) {
	                	   DVD dvd=dvdBiz.queryDVDById(Integer.parseInt(content));
	                	   if(dvd!=null) {
	                		   dvdList.add(dvd);
	                	   }
	                   }else {
	                	   JOptionPane.showMessageDialog(
									AdminDVDOperationView.this, "����ı��ֻ��������");
	                	   return;
	                   }
                      }else {
                    	  dvdList=dvdBiz.queryDVDByName(content);
                      }
					refreshTable(dvdList);
					btn_del.setEnabled(false);
					btn_update.setEnabled(false);
					if(dvdList.size()==0) {
						JOptionPane.showMessageDialog(
								AdminDVDOperationView.this, "û����Ҫ��ѯ������");
						return;
					}
				}
			});
	    	btn_add.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					
					String dname=tf_name.getText().trim();
					String dcount=tf_count.getText().trim();
					int status=cb_status.getSelectedIndex();//0�����ѽ�,1����ɽ�
					if(dname.equals("")) {
						JOptionPane.showMessageDialog
						(AdminDVDOperationView.this, "DVD���ֲ���Ϊ��");
						return;
					}
					if(dcount.equals("")) {
						JOptionPane.showMessageDialog
						(AdminDVDOperationView.this, "�����������Ϊ��");
						return;}
						if(!DVDUtil.isNumber(dcount)) {
							JOptionPane.showMessageDialog
							(AdminDVDOperationView.this, "DVD�������ֻ��Ϊ����");
							return;
					}
						int flag=JOptionPane.showConfirmDialog
								(AdminDVDOperationView.this, "�Ƿ�ȷ�����DVD?"
										,"ȷ����Ϣ",JOptionPane.YES_NO_OPTION);
						if(flag==JOptionPane.YES_OPTION) {
							boolean res=dvdBiz.addDVD(new DVD
									(dname,new Integer(dcount),status));
							if(res) {
								JOptionPane.showMessageDialog
								(AdminDVDOperationView.this, "��ӳɹ�!");
								
							}
							else {
								JOptionPane.showMessageDialog
								(AdminDVDOperationView.this, "���ʧ��,����ϵ����Ա");
								return;
							}
						}
				}
				
			});
	    }
	    
	    private class DVDInfoTableModel implements TableModel{
	    	private List<DVD> dvdList=null;
	    	public DVDInfoTableModel(List<DVD> dvdList) {
	    		this.dvdList=dvdList;
	    	}
			@Override
			public void addTableModelListener(TableModelListener arg0) {
				// TODO Auto-generated method stub
				
			}
			//JTable�е���������
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				return String.class;
			}
			//JTable���ݵ�����
			@Override
			public int getColumnCount() {
				// TODO Auto-generated method stub
				return 4;
			}
			
			@Override
			public String getColumnName(int columnIndex) {
				if(columnIndex==0) {
					return "Ӱ��ID";
				}else if(columnIndex==1) {
					return "Ӱ������";
				}else if(columnIndex==2) {
					return "Ӱ���������";
				}else if(columnIndex==3) {
					return "Ӱ��״̬";
				}else {
				return "����";
				}
			}
			//JTable��ʾ����������
			@Override
			public int getRowCount() {
				// TODO Auto-generated method stub
				return dvdList.size();
			}
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				DVD dvd=dvdList.get(rowIndex);
				if(columnIndex==0) {
					return dvd.getId();
				}else if(columnIndex==1) {
					return dvd.getDname();
				}else if(columnIndex==2) {
					return dvd.getDcount();
				}else if(columnIndex==3) {
					return ""+(dvd.getStatus()==1?"�ɽ�":"�ѽ�");
				}else {
					return "����";
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
	    //ˢ��JTable����ʾ����
	    private void refreshTable(List<DVD> dvdList) {
	    	infoTableModel=new DVDInfoTableModel(dvdList);
	    	table.setModel(infoTableModel);
	    }
}
