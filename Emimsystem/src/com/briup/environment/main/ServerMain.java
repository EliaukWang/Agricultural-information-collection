package com.briup.environment.main;

import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.client.impl.DBStorelmpl;
import com.briup.environment.client.impl.Serverlmpl;
import com.briup.environment.server.DBStore;
import com.briup.environment.server.Server;

/**
 * 
 * 服务器端入口类
 * 
 * @author briup
 *
 */
public class ServerMain {


	public static void main(String[] args) throws Exception {
      Server s=new Serverlmpl();
      Collection<Environment> coll=s.reciver() ;
     /* for(Environment n:coll) {
			System.out.println(n);
		}*/
      //入库
      DBStore d=new DBStorelmpl();
      d.saveDb(coll);
	}
}
