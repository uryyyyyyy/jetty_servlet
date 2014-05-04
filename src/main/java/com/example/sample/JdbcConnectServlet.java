package com.example.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JdbcConnectServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		//from web.xml
		ServletContext w_SrvCont = getServletContext();

		try{
			System.out.println("1");
			//set params to connect DB
			Class.forName(w_SrvCont.getInitParameter("jdbcDriver"));
			String dbURI = w_SrvCont.getInitParameter("jdbcUri");
			String dbUID = w_SrvCont.getInitParameter("dbUser");
			String dbPWD = w_SrvCont.getInitParameter("dbPassword");

			System.out.println("2");
			System.out.println(dbURI + dbUID + dbPWD);
			//set connection
			Connection dbcnn = DriverManager.getConnection(dbURI, dbUID, dbPWD);
			Statement stmt = dbcnn.createStatement();
			
			System.out.println("3");

			
			//データの登録			
			//you apply set these sql
			//create table customers(id serial, first_name varchar(255), last_name varchar(255));
			//INSERT INTO customers(first_name,last_name) values('first','last');

			ResultSet rs  = stmt.executeQuery("select * from customers");

			//登録したデータの検索
			System.out.println("4");
			// 出力タイプの設定
			response.setContentType("text/html; charset=UTF-8");

			// 出力するhtml準備
			PrintWriter out = response.getWriter();

			// html作成
			out.println("<html><body>");
			System.out.println("10");
			while(rs.next())
            {
              out.println("<tr>");
              out.println("<td>" + rs.getString(2) + "</td>"
                        + "<td>" + rs.getString(3) + "</td>");
              out.println("</tr>");
            }
			System.out.println("11");
			out.println("</body></html>");
			// 出力する
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
