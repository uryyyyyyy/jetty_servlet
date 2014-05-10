package com.example.questionnaire;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QueryQuestionDB
 */
@WebServlet("/QueryQuestionDB")
public class QueryQuestionDB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String startDate = request.getParameter("sdate");
		String endDate = request.getParameter("edate");
		String sort = request.getParameter("sort");
		
		if (startDate == null) startDate = "2011/04/01";
		if (endDate == null) endDate = "2011/04/30";
		if (sort == null) sort = "date";
		
		String sql = "SELECT purchase_date,purchase_price,star," +
			"lang_php,lang_perl,lang_java," +
			"lang_cs,lang_cpp,lang_basic," +
			"job" +
			" FROM question_tb" +
			" WHERE purchase_date >= '" + startDate + "'" +
			" AND purchase_date <= '" + endDate + "'";
		
		if (sort.equals("date")) {
			sql += " ORDER BY purchase_date";
		} else {
			sql += " ORDER BY star";
		}
		
		String database = "jdbc:mysql://localhost/sample_db" +
			"?useUnicode=true&characterEncoding=UTF8";
		String user = "sample_user";
		String password = "sample_pass";
		
		String outMessage = "";
		String data = "";
		int rows = 0;
		
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection
				= DriverManager.getConnection(database, user, password);
			statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(sql);
		
			while(resultSet.next()) {
				data += "<tr>";
				data += "<td>" + resultSet.getString("purchase_date") + "</td>";
				data += "<td>" + resultSet.getString("purchase_price") +"</td>";
				data += "<td>" + resultSet.getString("star") +"</td>";
				data += "<td>" + resultSet.getString("lang_php") + "</td>";
				data += "<td>" + resultSet.getString("lang_perl") + "</td>";
				data += "<td>" + resultSet.getString("lang_java") + "</td>";
				data += "<td>" + resultSet.getString("lang_cs") + "</td>";
				data += "<td>" + resultSet.getString("lang_cpp") + "</td>";
				data += "<td>" + resultSet.getString("lang_basic") + "</td>";
				data += "<td>" + resultSet.getString("job") + "</td>";
				data += "</tr>";

				rows++;
			}
		
			outMessage = "get" + rows + "posts data";
		} catch (SQLException e) {
			outMessage = "error<br><br>" + e;
		}
		
		try {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		} catch (SQLException e) {
			outMessage += "<br><br>";
			outMessage += "error<br><br>" + e;
		}
		
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.println("<html><body>");
		out.println("input search factor<br><br>");
		out.println("<form action=\"QueryQuestionDB\" method=\"GET\">");
		out.println("parchase date<br>");
		out.println("<input type=\"text\" name=\"sdate\" value=\"" +
				startDate + "\">");
		out.println(" - <input type=\"text\" name=\"edate\" value=\"" +
				endDate + "\">");
		out.println("<br><br>");
		out.println("moke<br>");
		if (sort.equals("date")) {
			out.println("<input type=\"radio\" name=\"sort\"" +
				"value=\"date\" checked>sort date");
			out.println("<input type=\"radio\" name=\"sort\"" +
				"value=\"star\">sort star");
		} else {
			out.println("<input type=\"radio\" name=\"sort\"" +
				"value=\"date\">sort parchase");
			out.println("<input type=\"radio\" name=\"sort\"" +
				"value=\"star\" checked>sort star");
		}
		writeOutput(outMessage, data, out);
		
		out.close();
	}

	private void writeOutput(String outMessage, String data, PrintWriter out) {
		out.println("<br><br>");
		out.println("<input type=\"submit\" value=\"???\">");
		out.println("</form>");
		
		out.println("results<br><br>");
		out.println("<table border=1>");
		out.println("<tr bgcolor=\"#CCCCCC\">");
		out.println("<td>date</td>");
		out.println("<td>price</td>");
		out.println("<td>star</td>");
		out.println("<td>PHP</td>");
		out.println("<td>Perl</td>");
		out.println("<td>Java</td>");
		out.println("<td>C#</td>");
		out.println("<td>C++</td>");
		out.println("<td>Basic</td>");
		out.println("<td>job</td>");
		out.println("</tr>");
		out.println(data);
		out.println("</table>");
		out.println("<br><br>");
		out.println(outMessage);
		out.println("</body></html>");
	}

}
