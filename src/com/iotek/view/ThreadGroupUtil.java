package com.iotek.view;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * ����ʵ���̳߳��࣬������������������߳�
 * @author ��ҫǿ 
 * @version 1.1
 *
 */
public class ThreadGroupUtil {

    private static ExecutorService pool = null;
    private static int size;  
    static {  

            size=400;
        System.out.println(size);
        pool = Executors. newFixedThreadPool(size);
    }  

    /**
     * �õ��������̳߳أ��̳߳��д�С����СΪ�豸��������Ķ���
     * @return ExecutorService �̳߳�
     */
    public static ExecutorService getInstance() {  
        return pool;  
    }  
}