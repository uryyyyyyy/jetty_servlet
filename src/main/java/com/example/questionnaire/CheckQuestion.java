package com.example.questionnaire;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckQuestion
 */
@WebServlet("/CheckQuestion")
public class CheckQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");
		
		String purchaseDate = request.getParameter("pdate");
		String purchasePrice = request.getParameter("pprice");
		String star = request.getParameter("star");
		String[] lang = request.getParameterValues("lang");
		String job = request.getParameter("job");
		
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.println("<html><body>");
		out.println("check your answer<br><br>");
		out.println("purchaseDate<br>");
		if (checkDate(purchaseDate)) {
			out.println(purchaseDate);
		} else {
			out.println(purchaseDate + " # date format error");
		}
		out.println("<br><br>");
		out.println("purchasePrice<br>");
		if (checkNumber(purchasePrice)) {
			out.println(purchasePrice);
		} else {
			out.println(purchasePrice + " # num format error");
		}
		out.println("<br><br>");
		out.println("star<br>");
		out.println(star);
		out.println("<br><br>");
		
		out.println("lang<br>");
		for (int i=0; i<lang.length; i++) {
			out.println("[" + lang[i] + "]");
		}
		out.println("<br><br>");
		
		out.println("job<br>");
		out.println(job);
		out.println("<br><br>");
		
		createPostButton(purchaseDate, purchasePrice, star, out);
		
		for (int i=0; i<lang.length; i++) {
			out.println("<input type=\"hidden\" name=\"lang\" value=\""
					+ lang[i] + "\">");
		}
		
		out.println("<input type=\"hidden\" name=\"job\" value=\""
				+ job + "\">");
		out.println("<input type=\"submit\" value=\"post your answer\">");
		out.println("</form>");
		
		out.println("</body></html>");
		out.close();
	}

	private void createPostButton(String purchaseDate, String purchasePrice,
			String star, PrintWriter out) {
		out.println("<form action=\"WriteQuestion\" method=\"POST\">");//WriteQuestionDB
		out.println("<input type=\"hidden\" name=\"pdate\"value=\""
				+ purchaseDate + "\">");
		out.println("<input type=\"hidden\" name=\"pprice\" value=\""
				+ purchasePrice + "\">");
		out.println("<input type=\"hidden\" name=\"star\" value=\""
				+ star + "\">");
	}
	
	public static boolean checkNumber(String numberString) {
		try {
			Integer.parseInt(numberString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean checkDate(String dateString) {
		SimpleDateFormat dateFormat
			= new SimpleDateFormat("yyyy/MM/dd");
		dateFormat.setLenient(false);
		
		try {
			dateFormat.parse(dateString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
