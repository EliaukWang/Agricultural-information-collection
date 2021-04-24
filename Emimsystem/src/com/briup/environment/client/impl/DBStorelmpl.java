package com.briup.environment.client.impl;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.LogManager;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggerFactory;

import com.briup.environment.bean.Environment;
import com.briup.environment.server.DBStore;
import com.briup.environment.util.Backup;
import com.briup.environment.util.JDBCUtils;
import com.briup.environment.util.Log;
import com.briup.environment.util.impl.LogImpl;
import com.sun.media.jfxmedia.logging.Logger;



	
public class DBStorelmpl implements DBStore{


	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public  void saveDb(Collection<Environment> coll) throws Exception  {
		 LogImpl logger=new LogImpl();
		 Object obj = new BackupImpl().load("src/backUp.txt");
		 if(obj != null) {
			 Collection<Environment> list = (Collection<Environment>) obj;
			 list.addAll(coll);
			 coll = list;
		 }
		
		//�������ݿ�����
		Connection conn=null;
		PreparedStatement ptmt=null;
		//��¼ʵ�������������
		int sum=0;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			//����һ��������
			int count=0;
			String sql="insert into e_detail_1 values(?,?,?,?,?,?,?,?,?)";
			 //��ȡptmt���󣬴���sql���
			ptmt= conn.prepareStatement(sql);
			
			for(Environment n:coll) {
				 String name=n.getName();
				 String Srcid=n.getSrcId();
				 String Dstid=n.getDstId();
				 //Devid=n.getDevId();
				 String address=n.getSersorAddress();
				 int cou=n.getCount();
				 String cmd=n.getCmd();
				 int Status=n.getStatus();
				 float data=n.getData();
				 Timestamp time = n.getGather_date();
				
					//����Environment��Ϣ
					ptmt.setString(1,name);
					ptmt.setString(2, Srcid);
					ptmt.setString(3, Dstid);
					ptmt.setString(4, address);
					ptmt.setInt(5, cou);
					//  ptmt.setLong(6, count);
					ptmt.setString(6, cmd);
					ptmt.setLong(7, Status);
					ptmt.setFloat(8, data);
					ptmt.setTimestamp(9, time);
					
					//ִ��������
					count++;
					ptmt.addBatch();
					logger.info("���������"+count);
					//ģ���쳣��Ҫ����
					/*if(count == 14) {
						int num = 10/0;
					} */
			
					if(count%6==0) {
						//ִ��������
						ptmt.executeBatch();
						
						//�ύ����
						conn.commit();
						sum+= 6;
						logger.info("ִ��������"+sum);
					}
				}
			
			//���һ��ִ�� ������
			ptmt.executeBatch();
			conn.commit();
			sum = sum + (count%6);
			logger.info("ִ��������"+sum);
			System.out.println("count: " + count);
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("�������쳣....");;
			conn.rollback();
			//�쳣������ִ�еĴ���
			/*
			 * ��ȡ��δ��������ݣ����ݵ�src/backUp.txt
			 * */
			ArrayList<Environment> list = (ArrayList<Environment>) coll;
			//׼����δ��������
			list.subList(0, sum).clear();
			logger.info("�����������ݣ�"+list.size());
			Backup bu = new BackupImpl();
			bu.backup("src/backUp.txt", list);
			logger.info("���ݳɹ�"+list.size());
			
		}finally {
			//�ر�
			  JDBCUtils.close(conn, ptmt);	
		}
	}

	private Object LogImpl() {
		// TODO Auto-generated method stub
		return null;
	}
}
