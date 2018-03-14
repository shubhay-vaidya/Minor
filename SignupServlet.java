

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
   	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   	{
   		Connection con=null; PreparedStatement ps=null;
    	int i=0;
    	PrintWriter out=response.getWriter();
    	try 
   		{
    		Class.forName("com.mysql.jdbc.Driver");
    		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/minor","root","root" );
   			String name=request.getParameter("name");
   			String username=request.getParameter("username");
   			String password=request.getParameter("password");
   			String city=request.getParameter("city");
   			long contact_no=Long.parseLong(request.getParameter("contact_no"));
			ps=con.prepareStatement("insert into Customer(name,username,password,city,contact_no) values(?,?,?,?,?)");
			ps.setString(1, name);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.setString(4, city);
			ps.setLong(5, contact_no);
			i=ps.executeUpdate();
			if(i!=0)
			{
				//out.println("record inserted");
				response.sendRedirect("home.jsp");
			}
			else
			{
				out.println("registration failed");
			}
		}
   		catch (ClassNotFoundException e) 
   		{
   			out.println("something is wrong with driver class");
		}
   		catch(SQLException e)
   		{
   			out.println("something is wrong with connection");
   		}
   		finally
   		{
   			try 
   			{
				con.close();
			}
   			catch (SQLException e) 
   			{
   				out.println("failed to close connection");
			}
   		}
	}
}
