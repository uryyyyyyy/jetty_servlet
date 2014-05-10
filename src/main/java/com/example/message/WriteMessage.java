package com.example.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class WriteMessage
 */
@WebServlet("/WriteMessage")
public class WriteMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		boolean login = false;
		if (session.getAttribute("login") != null) {
			login = (Boolean)session.getAttribute("login");
		}
		String displayUserName
		= (String)session.getAttribute("displayUserName");

		if (!login) {
			response.sendRedirect("login.html");
		}

		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control",
				"post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires",0);
		Date today = new Date();
		response.setDateHeader("Last-Modified",
				today.getTime());

		response.setContentType("text/html; charset=UTF-8");
		writeOutput(response, displayUserName);
	}

	private void writeOutput(HttpServletResponse response,
			String displayUserName) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("Login: <b>" + displayUserName + "</b>");
		out.println("<hr>");
		out.println("<a href=\"Menu\">menu  </a>");
		out.println("<a href=\"Logout\">logout  </a>");
		out.println("<hr>");
		out.println("please write message<br><br>");
		out.println("<form action=\"WriteMessage\" method=\"POST\">");
		out.println("title  <br>");
		out.println("<input type=\"text\" name=\"title\" size=\"50\">");
		out.println("<br><br>");
		out.println("message  <br>");
		out.println("<textarea name=\"message\" cols=\"40\"	rows=\"5\"></textarea>");
		out.println("<br><br>");
		out.println("<input type=\"submit\" value=\"register\">");
		out.println("</form>");
		out.println("</body></html>");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		boolean login = false;
		if (session.getAttribute("login") != null) {
			login = (Boolean)session.getAttribute("login");
		}

		String displayUserName =
				(String)session.getAttribute("displayUserName");

		request.setCharacterEncoding("UTF-8");
		String title = request.getParameter("title");
		String message = request.getParameter("message");

		String sql = "INSERT INTO message_tb" +
				"(message_title,message,user_name)" +
				"VALUES" +
				"('" + title + "','" + message + "','" + displayUserName + "')";

		String outMessage = "";

		String database = "jdbc:mysql://localhost/sample_db" +
				"?useUnicode=true&characterEncoding=UTF8";
		String user = "sample_user";
		String password = "sample_pass";

		Connection connection = null;
		Statement statement = null;

		try {
			connection = DriverManager.getConnection(database,
					user, password);
			statement = connection.createStatement();

			if (login) {
				int rows = statement.executeUpdate(sql);
				outMessage = "register" + rows + "posts";
			}
		} catch (SQLException e) {
			outMessage = "error<br><br>" + e;
		}

		try {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		} catch (SQLException e) {
			outMessage = "error<br><br>"
					+ e;
		}

		if (!login) {
			response.sendRedirect("login.html");
		}

		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control",
				"post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires",0);
		Date today = new Date();
		response.setDateHeader("Last-Modified",
				today.getTime());

		response.setContentType("text/html; charset=UTF-8");
		writeOutput2(response, displayUserName, outMessage);
	}

	private void writeOutput2(HttpServletResponse response,
			String displayUserName, String outMessage) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("Login: <b>" + displayUserName + "</b>");
		out.println("<hr>");
		out.println("<a href=\"Menu\">menu  </a>");
		out.println("<a href=\"Logout\">logout  </a>");
		out.println("<hr>");
		out.println(outMessage);
		out.println("<br><br>");
		out.println("<a href=\"ShowMessage\">read messages</a>");
		out.println("</body></html>");
		out.close();
	}

}
