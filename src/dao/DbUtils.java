package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//所有项目的数据库的 连接 交互  关闭
//公共的dao层
//一旦封装完成就表示你用的不再是源生的JDBC   操作模式和方式都会改变

//张三
public class DbUtils {
	
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	
	//java专用的配置文件中 
	//.properties
	//.xml
	//.yml
	private static String url = "jdbc:mysql://localhost:3308/waimaidelivery";
	private static String user = "root";
	private static String password = "mysql";
	private static String driver = "com.mysql.jdbc.Driver";
	
	//连接数据库  （类数据初始化技术  构造方法  静态代码块）
	
	static {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("连接成功!");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	//提供静态语句的更新 insert update delete   注入变量
	public  int update(String sql) {
		int rows = 0;
		try {
			stmt = conn.createStatement();
			rows = stmt.executeUpdate(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rows;
	}
	
	//提供静态语句的查询
	
	public ResultSet query(String sql) {
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
		
	}
	
	//sql注入问题 导致不安全
	//提供预处理语句的更新  insert update delete   ? ? ?
	public int preUpdate(String sql,Object...obj) {
		int rows = 0;
		try {
			//如果是预处理  sql中?号 我点知道sql中有几个问号
			//给每个问号 赋值
			pstmt = conn.prepareStatement(sql);
			for(int i = 0;i<obj.length;i++) {
				pstmt.setObject(i+1, obj[i]);
			}
			rows = pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rows;
	}
	//提供预处理语句的查询
	public ResultSet preQuery(String sql,Object...obj) {
		try {
			//如果是预处理  sql中?号 我点知道sql中有几个问号
			//给每个问号 赋值
			pstmt = conn.prepareStatement(sql);
			for(int i = 0;i<obj.length;i++) {
				pstmt.setObject(i+1, obj[i]);
			}
			rs = pstmt.executeQuery();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	//提供基于缓存的高级查询(做项目的时候能提升性能的)
	//技术 rs中的数据全部放入到内存中
	//查询缓存
	public ArrayList<HashMap<String,Object>> queryForList(String sql){
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()) {//行
				HashMap<String,Object> map = new HashMap<String,Object>();
				//直到有多少列
				for(int i = 1;i<=rsmd.getColumnCount(); i++) {//列
					//取出列名和列值   列名装入到map key  列值转入到map value
					String columnName =rsmd.getColumnLabel(i);
					Object columnValue = rs.getObject(columnName);
					map.put(columnName, columnValue);
				}
				
				list.add(map);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public void close() {
		
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
