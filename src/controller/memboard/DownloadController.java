package controller.memboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemBoardDAO;

public class DownloadController extends HttpServlet{

	//다운로드는 브라우저가 제어하므로 
	
	//얘가 받는건 글번호랑 이름!
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		//확인용
		
		// 확인용
		System.out.println("[-- Controller Confirm --]");
		System.out.println("Current Controller : " + this.getClass().getName());
		System.out.println("Method" + req.getMethod());
		System.out.println("URL FROM : " + req.getHeader("referer"));
		System.out.println("Session Checker : " + req.getSession().getAttribute("user"));
		
		
		//세션 확인 코드 
		if(req.getSession().getAttribute("user")==null) {
			req.getSession().invalidate();
			req.getRequestDispatcher("/login/LoginDivider.jsp").forward(req, resp);
		}
		
		String attachFile = req.getParameter("attachFile");
		String no = req.getParameter("no");
		
		
	
		//Download 모델
		
		utils.FileUtils.download(req, resp,attachFile, "/upload");
		
		MemBoardDAO dao = new MemBoardDAO(getServletContext());
		
		dao.updateDownCount(no);
		
		dao.close();
		
	}
	
}
