package com.example.questionnaire;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WriteQuestion
 */
@WebServlet("/WriteQuestion")
public class WriteQuestion extends HttpServlet {
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

		FileOutputStream outputStream = null;

		try {
			outputStream
			= new FileOutputStream("/hoge/question.csv",
					true);
			writeStream(purchaseDate, purchasePrice, request.getParameter("star"), request.getParameter("job"),
					outLangs, outMessage, outputStream);

			outMessage = "registered";
		} catch (IOException e) {
			outMessage = "fail to register<br><br>" + e;
		} finally {
			if (outputStream != null) outputStream.close();
		}

		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();

		out.println("<html><body>");
		out.println(outMessage);
		out.println("</body></html>");

		out.close();
	}

	private void writeStream(String purchaseDate,
			String purchasePrice, String star, String job, List<Boolean> outLang,
			String outMessage, FileOutputStream outputStream)
			throws UnsupportedEncodingException, IOException {
		OutputStreamWriter streamWriter = null;
		try{
		streamWriter
		= new OutputStreamWriter(outputStream,
				"UTF-8");
		writeToBuffer(purchaseDate, purchasePrice, star,
				job, outLang, streamWriter, outMessage);
		
		}catch (IOException e) {
			outMessage = "fail to register<br><br>" + e;
		} finally {
			if (streamWriter != null) streamWriter.close();
			}
		
	}

	private void writeToBuffer(String purchaseDate,
			String purchasePrice, String star, String job, List<Boolean> outLang,
			OutputStreamWriter streamWriter, String outMessage) throws IOException {
		BufferedWriter bufferedWriter = null;
		try{
		bufferedWriter = new BufferedWriter(streamWriter);

		bufferedWriter.write(purchaseDate);
		bufferedWriter.write(",");
		bufferedWriter.write(purchasePrice);
		bufferedWriter.write(",");
		bufferedWriter.write(star);
		bufferedWriter.write(",");
		for (int i=0; i<6; i++) {
			bufferedWriter.write(outLang.get(i).toString());
			bufferedWriter.write(",");
		}
		bufferedWriter.write(job);
		bufferedWriter.newLine();
		}catch(IOException e){
			outMessage = "fail to register<br><br>" + e;
		} finally {
			if (bufferedWriter != null) bufferedWriter.close();
		}
	}

}
