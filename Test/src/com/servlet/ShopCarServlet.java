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
 * Servlet implementation class ShopCarServlet
 */
public class ShopCarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopCarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub  
        response.setContentType("text/json;charset=utf-8"); 
        String username = request.getParameter("username");  
        PrintWriter out = response.getWriter();  
        DBUtil.openConnection();
        String sql = "select name,description,book.price*sum AS price,sum from "
        		+ "shopcar,book,user where user.username=shopcar.username and "
        		+ "shopcar.bookname=book.name and shopcar.username='"+username+"'";
//        String sql="select * from shopcar";
		ResultSet rs = null;
		try {
			rs = DBUtil.executSql(sql);
			JSONArray jsonarray=new JSONArray();
			JSONObject jsonobj=new JSONObject();
			while(rs.next()){
				 jsonobj.put("name",rs.getString("name"));
				 jsonobj.put("description", rs.getString("description"));
				 jsonobj.put("price",rs.getString("price"));
				 jsonobj.put("sum", rs.getInt("sum"));
				 jsonarray.add(jsonobj);			 	
			    }
			
			out.print(jsonarray);			
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
