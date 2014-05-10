package com.example.simpleuploader;

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
     * @see HttpServlet#HttpServlet()
     */
    public ListImages() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    
		// �o�͐�̃R���e���g�^�C�v���Z�b�g����
		response.setContentType("text/html; charset=UTF-8");
		
		// �o�͐�����o���A�ϐ�out�ɑ���
		PrintWriter out = response.getWriter();
		
		// �o�͓��e���Z�b�g����
		out.println("<html><body>");
		
		// upload\s�t�H���_�[�̐�΃p�X�𒲂ׂ�
		String path = getServletContext().getRealPath("upload\\s");
		
		// File�^�I�u�W�F�N�g�ɕϊ�
		File directory = new File(path);
		
		// �f�B���N�g�����̂��ׂẴt�@�C���𒊏o
		File[] files = directory.listFiles();
		
		// ���ׂẴt�@�C����\��
		for (int i=0; i<files.length; i++) {
			// i�Ԗڂ̃t�@�C���������o��
			String filename = files[i].getName();
		
			// i�Ԗڂ̃t�@�C����\��
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
		
		// �o�͂���
        out.close();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
