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
 * Servlet implementation class BookServlet
 */
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub  
        response.setContentType("text/json;charset=utf-8"); 
        String id = request.getParameter("id");  
//        String id = "1";
        PrintWriter out = response.getWriter();  
        DBUtil.openConnection();
        String sql = "select * from book where id='"+id+"'";
		ResultSet rs = null;
		try {
			rs = DBUtil.executSql(sql);
			JSONObject jsonobj=new JSONObject();
			while(rs.next()){
				 jsonobj.put("id",rs.getInt("id"));
				 jsonobj.put("name",rs.getString("name"));
				 jsonobj.put("description", rs.getString("description"));
				 jsonobj.put("price",rs.getString("price"));
				 jsonobj.put("number",rs.getString("number"));
			    }
			
			out.print(jsonobj);			
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
