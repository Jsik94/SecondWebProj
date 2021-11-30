package controller.memboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import model.CodeImpl;
import model.CustomDTO;
import model.MemBoardDAO;
import model.MemBoardDTO;

public class EditController extends HttpServlet {

	// 확인용
	// 사용하는 계층
	// session
	// request

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doPost(req, resp);
		// 확인용
		System.out.println("[-- Controller Confirm --]");
		System.out.println("Current Controller : " + this.getClass().getName());
		System.out.println("Method" + req.getMethod());
		System.out.println("URL FROM : " + req.getHeader("referer"));
		System.out.println("Session Checker : " + req.getSession().getAttribute("user"));
		
		// 세션 확인 코드
		if (req.getSession().getAttribute("user") == null) {
			req.getSession().invalidate();
			req.getRequestDispatcher("/login/LoginDivider.jsp").forward(req, resp);

		}
		req.setAttribute("WHERE", "Edit");
		MultipartRequest mr = utils.FileUtils.upload(req, req.getServletContext().getRealPath("/upload"));

		if (mr == null) {

			req.setAttribute("result_code", CodeImpl.RES_MEM_EDIT_EXCEED);
		} else {


			Map map = new HashMap();
			System.out.println("지금꺼 있음 " + mr.getFilesystemName("attachFile"));
			System.out.println("예전꺼 있음 " + mr.getParameter("prevFile").trim());

			String new_file = mr.getFilesystemName("attachFile");
			String prev_file = mr.getParameter("prevFile").trim().isEmpty() ? null :mr.getParameter("prevFile").trim() ;
			String no = mr.getParameter("no");
			String title = mr.getParameter("title");
			String content = mr.getParameter("content");
			System.out.println(this.getClass().getName()+"|POST|new_file : " + new_file +" prev_file : "+prev_file);
			/*
 			 * 		CAUTION 
			 * 		UPDATE의 경우 크게 3가지로 나눌 수있음	
			 * 	1) 아예 파일을 건들지 않는 케이스	 		attach null	, prev: null
			 * 		updateNo
			 *  2) 기존 파일만 삭제하는 경우  				attach null , prev : 존재
			 *  	deletePrev
			 *  
			 *  3) 새로 파일을 교체하는 경우 				attach 존재 	, prve : 존재 or null 
			 * 		updateAll
			 */
			int whichCase =0;
			
			if(new_file ==null && prev_file ==null) {
				whichCase = CodeImpl.MEM_EDIT_CASE_UPDATENO;
			}else if(new_file==null && prev_file!=null) {
				whichCase = CodeImpl.MEM_EDIT_CASE_DELETEPREV;
			}else if(new_file!=null) {
				whichCase = CodeImpl.MEM_EDIT_CASE_UPDATEALL;
			}else {
				
			}
			
			System.out.println("케이스 구별자 : " +whichCase);
			map.put("case",whichCase);
			
			MemBoardDAO dao = new MemBoardDAO(getServletContext());
			MemBoardDTO dto = new MemBoardDTO();

			dto.setNo(Integer.parseInt(no));
			dto.setTitle(title);
			dto.setContent(content);
			if (prev_file != null) {
				dto.setAttachFile(new_file);
				System.out.println("새로운 파일이 들어왔으므로 기존파일을 삭제예정입니다.");
			}


			map.put("record", dto);
			int affected = dao.update(map);
			dao.close();

			System.out.println("affected : " + affected);

			// SQL문이 성공적으로 변경되었다면
			if (affected != 0) {

				//파일을 변경하지 않는 경우를 제외하고는 모두 이전파일을 제거해야함
				if(whichCase != CodeImpl.MEM_EDIT_CASE_UPDATENO) {
					//이전파일이 있는 경우에 한하여 
					if (prev_file != null) {
						System.out.println("이전 파일이 존재합니다.");
						System.out.println("쿼리가 성공적으로 변경되었으므로 기존파일을 삭제합니다.");
						// 기존파일 삭제
						utils.FileUtils.deleteFile(req, "/upload", prev_file);
					}
					
				}
				
				/* 요게 페이징 세트임 */
				int nowPage = mr.getParameter("nowPage") ==null ? 1 : Integer.parseInt(mr.getParameter("nowPage"));
				String query =mr.getParameter("query")==null ? "" : "searchKind="+mr.getParameter("query");
				
				System.out.println("nowPage : " + nowPage);
				System.out.println("Query : " + query);
				
				req.setAttribute("query", query);
				req.setAttribute("nowPage", nowPage);
				
				req.setAttribute("result_code", CodeImpl.RES_MEM_EDIT_SUCC);
			} else {
				
				//파일을 변경하지 않는 경우 삭제를 생각할 필요가없다
				if(whichCase !=CodeImpl.MEM_EDIT_CASE_UPDATENO) {
					//파일을 변경하는 case에 한해서 
					//새파일이 존재한다면
					if(new_file!=null) {
						System.out.println("쿼리가 실패했으므로 새파일을 삭제합니다.");
						// 현재 올려놓은 파일 삭제
						utils.FileUtils.deleteFile(req, "/upload", new_file);
					}
					
				}
				
				req.setAttribute("result_code", CodeImpl.RES_MEM_EDIT_FAILED);
			}

		}
		
		req.getRequestDispatcher("/membershipboard/MB_Divider.jsp").forward(req, resp);;

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doPost(req, resp);
		// 확인용
		System.out.println("[-- Controller Confirm --]");
		System.out.println("Current Controller : " + this.getClass().getName());
		System.out.println("Method" + req.getMethod());
		System.out.println("URL FROM : " + req.getHeader("referer"));
		System.out.println("Session Checker : " + req.getSession().getAttribute("user"));

		// 세션 확인 코드
		if (req.getSession().getAttribute("user") == null) {
			req.getSession().invalidate();
			req.getRequestDispatcher("/login/LoginDivider.jsp").forward(req, resp);
		}

		String no = req.getParameter("no");

		MemBoardDAO dao = new MemBoardDAO(getServletContext());
		MemBoardDTO dto = dao.selectOne(no);
		dao.close();
		System.out.println("user: " + ((CustomDTO) req.getSession().getAttribute("user")).getPid());
		System.out.println("pid: " + dto.getPid());

		// 세션과 페이지 수정자가 다를 경우 무조건 이전페이지로 돌려버림
		if (((CustomDTO) req.getSession().getAttribute("user")).getPid() != dto.getPid()) {
			req.setAttribute("WHERE", "Edit");
			req.setAttribute("result_code", CodeImpl.RES_WRONG_ACCESS);
			req.getRequestDispatcher("/membershipboard/MB_Divider.jsp").forward(req, resp);
		}
		
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
		req.setAttribute("record", dto);
		req.getRequestDispatcher("/membershipboard/MB_Edit.jsp").forward(req, resp);

	}

}
