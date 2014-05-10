package com.example.message;

import java.io.IOException;
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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckLogin
 */
@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String userName = request.getParameter("uname");
		String userPassword = request.getParameter("upass");

		String sql = "SELECT user_name FROM user_tb" +
				" WHERE login_name = '" + userName + "'" +
				" AND login_password = '" + userPassword + "'";

		String displayUserName = "";
		boolean login = false;

		String database = "jdbc:mysql://localhost/sample_db" +
				"?useUnicode=true&characterEncoding=UTF8";
		String user = "sample_user";
		String password = "sample_pass";

		Connection connection = null;
		Statement statement = null;

		try {
			connection
			= DriverManager.getConnection(database, user,
					password);
			statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while(resultSet.next()) {
				displayUserName = resultSet.getString("user_name");
				login = true;
			}
		} catch (SQLException e) {
			login = false;
			log("�ڑ����s�F" + e);
		}

		try {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		} catch (SQLException e) {
			log("�ؒf���s�F" + e);
		}

		HttpSession session = request.getSession();

		session.setAttribute("login", login);

		session.setAttribute("displayUserName", displayUserName);

		if (login) {
			response.sendRedirect("Menu");
		} else {
			response.sendRedirect("login.html");
		}
	}

}
