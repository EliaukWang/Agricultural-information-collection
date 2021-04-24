package com.briup.environment.main;

import java.util.Collection;


import com.briup.environment.bean.Environment;
import com.briup.environment.client.Client;
import com.briup.environment.client.Gather;
import com.briup.environment.client.impl.ClientImpl;
import com.briup.environment.client.impl.GatherImpl;
import com.briup.environment.server.DBStore;

/**
 * 客户端入口类
 * @author briup
 */
public class ClientMain {

	public static void main(String[] args) throws Exception {
		//填写测试代码
		//	实例化一个 采集对象 ，调用gather方法，测试是否有效
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
