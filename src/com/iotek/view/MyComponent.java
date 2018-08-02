package com.iotek.view;
import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.ExecutorService;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * ��ʾ��������ʵ����
 * @author ��ҫǿ   20150507
 * @version 1.1
 * */

public class MyComponent extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel jl2;//��value
    private String[] msg;

    private int x=900,y=50;
    private int i=0;
    private Roll roll;
    private ExecutorService pool;
    public static boolean flag;



    public JLabel getJl2() {
        return jl2;
    }

    public void setJl2(JLabel jl2) {
        this.jl2 = jl2;
    }

    public MyComponent() {
        super();
        
        this.setLayout(null);
        JLabel jl2=new JLabel();
        this.jl2=jl2;
        jl2.setBounds(0, 0, 100, 30);
        this.add(jl2);
    }

    /**
     * ������ʾֵ
     * */
    public void setArrText(String[] msg){
        this.msg=msg;
        /*
         * ����ʵ��
         * */
        if(msg!=null&&msg.length>0){
            flag=true;
            pool=ThreadGroupUtil.getInstance();
            if(roll==null){
                roll=new Roll();
                //  roll.start();
                pool.execute(roll);
            } 
        }else{
            flag=false;
        }
    }

    /**
     *  �����̣߳�����ҵ��ֵ������ʱ���������߳�
     */
    class Roll extends Thread{
        @Override
        public void run(){ 
            while(flag){
                try {
                    Thread.sleep(100);
                    if(jl2.getText()!=null){
                        jl2.setText(null);
                    }
                } catch (InterruptedException e) {
                }
                if((x-=1) > -25){
                    continue;
                } 
                i = ++i % msg.length;

                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                }
                x=90;
            }
        }
    } 
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(msg!=null){
            if(i<msg.length){

                g.drawString(msg[i],x,y);
            }
            this.repaint();
        }
    }
}