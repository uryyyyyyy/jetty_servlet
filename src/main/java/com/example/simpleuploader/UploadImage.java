package com.example.simpleuploader;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UploadImage
 */
@WebServlet("/UploadImage")
// �}���`�p�[�g�f�[�^�̐ݒ�
@MultipartConfig(location="", maxFileSize=1024*1024*2)
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// �󂯎��f�[�^�̕����R�[�h��UTF-8�ɃZ�b�g����
		request.setCharacterEncoding("UTF-8");
		
		// �p�����[�^�[�gfilename�h�̃}���`�p�[�g�f�[�^�l���擾
		Part part = request.getPart("filename");
		
		// HTTP�w�b�_�[�gcontent-disposition�h�̒l���擾
		String contentDisposition = part.getHeader("content-disposition");
		// MIME�^�C�v�gcontent-type�h�̎擾
		String contentType = part.getHeader("content-type");
		// �t�@�C���T�C�Y�̎擾
		long size = part.getSize();
		
		// upload�t�H���_�[�̐�΃p�X�𒲂ׂ�
		String path = getServletContext().getRealPath("upload");
		
		// �ϐ�contentDisposition����ufilename=�v�ȍ~�𔲂��o��
		int filenamePosition
			= contentDisposition.indexOf("filename=");
		String filename
			= contentDisposition.substring(filenamePosition);
		
		// �ufilename=�v�Ɓu"�v����菜��
		filename = filename.replace("filename=", "");
		filename = filename.replace("\"", "");
		
		// �t�@�C���������o��
		filename = new File(filename).getName();
		
		// JPEG�`���̉摜�̂ݕۑ�����
		if ((contentType.equals("image/jpeg"))
				|| (contentType.equals("image/pjpeg"))) {
			// �摜�t�@�C����path�{filename�Ƃ��ĕۑ�����
			part.write(path + "\\" + filename);
			
			// �T���l�[���摜�̍쐬
			createThumbnail(path + "\\" + filename,
					path + "\\s\\" + filename,
					120);
		}
		
		// �o�͐�̃R���e���g�^�C�v���Z�b�g����
		response.setContentType("text/html; charset=UTF-8");
		
		// �o�͐�����o���A�ϐ�out�ɑ���
		PrintWriter out = response.getWriter();
		
		// �o�͓��e���Z�b�g����
		out.println("<html><body>");
		// JPEG�`���̃`�F�b�N
		if ((contentType.equals("image/jpeg"))
				|| (contentType.equals("image/pjpeg"))) {
			// �A�b�v���[�h�������b�Z�[�W
			out.println("���A�b�v���[�h�t�@�C����ۑ����܂����B<br><br>");
			out.println("��΃p�X�F" + path);
			out.println("<br><br>");
			out.println("�ۑ��ꏊ�F " + path + "\\" + filename);
			out.println("<br><br>");
			// �摜��\������
			out.println("<img src=\"upload\\" + filename + "\">");
			out.println("<br><br>");
		} else {
			// �摜�`�F�b�N�G���[���b�Z�[�W
			out.println("��JPEG�`���̉摜���A�b�v���[�h���Ă��������B");
			out.println("<br><br>");
		}
		out.println("���A�b�v���[�h�t�@�C�����<br><br>");
		out.println("HTTP�w�b�_�[�icontent-disposition�j�F "
				+ contentDisposition);
		out.println("<br><br>");
		out.println("MIME�^�C�v�icontent-type�j�F " + contentType);
		out.println("<br><br>");
		out.println("�t�@�C���T�C�Y�F " + size + "�o�C�g");
		out.println("</body></html>");
		
		// �o�͂���
		out.close();
	}

	// �T���l�[���摜���쐬���ĕۑ����郁�\�b�h
	private void createThumbnail(String originFile,
			String thumbnailFile, int width) {
		try {
			// ���摜�̓ǂݍ���
			BufferedImage image = ImageIO.read(new File(originFile));
			
			// ���摜�̏����擾
			int originWidth = image.getWidth();
			int originHeight = image.getHeight();
			int type = image.getType();
			
			// �k���摜�̍������v�Z
			int height = originHeight * width / originWidth;

			// �k���摜�̍쐬
			BufferedImage smallImage
				= new BufferedImage(width, height, type);
			Graphics2D g2 = smallImage.createGraphics();
			
			// �`��A���S���Y���̐ݒ�
			// �`��F�i���D��
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			// �A���`�G�C���A�X�FON
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			// ���摜�̏k��
			g2.drawImage(image, 0, 0, width, height, null);
			
			// �k���摜�̕ۑ�
			ImageIO.write(smallImage, "jpeg", new File(thumbnailFile));
			
		} catch (Exception e) {
			// �摜�̏k���Ɏ��s
			log("�摜�̏k���Ɏ��s : " + e);
		}
	}
}
