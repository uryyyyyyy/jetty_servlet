package com.example.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
 * Servlet implementation class ShowMessage
 */
@WebServlet("/ShowMessage")
public class ShowMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sql = "SELECT message_id,message_title,message," +
				"user_name,entry_date" +
				" FROM message_tb" +
				" ORDER BY entry_date";

		String outMessage = "";
		String data = "";
		int rows = 0;

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

			ResultSet resultSet = statement.executeQuery(sql);

			while(resultSet.next()) {
				data += "<tr>";
				data += "<td>" +
						resultSet.getString("message_id") + "</td>";
				data += "<td>" +
						resultSet.getString("message_title") + "</td>";
				data += "<td>" +
						resultSet.getString("message") + "</td>";
				data += "<td>" +
						resultSet.getString("user_name") + "</td>";
				data += "<td>" +
						resultSet.getString("entry_date") + "</td>";
				data += "</tr>";

				rows++;
			}

			outMessage = "�����b�Z�[�W��" + rows + "�����o���܂����B";
		} catch (SQLException e) {
			outMessage = "�����b�Z�[�W�̎��o���Ɏ��s���܂����B<br><br>" + e;
		}

		try {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		} catch (SQLException e) {
			outMessage = "���f�[�^�x�[�X�̐ؒf�Ɏ��s���܂����B<br><br>" + e;
		}

		HttpSession session = request.getSession();

		boolean login = false;
		if (session.getAttribute("login") != null) {
			login = (Boolean)session.getAttribute("login");
		}

		String displayUserName =
				(String)session.getAttribute("displayUserName");

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
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("Login: <b>" + displayUserName + "</b>");
		out.println("<hr>");
		out.println("<a href=\"Menu\">�y���j���[�z</a>");
		out.println("<a href=\"Logout\">�y���O�A�E�g�z</a>");
		out.println("<hr>");
		out.println("�����b�Z�[�W�ꗗ<br><br>");
		out.println("<table border=1>");
		out.println("<tr bgcolor=\"#CCCCCC\">");
		out.println("<td>ID</td>");
		out.println("<td>�^�C�g��</td>");
		out.println("<td>���b�Z�[�W</td>");
		out.println("<td>���[�U�[</td>");
		out.println("<td>�o�^��</td>");
		out.println("</tr>");
		out.println(data);
		out.println("</table>");
		out.println("<br><br>");
		out.println(outMessage);
		out.println("</body></html>");
		out.close();
	}
}
