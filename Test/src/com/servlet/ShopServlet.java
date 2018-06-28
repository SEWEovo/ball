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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ShopServlet
 */
public class ShopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // TODO Auto-generated method stub  
        response.setContentType("text/html;charset=utf-8");   
        String username = request.getParameter("username"); 
        String name=request.getParameter("bookname");
        String price=request.getParameter("price");
        PrintWriter out = response.getWriter();  
        DBUtil.openConnection();
        String sql = "select * from shopcar where username='"+username+"' and bookname='"+name+"'";
		ResultSet rs = null;
		try {
			rs = DBUtil.executSql(sql);
			if(rs.next()){
				 String sql2 = "update shopcar set sum=sum+1 where username='"+username+"' and bookname='"+name+"'";
					boolean rs2 ;
					try {
						rs2 = DBUtil.executSqlUpdate(sql2);	 
							
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 	
					out.print(true);
			} 	
			else {
				 String sql2 = "insert into shopcar(username,bookname,sum,price) values('"+username+"','"+name+"',1,'"+price+"')";					boolean rs2 ;
					try {
						rs2 = DBUtil.executSqlUpdate(sql2);	 
							
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.print(true);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
