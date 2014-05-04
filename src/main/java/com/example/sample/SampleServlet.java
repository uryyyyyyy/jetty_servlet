package com.example.sample;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SampleServlet extends HttpServlet{

    private static final long serialVersionUID = 6569508331779151501L;

    /**
     * {@inheritDoc}
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
		// TODO Auto-generated method stub

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
}