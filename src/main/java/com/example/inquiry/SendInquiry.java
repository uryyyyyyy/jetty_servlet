package com.example.inquiry;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.smtp.SMTPTransport;


@WebServlet("/SendInquiry")
public class SendInquiry extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {
			// プロパティの設定
			Properties props = System.getProperties();
			// ホスト
			props.put("mail.smtp.host", "smtp.gmail.com");
			// 認証（する）
			props.put("mail.smpt.auth", "true");
			// ポート指定（サブミッションポート）
			props.put("mail.smtp.port", "587");
			// STARTTLSによる暗号化（する）
			props.put("mail.smtp.starttls.enable", "true");

			// セッションの取得
			Session session = Session.getInstance(props);

			// MimeMessageの取得と設定
			Message msg = new MimeMessage(session);
			// 送信者設定
			msg.setFrom(new InternetAddress("GMailアカウント名@gmail.com"));
			// 宛先設定
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("送信先メールアドレス", false));
			// タイトル設定
			msg.setSubject(request.getParameter("title"));
			// 本文設定
			msg.setText(request.getParameter("message"));
			// 送信日時設定
			msg.setSentDate(new Date());

			// 送信
			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
			try {
				t.connect("smtp.gmail.com", "GMailアカウント名", "GMailパスワード");
				t.sendMessage(msg, msg.getAllRecipients());
			} finally {
				t.close();
			}

			out.println("<html><body>");
			out.println("message posted");
			out.println("</body></html>");
		} catch (Exception e) {
			out.println("<html><body>");
			out.println("failed to post");
			out.println("<br>error massage"+e);
			out.println("</body></html>");
		}
		out.close();
	}

}
