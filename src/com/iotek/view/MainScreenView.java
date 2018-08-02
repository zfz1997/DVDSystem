package com.iotek.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.iotek.entity.User;


         
public class MainScreenView extends JFrame {
	     private JPanel contentPane;
	     private Button btn_operater;
	     private JPanel panel_picture;
	     private JLabel jlb;
	     private Button btn_querry;
	     private Button btn_quit;
	     private User user=null;
	     private String userName=null;
        public MainScreenView(User user) {
        	 this.user=user;
        	 userName=user.getUname();
        	 init();
        	 registerListener();
        	
        	 
        }
        public void init() {
        	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(300, 0, 1200, 800);
            contentPane = new JPanel();
            setContentPane(contentPane);
            GridBagLayout gbl_contentPane = new GridBagLayout();
            contentPane.setLayout(gbl_contentPane);
            jlb = new JLabel();
            int width = 1000,height = 650;
            ImageIcon image =new ImageIcon(
    				ClassLoader.getSystemResource("images/海贼王.jpg"));
            image.setImage(image.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
            jlb.setIcon(image);
            jlb.setSize(width,height);
            MyComponent panel_word=new MyComponent();
            
           panel_word.setArrText(new String []{"欢  迎  "  +userName+  "  使  用  DVD  租  赁  系  统"});
          
            panel_picture=new JPanel();
            
            JPanel panel_operate=new JPanel();
            
            contentPane.add(panel_word, new GBC(0,0,2,1).  
                    setFill(GBC.BOTH).setIpad(1000, 200).setWeight(100, 100));
            contentPane.add(panel_picture, new GBC(0,1).  
                    setFill(GBC.BOTH).setIpad(100, 125).setWeight(100, 100));
            contentPane.add(panel_operate, new GBC(1,1).  
                    setFill(GBC.BOTH).setIpad(200, 400).setWeight(100, 100));
            panel_operate.setLayout(new GridLayout(5,1,0,5));
            JLabel lb_word=new JLabel("快捷功能区");
            btn_operater=new Button("DVD查询租赁操作");
            btn_querry=new Button("DVD租赁记录查询");
            btn_quit=new Button("退出窗口");
            panel_picture.add(jlb);
            panel_operate.add(lb_word);
            panel_operate.add(new JPanel());
            panel_operate.add(btn_operater);
            panel_operate.add(btn_querry);
            panel_operate.add(btn_quit);
            
            this.setVisible(true);
            this.setResizable(false);
         
        }
       private void registerListener() {
    	   btn_quit.addActionListener(new ActionListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent arg0) {
    				// TODO Auto-generated method stub
    				
    				int flag=JOptionPane.showConfirmDialog(MainScreenView.this, 
    						"是否确定退出","确认信息",JOptionPane.YES_NO_OPTION);
    				if(flag==JOptionPane.YES_OPTION) {
    					MainScreenView.this.dispose();
    					new LoginView();
    				}
    			}
    		});
    	   btn_operater.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				UserQueryRentDVDView qdv=new UserQueryRentDVDView(user);
				jlb.add(qdv);//把指定的视图添加到桌面容器中
				qdv.toFront();//视图显示在前面
			}
		});
    	   btn_querry.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				UserQueryDVDRecordView qdv=new UserQueryDVDRecordView(user);
				jlb.add(qdv);//把指定的视图添加到桌面容器中
				qdv.toFront();//视图显示在前面
			}
		});
       }

    
    }




