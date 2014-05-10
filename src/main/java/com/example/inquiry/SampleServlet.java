package com.example.inquiry;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class SampleServlet extends HttpServlet{

    private static final long serialVersionUID = 6569508331779151501L;

    /**
     * {@inheritDoc}
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

		// 文字コード
		request.setCharacterEncoding("UTF-8");
		
		// titleのパラメータ（value）を受け取る
		String title = request.getParameter("title");
		
		// 出力タイプの設定
		response.setContentType("text/html; charset=UTF-8");
		
		// 出力するhtml準備
		PrintWriter out = response.getWriter();
		
		// html作成
		out.println("<html><body>");
		out.println("your title is "+title);
		out.println("</body></html>");
		
		// 出力する
		out.close();
	}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String title = request.getParameter("title");
		String message = request.getParameter("message");
		
		title = replaceInput(title);
		message = replaceInput(message);
		
		message = message.replace("\r\n","<br>");
		
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.println("<html><body>");
		out.println("kakunin<br><br>");
		out.println("title<br>");
		out.println(title);
		out.println("<br><br>");
		out.println("content<br>");
		out.println(message);
		
		out.println("<form action=\"SendInquiry\" method=\"POST\">");
		out.println("<input type=\"hidden\" name=\"title\" value=\""+title+"\">");
		out.println("<input type=\"hidden\" name=\"message\" value=\""+message+"\">");
		out.println("<input type=\"submit\" value=\"send\">");
		out.println("</form>");
		
		out.println("</body></html>");
		
		out.close();
	}
	
	private String replaceInput(String inputData)
	{
		String outputData = inputData;
		outputData = outputData.replace("&","&amp;");
		outputData = outputData.replace("\"","&quot;");
		outputData = outputData.replace("<","&lt;");
		outputData = outputData.replace(">","&gt;");
		outputData = outputData.replace("'","&#039;");
		return outputData;
	}
}