package com.iotek.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.iotek.biz.UserBiz;
import com.iotek.biz.impl.UserBizImpl;
import com.iotek.entity.User;

public class LoginView extends JFrame {
	private static final long serialVersionUID = -3190133401127889997L;
	private JPanel panel_main=null;//�����
	private JPanel panel_left=null;//������
	private JPanel panel_right=null;//�Ҳ����
    
	private JLabel lb_uname=null;//�û���ǩ
	private JLabel lb_upass=null;//�����ǩ
	private JLabel lb_type=null;//��¼���ͱ�ǩ
	
	private JTextField tf_uname=null;//�û��ı���
	private JPasswordField pf_pass=null;//�����ı���
	
	private JLabel lb_img=null;//��ʾͼƬ�ı�ǩ
	
	private JButton btn_login=null;//��¼��ť
	private JButton btn_register=null;//ע�ᰴť
	
	private JComboBox<String> cb_type=null;//��¼���������б��
	public UserBiz userBiz=null;
	
	public LoginView() {
		init();
		userBiz=new UserBizImpl();
		registerListener();
	}
	//��ʼ���ؼ��ķ���
	private void init() {
		this.setSize(320,220);//���ô����С
		this.setResizable(false);//�����϶������С
		this.setLocationRelativeTo(null);//���������ʾ
		this.setTitle("��¼����");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رմ��ڹ���
		//��ʼ�����
		panel_main=new JPanel(new GridLayout(1, 2));
		panel_left=new JPanel();
		panel_right=new JPanel(new GridLayout(4, 2,0,10));
		
		//��ʼ���ؼ�
		tf_uname=new JTextField(8);
		pf_pass=new JPasswordField(8);
		cb_type=new JComboBox<String>(new String[] {"��ͨ�û�","����Ա"});
		btn_login=new JButton("��¼");
		btn_register=new JButton("ע��");
		lb_uname=new JLabel("�û�:",JLabel.CENTER);
		lb_upass=new JLabel("����:",JLabel.CENTER);
		lb_type=new JLabel("����:",JLabel.CENTER);
		lb_img=new JLabel(new ImageIcon(
				ClassLoader.getSystemResource("images/timg.jpg")));
		//����Ӧ�Ŀؼ��ŵ������ȥ
		panel_left.add(lb_img);
		panel_right.add(lb_uname);
		panel_right.add(tf_uname);
		panel_right.add(lb_upass);
		panel_right.add(pf_pass);
		panel_right.add(lb_type);
		panel_right.add(cb_type);
		panel_right.add(btn_login);
		panel_right.add(btn_register);
		
		//������з������������
		panel_main.add(panel_left);
		panel_main.add(panel_right);
		//�ٰ������ŵ�������
		this.getContentPane().add(panel_main);
		this.pack();
		this.setVisible(true);
	}
	
	private void registerListener() {
		btn_register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new UserRegisterView();//����ע����ͼ
				LoginView.this.dispose();//��������ʧ
			}
		});
		btn_login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//��ȡ�û���������
				String uname=tf_uname.getText().trim();
				String upass=new String(pf_pass.getPassword());
				int type=cb_type.getSelectedIndex()+1;
				if(uname.equals("")) {
					JOptionPane.showMessageDialog(LoginView.this,"�û�������Ϊ��");
					return;
				}else if(upass.equals("")) {
					JOptionPane.showMessageDialog(LoginView.this, "���벻��Ϊ��");
					return;
				}
				User user=new User(uname,upass,type);
				user =userBiz.login(user);
				if(user!=null) {
					//��ͨ�û�
					if(user.getType()==1) {
						new MainScreenView(user);
					}else {
						new AdminMainView();
					}
					LoginView.this.dispose();
				}else {
					JOptionPane.showMessageDialog(LoginView.this, "�û������������");
				}
			}
		});
	}
	
	
	
}
