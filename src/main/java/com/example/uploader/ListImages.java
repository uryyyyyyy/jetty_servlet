package com.example.uploader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListImages
 */
@WebServlet("/ListImages")
public class ListImages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.println("<html><body>");
		
		String path = getServletContext().getRealPath("upload\\s");
		
		File directory = new File(path);
		
		File[] files = directory.listFiles();
		
		for (int i=0; i<files.length; i++) {
			String filename = files[i].getName();
		
			out.println(filename);
			out.println("<br>���摜�����N�Fupload/" + filename);
			out.println("<br>�T���l�[���摜�����N�Fupload/s/"
					+ filename);
			out.println("<br><a href=\"upload/" + filename
					+ "\">");
			out.println("<br><img src=\"upload/s/" + filename
					+ "\">");
			out.println("</a>");
			out.println("<br>");
		}
		out.println("</body></html>");
        out.close();
    }

}
