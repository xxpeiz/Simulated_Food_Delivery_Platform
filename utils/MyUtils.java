package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtils {

	//获取系统时间
	public static String getSystemDate(String gs) {
		
		String sj = new SimpleDateFormat(gs).format(new Date());
		
		return sj;
	}
	
	
	//获取随机数
	public static int getNum(int ws) {
		
		return (int)(Math.random()*ws);
	}
	
	
	//修改文件名
	public static String uodateFileName(String oldName) {
		
		String newFileName = "";
		
		String kzm = oldName.substring(oldName.lastIndexOf("."));
		
		newFileName = getSystemDate("YYYYMMddhhmmss")+getNum(10000)+kzm;
		
		return newFileName;
	}

}
