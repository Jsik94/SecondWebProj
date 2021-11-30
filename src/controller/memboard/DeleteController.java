package controller.memboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CodeImpl;
import model.CustomDAO;
import model.MemBoardDAO;
import model.MemBoardDTO;

public class DeleteController extends HttpServlet {

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
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
		
		
		req.setAttribute("WHERE", "Delete");
		
		String no = req.getParameter("no");
		String who =req.getParameter("who");
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		CustomDAO  cdao = new CustomDAO(getServletContext());
		
		boolean result = cdao.isMember(id, pw);
		
		cdao.close();
		
		if(result) {
			MemBoardDAO mdao= new MemBoardDAO(getServletContext());
			MemBoardDTO mdto = mdao.selectOne(no);
			
			int affected = mdao.delete(no);
			
			if(affected!=0) {
				System.out.println("쿼리가 정상적으로 삭제되었습니다. 파일을 삭제합니다.");
				utils.FileUtils.deleteFile(req, "/upload", mdto.getAttachFile());
				/* 요게 페이징 세트임 */
				int nowPage = req.getParameter("nowPage") ==null ? 1 : Integer.parseInt(req.getParameter("nowPage"));
				String searchKind =req.getParameter("searchKind")==null ? "" : "searchKind="+req.getParameter("searchKind");
				String searchKeyword = req.getParameter("searchKeyword")==null ? "" : "searchKeyword="+req.getParameter("searchKeyword");
				String query = "";
				if(!searchKind.isEmpty()) {
					query+=searchKind;
				}
				if(!searchKeyword.isEmpty()) {
					query+="&"+searchKeyword+"&";
				}
				req.setAttribute("query", query);
				req.setAttribute("nowPage", nowPage);
				
				req.setAttribute("result_code", CodeImpl.RES_MEM_DELETE_SUCC);
			}else {
				req.setAttribute("result_code", CodeImpl.RES_MEM_DELETE_FAILED);
				req.setAttribute("failMsg", "삭제에 실패하였습니다.");
			}
			
			
		}else {

			req.setAttribute("failMsg", "비밀번호가 일치하지 않습니다.");
			req.setAttribute("result_code", CodeImpl.RES_MEM_DELETE_FAILED);
			
		}
		
		
		req.getRequestDispatcher("/membershipboard/MB_Divider.jsp").forward(req, resp);
		
		
		
	}
}
