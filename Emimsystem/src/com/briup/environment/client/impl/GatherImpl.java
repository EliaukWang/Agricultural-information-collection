package com.briup.environment.client.impl;

import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;

import com.briup.environment.bean.Environment;
import com.briup.environment.client.Gather;
import com.sun.org.apache.bcel.internal.generic.D2F;

//采集模块具体实现类
public class GatherImpl implements Gather {
	@Override
	public void init(Properties properties) throws Exception {
		
	}

	//	采集本地文件src/data_temp.txt内容，得到16个对象，
	//	最后封装到一个集合中返回
		@Override
		public Collection<Environment> gather() throws Exception {
          BufferedReader br=new BufferedReader(new FileReader("src/data_temp.txt"));
           //逐行读取
           String len=null;
           Collection<Environment>  coll=new ArrayList<>();
           while((len=br.readLine())!=null) {
        	   //  System.out.println(len);
           
           //拆分字符串，得到一个数组
           String[] arr=len.split("\\|");
          // System.out.println(arr.length);
           String name=null;
           String srcId=arr[0];
           String dstId=arr[1];
           String devId=arr[2];
           String sersorAddress=arr[3];
           int count =Integer.parseInt(arr[4]);
           String cmd=arr[5];
           int status=Integer.parseInt(arr[7]);
           long t=Long.parseLong(arr[8]);
           Timestamp time=new Timestamp(t);
           int a2=Integer.parseInt(arr[3]);
         
          //根据第四项的值，处理第七项数据
	         if(a2==16) {
	        	 //当a2等于16时，下标为六的数，表示温湿度
	        	 String str=arr[6].substring(0, 4);//5d60
	        	  Long value=Long.parseLong(str,16);
	        	//  System.out.println(value);
	        	 String str2=arr[6].substring(4, 8);//6f78
	        	 long value2=Long.parseLong(str2,16);
	        	 //System.out.println("value2: "+value2);
        	    //温度value(int)  
	        	 name="温度";
				float Temperature =(float) (( (float) value*0.00268127)-46.85);
				//System.out.println(Temperature);
				Environment envir=new Environment(name, srcId, dstId, devId, sersorAddress, count, cmd, status, Temperature, time);
				coll.add(envir);
				name="湿度";
				float Humidity = (float) (((float)value2*0.00190735)-6);
				//System.out.println(Humidity);
				Environment envir2=new Environment(name, srcId, dstId, devId, sersorAddress, count, cmd, status, Humidity, time);
				coll.add(envir2);
	        	 
	         }else if(a2==256) {
	        	 //a2等于256时，表示光照强度
	        	 name="光照强度";
	        	  long long1=Long.parseLong(arr[6],16);
	        	  Environment envir2=new Environment(name, srcId, dstId, devId, sersorAddress, count, cmd, status, long1, time);
	        	  coll.add(envir2);
	         }else {
	        	 //co2
	        	   String str3=arr[6].substring(0, 5);
	        	   int int3=Integer.parseInt(str3,16);
	        	   name="co2";
	        	   Environment envir2=new Environment(name, srcId, dstId, devId, sersorAddress, count, cmd, status, int3, time);
	        	   coll.add(envir2);
	         }
          //使用上述9项值，实例化Environment对象
                 //System.out.println(envir);
                 //添加到coll集合中
                 
           }
		//当所有的行都处理完成，关闭资源，返回coll
           br.close();
           return coll;
           
	}

	
	
}
