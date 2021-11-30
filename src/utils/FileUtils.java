package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class FileUtils {

	private static void mkDir(String saveDir) {

		File folder = new File(saveDir);
		
		if (!folder.exists()) {
			try {
				folder.mkdir(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println("이미 폴더가 생성되어 있습니다.");
			return;
		}

	}

	public static MultipartRequest upload(HttpServletRequest req, String saveDir) {
		
		mkDir(saveDir);
		
		MultipartRequest mr = null;

		try {

			mr = new MultipartRequest(req, saveDir, 500 * 1024, "UTF-8", new DefaultFileRenamePolicy());

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return mr;
		}

		return mr;
	}

	public static void deleteFile(HttpServletRequest req, String webPath, String filename) {

		String saveDir = req.getServletContext().getRealPath(webPath);

		File file = new File(saveDir + File.separator + filename);

		if (file.exists())
			file.delete();
	}

	public static void download(HttpServletRequest request, HttpServletResponse response, String filename,
			String webPath) {

		String saveDirectory = request.getServletContext().getRealPath(webPath);

		// 옵션] 프로그래스바 - 파일 크기를 알아야함

		File file = new File(saveDirectory + File.separator + filename);
		long len = file.length();

		// 컨텐트타입 설정
		response.setContentType("application/octet-stream");

		// 프로그래스바 설정을 위한거임
		response.setContentLengthLong(len);

		// IE 판단 : 헤더에 MSIE , EDGE 값이 있거나 11.0 의 값이 존재할 경우
		boolean isIe = request.getHeader("user-agent").toUpperCase().indexOf("MSIE") != -1
				|| request.getHeader("user-agent").indexOf("11.0") != -1
				|| request.getHeader("user-agent").toUpperCase().indexOf("EDGE") != -1 ? true : false;

		response.setHeader("Content-Disposition", "attachment;filename=" + filename + "");

		try {
			if (isIe) {
				filename = URLEncoder.encode(filename, "UTF-8");
			} else {
				// 기타브라우저
				// charset 8859_1의 인코딩 방식을 따른다
				// 문자열을 위의 인코딩방식으로 byte배열로 바꾼다.

				filename = new String(filename.getBytes("UTF-8"), "8859_1");

			}
			BufferedInputStream bis;

			bis = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

			int data;
			while ((data = bis.read()) != -1) {

				bos.write(data);
				bos.flush();
			}
			bis.close();
			bos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
