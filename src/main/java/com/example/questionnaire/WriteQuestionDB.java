package com.example.questionnaire;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WriteQuestionDB
 */
@WebServlet("/WriteQuestionDB")
public class WriteQuestionDB extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String purchaseDate = request.getParameter("pdate");
		if (!CheckQuestion.checkDate(purchaseDate))
			purchaseDate = "";
		String purchasePrice = request.getParameter("pprice");
		if (!CheckQuestion.checkNumber(purchasePrice))
			purchasePrice = "";
		String star = request.getParameter("star");
		String job = request.getParameter("job");

		List<String> langs = Arrays.asList(request.getParameterValues("lang"));

		List<Boolean> outLangs = new ArrayList<>();
		for(int i = 0; i<6; i++){
			outLangs.add(false);
		}
		for (String lang : langs) {
			if (lang.equals("PHP")) outLangs.set(0, true);
			if (lang.equals("Perl")) outLangs.set(1, true);
			if (lang.equals("Java")) outLangs.set(2, true);
			if (lang.equals("C#")) outLangs.set(3, true);
			if (lang.equals("C++")) outLangs.set(4, true);
			if (lang.equals("Basic")) outLangs.set(5, true);
		}

		String outMessage = "";

		String sql = "INSERT INTO question_tb(" +
				"purchase_date," +
				"purchase_price," +
				"star," +
				"lang_php,lang_perl,lang_java," +
				"lang_cs,lang_cpp,lang_basic," +
				"job)" +
				"VALUES (" +
				"'" + purchaseDate + "'," +
				purchasePrice + "," +
				star + "," +
				outLangs.get(0) + "," + outLangs.get(1) + "," +
				outLangs.get(2) + "," + outLangs.get(3) + "," +
				outLangs.get(4) + "," + outLangs.get(5) + "," +
				"'" + job + "')";

		String database = "jdbc:mysql://localhost/sample_db" +
				"?useUnicode=true&characterEncoding=utf8";
		String user = "sample_user";
		String password = "sample_pass";

		Connection connection = null;
		Statement statement = null;

		try {
			connection
			= DriverManager.getConnection(database, user,
					password);
			statement = connection.createStatement();

			int rows = statement.executeUpdate(sql);

			outMessage = "registered" + rows + "posts";
		} catch (SQLException e) {
			outMessage = "error<br><br>"
					+ e;
		}

		try {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		} catch (SQLException e) {
			outMessage += "<br><br>";
			outMessage += "error<br><br>"
					+ e;
		}

		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();

		out.println("<html><body>");
		out.println(outMessage);
		out.println("</body></html>");

		out.close();
	}

}
