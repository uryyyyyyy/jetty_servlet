package com.example.uploader;

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
@MultipartConfig(location="", maxFileSize=1024*1024*2)
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Part part = request.getPart("filename");
		
		String contentDisposition = part.getHeader("content-disposition");
		//System.out.println(contentDisposition);
		
		String path = getServletContext().getRealPath("upload");
		//System.out.println(path);
		
		String filename = setFileName(contentDisposition);
		//System.out.println(filename);
		
		String contentType = part.getHeader("content-type");
		//System.out.println(contentType);
		saveFileAndThumbnail(part, path, filename, contentType);
		
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.println("<html><body>");
		if ((contentType.equals("image/jpeg"))
				|| (contentType.equals("image/pjpeg"))) {
			createHtmlWithImage(path, filename, out);
		} else {
			createHtmlWithoutImage(out);
		}
		createHtmlFooter(part, contentDisposition, contentType, out);
		out.close();
	}

	private void createHtmlFooter(Part part, String contentDisposition,
			String contentType, PrintWriter out) {
		out.println("file details<br><br>");
		out.println("HTTP header : "
				+ contentDisposition);
		out.println("<br><br>");
		out.println("MIME type : " + contentType);
		out.println("<br><br>");
		out.println("file size : " + part.getSize() + "byte");
		out.println("</body></html>");
	}

	private void createHtmlWithoutImage(PrintWriter out) {
		out.println("upload failed");
		out.println("<br><br>");
	}

	private void createHtmlWithImage(String path, String filename,
			PrintWriter out) {
		out.println("upload done<br><br>");
		out.println("absolute path  :  " + path);
		out.println("<br><br>");
		out.println("rerative path : " + path + "/" + filename);
		out.println("<br><br>");
		out.println("<img src=\"upload/" + filename + "\">");
		out.println("<br><br>");
	}

	private void saveFileAndThumbnail(Part part, String path, String filename,
			String contentType) throws IOException {
		if ((contentType.equals("image/jpeg"))
				|| (contentType.equals("image/pjpeg"))) {
			System.out.println(path + "/" + filename);
			part.write(path + "/" + filename);
			
			createThumbnail(path + "/" + filename,
					path + "/s/" + filename,
					120);
		}
	}

	private String setFileName(String contentDisposition) {
		int filenamePosition
			= contentDisposition.indexOf("filename=");
		String filename
			= contentDisposition.substring(filenamePosition);
		
		filename = filename.replace("filename=", "");
		filename = filename.replace("\"", "");
		
		filename = new File(filename).getName();
		return filename;
	}

	private void createThumbnail(String originFile,
			String thumbnailFile, int width) {
		try {
			BufferedImage image = ImageIO.read(new File(originFile));
			
			int originWidth = image.getWidth();
			int originHeight = image.getHeight();
			int type = image.getType();
			
			int height = originHeight * width / originWidth;

			BufferedImage smallImage
				= new BufferedImage(width, height, type);
			Graphics2D g2 = smallImage.createGraphics();
			
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			g2.drawImage(image, 0, 0, width, height, null);
			
			ImageIO.write(smallImage, "jpeg", new File(thumbnailFile));
			
		} catch (Exception e) {
			log("error : " + e);
		}
	}
}
