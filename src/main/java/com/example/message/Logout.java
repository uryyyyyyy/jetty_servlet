package com.example.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		session.invalidate();
		
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
		out.println("�����O�A�E�g���܂����B");
		out.println("</body></html>");
		out.close();
	}

}
