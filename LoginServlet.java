import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/lin")
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection con=null; PreparedStatement ps=null;
    	PrintWriter out=response.getWriter();
    	try 
   		{
    		Class.forName("com.mysql.jdbc.Driver");
    		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/minor","root","root" );
   			String username=request.getParameter("username");
   			String password=request.getParameter("password");
   			ps=con.prepareStatement("select * from Customer where username=? and password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				HttpSession s=request.getSession();
				s.setAttribute("idk",username);
				//out.println("login success");
				response.sendRedirect("home.jsp");
			}
			else
			{
				response.sendRedirect("login.html");
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
