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

//�ɼ�ģ�����ʵ����
public class GatherImpl implements Gather {
	@Override
	public void init(Properties properties) throws Exception {
		
	}

	//	�ɼ������ļ�src/data_temp.txt���ݣ��õ�16������
	//	����װ��һ�������з���
		@Override
		public Collection<Environment> gather() throws Exception {
          BufferedReader br=new BufferedReader(new FileReader("src/data_temp.txt"));
           //���ж�ȡ
           String len=null;
           Collection<Environment>  coll=new ArrayList<>();
           while((len=br.readLine())!=null) {
        	   //  System.out.println(len);
           
           //����ַ������õ�һ������
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
         
          //���ݵ������ֵ���������������
	         if(a2==16) {
	        	 //��a2����16ʱ���±�Ϊ����������ʾ��ʪ��
	        	 String str=arr[6].substring(0, 4);//5d60
	        	  Long value=Long.parseLong(str,16);
	        	//  System.out.println(value);
	        	 String str2=arr[6].substring(4, 8);//6f78
	        	 long value2=Long.parseLong(str2,16);
	        	 //System.out.println("value2: "+value2);
        	    //�¶�value(int)  
	        	 name="�¶�";
				float Temperature =(float) (( (float) value*0.00268127)-46.85);
				//System.out.println(Temperature);
				Environment envir=new Environment(name, srcId, dstId, devId, sersorAddress, count, cmd, status, Temperature, time);
				coll.add(envir);
				name="ʪ��";
				float Humidity = (float) (((float)value2*0.00190735)-6);
				//System.out.println(Humidity);
				Environment envir2=new Environment(name, srcId, dstId, devId, sersorAddress, count, cmd, status, Humidity, time);
				coll.add(envir2);
	        	 
	         }else if(a2==256) {
	        	 //a2����256ʱ����ʾ����ǿ��
	        	 name="����ǿ��";
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
          //ʹ������9��ֵ��ʵ����Environment����
                 //System.out.println(envir);
                 //��ӵ�coll������
                 
           }
		//�����е��ж�������ɣ��ر���Դ������coll
           br.close();
           return coll;
           
	}

	
	
}
