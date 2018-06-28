package com.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	private static Connection con=null;
	private static String conStr="jdbc:mysql://127.0.0.1/test?user=root&password=root&characterEncoding=utf8&useSSL=true";
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean openConnection(){
		try {
			if(con==null||con.isClosed()){
				con=DriverManager.getConnection(conStr);
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public static boolean closeConnection() throws SQLException{
		if(con!=null&&!con.isClosed())
			con.close();
		return false;
	}
	public static ResultSet executSql(String sql) throws SQLException{
		Statement st=con.createStatement();
		return st.executeQuery(sql);
	}
	public static ResultSet executSqlmove(String sql) throws SQLException{
		Statement st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		return st.executeQuery(sql);
	}
	public static boolean executSqlUpdate(String sql) throws SQLException{
		Statement st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		return st.execute(sql);
	}
	public static boolean executNoSql(String sql) throws SQLException{
		Statement st=con.createStatement();
		st.executeUpdate(sql);
		return true;
	}
}
