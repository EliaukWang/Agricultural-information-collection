package com.briup.environment.main;

import java.util.Collection;


import com.briup.environment.bean.Environment;
import com.briup.environment.client.Client;
import com.briup.environment.client.Gather;
import com.briup.environment.client.impl.ClientImpl;
import com.briup.environment.client.impl.GatherImpl;
import com.briup.environment.server.DBStore;

/**
 * �ͻ��������
 * @author briup
 */
public class ClientMain {

	public static void main(String[] args) throws Exception {
		//��д���Դ���
		//	ʵ����һ�� �ɼ����� ������gather�����������Ƿ���Ч
      Gather g=new GatherImpl();
       Collection<Environment> coll=g.gather();
       Client c=new ClientImpl();
       c.send(coll);
     /*  System.out.println(coll.size());
        for(Environment e:coll) {
        	System.out.println(e);
        }
*/	}
}
