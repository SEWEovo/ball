package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connect.DBUtil;

/**
 * Servlet implementation class RegistServlet
 */
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // TODO Auto-generated method stub  
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        String username = request.getParameter("username");  
        String password = request.getParameter("password");
        DBUtil.openConnection();
        String sql = "select * from user where username='"+username+"'";
		ResultSet rs = null;
		try {
			rs = DBUtil.executSql(sql);
			if(rs.next()){
				out.print("1233"); 
				
			} 
			else {
				out.print("123"); 
				 String sql2 = "insert into user(username,password) values('"+username+"','"+password+"')";
					boolean rs2 ;
					try {
						rs2 = DBUtil.executSqlUpdate(sql2);
						 
							
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result=null; 
 

	    } 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
