

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.DBConnection;

/**
 * Servlet implementation class tob
 */
public class tob extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public tob() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		try {
			out.println("<html><body>");
			out.println("<style>\r\n"
					+ "table {\r\n"
					+ "  border-collapse: collapse;\r\n"
					+ "  width: 100%;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "th, td {\r\n"
					+ "  text-align: left;\r\n"
					+ "  padding: 8px;\r\n"
					+ "}\r\n"
					+ "\r\n"
					+ "tr:nth-child(even) {background-color: #f2f2f2;}\r\n"
					+ "</style>");

			InputStream in = getServletContext().getResourceAsStream("config.properties");
			Properties props = new Properties();
			props.load(in);

			String id = request.getParameter("id");

			DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("userid"), props.getProperty("password"));
			Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rst = stmt.executeQuery("select * from eproduct where id="+id);
			rst.last();
			if(rst.getRow()>0) {
				rst.first();
				out.println("<table>");
				out.println("<tr><th>"+"Name" + "</th><th>" + "Price" + "</th></th>");
				
					out.println("<tr><td>"+rst.getString("name") + "</td><td>" + rst.getInt("price") + "</td></tr>");
				
				out.println("</table>");
			}
			else {
				out.println("Record Not Found!");
			}
			stmt.close();



			out.println("</body></html>");
			conn.closeConnection();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			out.println("An Exception Has Occurred!");
		} catch (SQLException e) {
			e.printStackTrace();
			out.println("Please Enter Valid Id!");
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
