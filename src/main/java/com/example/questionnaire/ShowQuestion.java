package com.example.questionnaire;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowQuestion
 */
@WebServlet("/ShowQuestion")
public class ShowQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html><body>");
		out.println("questionnaire results<br><br>");
		out.println("<table border=1>");
		out.println("<tr bgcolor=\"#CCCCCC\">");
		out.println("<td>purchase</td>");
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
		
		FileInputStream inputStream = null;
		InputStreamReader streamReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			inputStream
				= new FileInputStream("/media/shiba/671e0cbe-6a56-40ea-b262-448fb6d5bda1/git/jetty_servlet/question.csv");
			streamReader
				= new InputStreamReader(inputStream,"UTF-8");
			bufferedReader = new BufferedReader(streamReader);
		
			String line = "";
			
			while((line = bufferedReader.readLine()) != null ) {
				String[] data = line.split(",");
				
				out.println("<tr>");
				for (int i=0; i<data.length; i++) {
					out.println("<td>" + data[i] + "</td>");
				}
				out.println("</tr>");
			}
		} catch (IOException e) {
			out.println("<tr><td colspan=10>");
			out.println("error<br><br>" + e);
			out.println("</td></tr>");
		} finally {
			if (bufferedReader != null) bufferedReader.close();
			if (streamReader != null) streamReader.close();
			if (inputStream != null) inputStream.close();
		}
		out.println("</table>");
		out.close();
	}

}
