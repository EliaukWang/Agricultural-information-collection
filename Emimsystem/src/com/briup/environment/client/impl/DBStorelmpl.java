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
		
		//进行数据库连接
		Connection conn=null;
		PreparedStatement ptmt=null;
		//记录实际入库数据条数
		int sum=0;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			//定义一个计数器
			int count=0;
			String sql="insert into e_detail_1 values(?,?,?,?,?,?,?,?,?)";
			 //获取ptmt对象，处理sql语句
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
				
					//插入Environment信息
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
					
					//执行批处理
					count++;
					ptmt.addBatch();
					logger.info("添加批处理："+count);
					//模拟异常将要发生
					/*if(count == 14) {
						int num = 10/0;
					} */
			
					if(count%6==0) {
						//执行批处理
						ptmt.executeBatch();
						
						//提交事务
						conn.commit();
						sum+= 6;
						logger.info("执行批处理："+sum);
					}
				}
			
			//最后一次执行 批处理
			ptmt.executeBatch();
			conn.commit();
			sum = sum + (count%6);
			logger.info("执行批处理："+sum);
			System.out.println("count: " + count);
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("入库出现异常....");;
			conn.rollback();
			//异常发生后，执行的代码
			/*
			 * 提取尚未输入的数据，备份到src/backUp.txt
			 * */
			ArrayList<Environment> list = (ArrayList<Environment>) coll;
			//准备尚未入库的数据
			list.subList(0, sum).clear();
			logger.info("即将备份数据："+list.size());
			Backup bu = new BackupImpl();
			bu.backup("src/backUp.txt", list);
			logger.info("备份成功"+list.size());
			
		}finally {
			//关闭
			  JDBCUtils.close(conn, ptmt);	
		}
	}

	private Object LogImpl() {
		// TODO Auto-generated method stub
		return null;
	}
}
