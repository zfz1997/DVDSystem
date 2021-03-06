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
	private JPanel panel_main=null;//主面板
	private JPanel panel_left=null;//左侧面板
	private JPanel panel_right=null;//右侧面板
    
	private JLabel lb_uname=null;//用户标签
	private JLabel lb_upass=null;//密码标签
	private JLabel lb_type=null;//登录类型标签
	
	private JTextField tf_uname=null;//用户文本框
	private JPasswordField pf_pass=null;//密码文本框
	
	private JLabel lb_img=null;//显示图片的标签
	
	private JButton btn_login=null;//登录按钮
	private JButton btn_register=null;//注册按钮
	
	private JComboBox<String> cb_type=null;//登录类型下拉列表框
	public UserBiz userBiz=null;
	
	public LoginView() {
		init();
		userBiz=new UserBizImpl();
		registerListener();
	}
	//初始化控件的方法
	private void init() {
		this.setSize(320,220);//设置窗体大小
		this.setResizable(false);//不可拖动窗体大小
		this.setLocationRelativeTo(null);//窗体居中显示
		this.setTitle("登录窗体");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口功能
		//初始化面板
		panel_main=new JPanel(new GridLayout(1, 2));
		panel_left=new JPanel();
		panel_right=new JPanel(new GridLayout(4, 2,0,10));
		
		//初始化控件
		tf_uname=new JTextField(8);
		pf_pass=new JPasswordField(8);
		cb_type=new JComboBox<String>(new String[] {"普通用户","管理员"});
		btn_login=new JButton("登录");
		btn_register=new JButton("注册");
		lb_uname=new JLabel("用户:",JLabel.CENTER);
		lb_upass=new JLabel("密码:",JLabel.CENTER);
		lb_type=new JLabel("类型:",JLabel.CENTER);
		lb_img=new JLabel(new ImageIcon(
				ClassLoader.getSystemResource("images/timg.jpg")));
		//把相应的控件放到面板中去
		panel_left.add(lb_img);
		panel_right.add(lb_uname);
		panel_right.add(tf_uname);
		panel_right.add(lb_upass);
		panel_right.add(pf_pass);
		panel_right.add(lb_type);
		panel_right.add(cb_type);
		panel_right.add(btn_login);
		panel_right.add(btn_register);
		
		//主面板中放左右两个面板
		panel_main.add(panel_left);
		panel_main.add(panel_right);
		//再把主面板放到窗体中
		this.getContentPane().add(panel_main);
		this.pack();
		this.setVisible(true);
	}
	
	private void registerListener() {
		btn_register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new UserRegisterView();//弹出注册视图
				LoginView.this.dispose();//本窗口消失
			}
		});
		btn_login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//获取用户名和密码
				String uname=tf_uname.getText().trim();
				String upass=new String(pf_pass.getPassword());
				int type=cb_type.getSelectedIndex()+1;
				if(uname.equals("")) {
					JOptionPane.showMessageDialog(LoginView.this,"用户名不能为空");
					return;
				}else if(upass.equals("")) {
					JOptionPane.showMessageDialog(LoginView.this, "密码不能为空");
					return;
				}
				User user=new User(uname,upass,type);
				user =userBiz.login(user);
				if(user!=null) {
					//普通用户
					if(user.getType()==1) {
						new MainScreenView(user);
					}else {
						new AdminMainView();
					}
					LoginView.this.dispose();
				}else {
					JOptionPane.showMessageDialog(LoginView.this, "用户名或密码错误");
				}
			}
		});
	}
	
	
	
}
