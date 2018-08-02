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
    private JPanel paneltable=null;//��������Jtable��һ�����
    private JTable table=null;//����Jtable
    private JPanel panelButton=null;//��ť���
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
    	this.setTitle("DVD��Ϣ��ѯ");
    	this.setSize(800,500);
    	this.setIconifiable(true);//�������С��
    	this.setClosable(true);//����ɹر�
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	table=new JTable();
    	refreshTable(new ArrayList<DVD>());
    	paneltable=new JPanel(new BorderLayout());//�������
    	//��������ñ߿�
    	paneltable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(null,null),"��ѯ��Ϣ"));
    	paneltable.add(new JScrollPane(table),BorderLayout.CENTER);
    	this.add(paneltable,BorderLayout.CENTER);
  
    	panelContent=new JPanel(new GridLayout(1,6));
    	lb_uid=new JLabel("�û�id");
    	tf_uid=new JTextField();
    	lb_did=new JLabel("Ӱ��id");
    	tf_did=new JTextField();
    	
    	panelContent.add(lb_uid);
    	panelContent.add(tf_uid);
    	panelContent.add(lb_did);
    	panelContent.add(tf_did);
    	
    	paneltable.add(panelContent,BorderLayout.SOUTH);
    	this.add(paneltable,BorderLayout.CENTER);
    	
    	
    	
    	panelButton=new JPanel(new GridLayout(7, 1,10,30));
    	panelButton.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(null,null),"��ѯ����"));
    	this.add(panelButton, BorderLayout.EAST);
    	lb_type=new JLabel("��ѯ����");
    	panelButton.add(lb_type);
    	cb_type=new JComboBox<String>(new String[] {"ȫ��DVD","�ɽ�DVD","�ѽ�DVD","����DVD"});
    	panelButton.add(cb_type);
    	btn_search=new JButton("��ѯ");
    	panelButton.add(btn_search);
    	btn_rent=new JButton("��DVD");
    	btn_rent.setEnabled(false);//Ĭ�ϲ�����
    	panelButton.add(btn_rent);
    	panelButton.add(new JLabel());
    	panelButton.add(new JLabel());
    	btn_exit=new JButton("�˳�����");
    	panelButton.add(btn_exit);
    	this.setVisible(true);
    	
    	
    }
    private void registerListener() {
    	btn_rent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int flag=JOptionPane.showConfirmDialog(UserQueryRentDVDView.this, 
						"�Ƿ�ȷ����DVD","ȷ����Ϣ",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					int flag2=dvdBiz.lendDVD(Integer.parseInt(tf_did.getText()),Integer.parseInt(tf_uid.getText()));
					if(flag2==0) {
						JOptionPane.showMessageDialog(
								UserQueryRentDVDView.this, "û���ҵ�Ҫ���DVD");
					}else if(flag2==1) {
						JOptionPane.showMessageDialog(
								UserQueryRentDVDView.this, "�Բ��𣬸�DVD�Ѿ����");
					}else if(flag2==2) {
						JOptionPane.showMessageDialog(
								UserQueryRentDVDView.this, "��DVD�ɹ�");
					}else {
						JOptionPane.showMessageDialog(
								UserQueryRentDVDView.this, "��DVDʧ�ܣ�����ϵ����Ա");
					}
				}
				   
			}
		});
    	table.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			if(table.getSelectedRow()!=-1) {
    				btn_rent.setEnabled(true);
    				
    			}
    			int row=table.getSelectedRow();//�õ�����ѡ�����е��±�
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
						"�Ƿ�ȷ���˳�","ȷ����Ϣ",JOptionPane.YES_NO_OPTION);
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
				return "�������";
			}else if(columnIndex==3){
			return "״̬";
			}else
			{
				return "����";
			}
		}
		//JTable��ʾ����������
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
				return ""+(Dvd.getStatus()==1?"�ɽ�":"�ѽ�");
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
    private void refreshTable(List<DVD> DVDList) {
    	DVD=new InfoTableDVDList(DVDList);
    	table.setModel(DVD);
    }
}
