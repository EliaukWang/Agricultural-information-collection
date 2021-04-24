package com.briup.environment.client.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import com.briup.environment.util.Backup;

public class BackupImpl implements Backup{

	@Override
	public void init(Properties properties) {
		
	}
      //��������
	@Override
	public void backup(String fileName, Object data) throws Exception {
		//������ �ļ��� ��data����д��fileName�ļ���
		FileOutputStream  fs=new FileOutputStream(fileName) ;
		ObjectOutputStream oos=new ObjectOutputStream(fs );
		oos.writeObject(data);
		oos.close();
		fs.close();
		
		
	}
      //���ر�������
	@Override
	public Object load(String fileName) throws Exception {
		File file = new File(fileName);
		if(file.length() == 0)
			return null;
		FileInputStream fi=new FileInputStream(fileName);
		ObjectInputStream ois=new ObjectInputStream(fi);
		Object obj = ois.readObject();
		ois.close();
		return obj;
	}

	//ɾ������
	@Override
	public void deleteBackup(String fileName) {
		
	}

}
