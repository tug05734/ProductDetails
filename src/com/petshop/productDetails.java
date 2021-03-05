package com.petshop;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.log.Log;
import com.petshop.DBConnection;

/**
 * Servlet implementation class productDetails
 */
@WebServlet("/productDetails")
public class productDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public productDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
		   String numId = request.getParameter("idnum");
		   Boolean found = false;
           PrintWriter out = response.getWriter();
           out.println("<html><body>");
           InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
           Properties props = new Properties();
           props.load(in);
           
           DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("userid"), props.getProperty("password"));
           java.sql.Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
           //PreparedStatement myStmt = myconn.prepareStatement("select * from products where id = ?");
           //myStmt.setInt(1, Integer.parseInt(numId));
           ResultSet rst = stmt.executeQuery("select * from products");
           
           if(numId.isEmpty()) {
        	   while(rst.next()) {
        		   out.println("<b>Product ID</b>: " + rst.getInt("id") + "   |   " + "<b>Product Name</b>: " +rst.getString("name") + "   |   " 
                		   + "<b>Product Color</b>: " + rst.getString("color") + "   |   " + "<b>Product Price</b>: " + rst.getDouble("price") +"<Br>");
                   found = true;
        	   }
           }else {
	           while (rst.next()) {
	        	   if(String.valueOf(rst.getInt("id")).equals(numId)) {
	        		   out.println("<h3>Product Found!</h3><br>");
	                   out.println("<b>Product ID</b>: " + rst.getInt("id") + "   |   " + "<b>Product Name</b>: " +rst.getString("name") + "   |   " 
	                		   + "<b>Product Color</b>: " + rst.getString("color") + "   |   " + "<b>Product Price</b>: " + rst.getDouble("price") +"<Br>");
	                   found = true;
	        	   }
	           }
           }
           
           if(!found) {out.println("<h3>Product not Found</h3><br>");}
           
           stmt.close();
           out.println("</body></html>");
           conn.closeConnection();
           
			} catch (ClassNotFoundException e) {
					e.printStackTrace();
			} catch (SQLException e) {
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
