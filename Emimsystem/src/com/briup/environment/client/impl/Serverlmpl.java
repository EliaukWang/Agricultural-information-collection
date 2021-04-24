package com.briup.environment.client.impl;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.environment.bean.Environment;
import com.briup.environment.server.Server;
import com.briup.environment.util.impl.LogImpl;

public class Serverlmpl implements Server {

	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Environment> reciver() throws Exception {
		
		// TODO Auto-generated method stub
		ServerSocket server=new ServerSocket(9999);
		LogImpl logger=new LogImpl();
		logger.info("�����������ɹ�....");
		Socket socket=server.accept();
		logger.info("���������ܿͻ��˵�����....");
		ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
		
		Collection<Environment> reciver=(Collection<Environment>) ois.readObject();
		logger.info("��������������");
		for(Environment s:reciver) {
			System.out.println(s);
		}
		ois.close();
		socket.close();
		server.close();
		return reciver;
	}

}
