package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;

import core.Activator;


public class UploadFile {
	
	
	
	//文件上传 需要上传的文件上传到icons中
	public static void uploadFile(File file,String fileName){
		
		try{
			FileInputStream input = new FileInputStream(file);
			//String path = ServletActionContext.getRequest().getRealPath("/upload");
			/* 获得icons目录的位置  */
			URL url = Activator.getDefault().getBundle().getResource("icons");
			String str = "";
			try {
				str = FileLocator.toFileURL(url).toString().substring(6);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			File file1 = new File(str,fileName);
			
			FileOutputStream out = new FileOutputStream(file1);
			
			byte [] bb = new byte[ (int) file.length()];
			int len = 0;
			while((len = input.read(bb)) > 0){
				out.write(bb,0,len);
			}
			
			out.flush();
			out.close();
			input.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

}
