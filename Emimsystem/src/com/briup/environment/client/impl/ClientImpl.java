package com.briup.environment.client.impl;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import com.briup.environment.bean.Environment;
import com.briup.environment.client.Client;
import com.briup.environment.util.impl.LogImpl;

public class ClientImpl implements Client {

	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(Collection<Environment> coll) throws Exception {
		// TODO Auto-generated method stub
		  Socket client=new Socket("127.0.0.1",9999);
		  LogImpl logger=new LogImpl();
			logger.info("客户端连接成功....");
		  ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());
		  logger.info("客户端开始传输数据了");
		  oos.writeObject(coll);
		  logger.info("传输成功");
		  //关闭资源
		  oos.close();
		  client.close();
	}

}
