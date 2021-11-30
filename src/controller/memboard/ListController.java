package controller.memboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.deploy.uitoolkit.impl.fx.Utils;

import model.CodeImpl;
import model.MemBoardDAO;
import model.MemBoardDTO;

public class ListController extends HttpServlet {

	// Post 방식
	// 1) Write 이후
	// 2) Delete 이후
	// 3) Search 이후

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
		
		
		//세션 확인 코드 
		if(req.getSession().getAttribute("user")==null) {
			req.getSession().invalidate();
			req.getRequestDispatcher("/login/LoginDivider.jsp").forward(req, resp);
		}
		
		int result_code = (int) req.getAttribute("result_code");
		
		if(result_code == CodeImpl.RES_WRITE_SUCC) {
			
			
			System.out.println("[------------------------------------ 방식 전이 ----------------------------------]");
			doGet(req, resp);
			
			
		}
		

	}

	// GET 방식으로 오는 케이스 :
	// 1) 처음 목록으로 들어올 때
	// 2) View에서 올 때
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		
		
		int nowPage = req.getParameter("nowPage") ==null ? 1 : Integer.parseInt(req.getParameter("nowPage"));
		String searchKind =req.getParameter("searchKind")==null ? "" : req.getParameter("searchKind");
		String searchKeyword = req.getParameter("searchKeyword")==null ? "" :req.getParameter("searchKeyword");
		
		
		// logic
		MemBoardDAO dao = new MemBoardDAO(getServletContext());
		Map map = new HashMap();
		if(!searchKeyword.isEmpty()) {
			map.put("searchKind", searchKind);
		}
		if(!searchKeyword.isEmpty()) {
			map.put("searchKeyword", searchKeyword);
		}
		//페이징 로직 
		
		int pageSize = Integer.parseInt(this.getServletContext().getInitParameter("PAGESIZE"));
		int blockPage = Integer.parseInt(this.getServletContext().getInitParameter("BLOCKPAGE"));
		//Search 조건을 통해 total을 우선적으로 구함
		int totalRowCount = dao.getTotalRecordCount(map);
		int totalPage = (int)Math.ceil((double)totalRowCount/pageSize);
		
		nowPage = nowPage>totalPage ? nowPage-1 ==0 ? 1 : nowPage-1 : nowPage;
		int start= (nowPage-1)*pageSize+1;
		int end = nowPage*pageSize;
		
		
		
		System.out.println("Start : " + start);
		System.out.println("End : " + end);
		map.put("start",start);
		map.put("end",end);

		
		
		List<MemBoardDTO> records = dao.select(map);
		dao.close();
		
		System.out.println("[-------------Paging 관련 ----------------]");
		System.out.println("nowPage :" +nowPage);
		System.out.println("TotalCount : " +totalRowCount);
		System.out.println("pageSize : " +pageSize);
		System.out.println("blockSize : " +blockPage);
		System.out.println("TotalPage : " +totalPage);

		System.out.println("[---------------------------------------]");
		String page= req.getContextPath()+"/memboard/List.jsik?";
		String query = "";
		if(!searchKind.isEmpty()) {
			query+="searchKind="+searchKind;
		}
		if(!searchKeyword.isEmpty()) {
			query+="&"+ "searchKeyword="+searchKeyword+"&";
		}
		page+=query;
		System.out.println("pageUrl : " +page);
		// Packaging
		req.setAttribute("records", records);
		req.setAttribute("query", query);
		req.setAttribute("nowPage", nowPage);
		req.setAttribute("paging", utils.PagingUtil.pagingBootStrapStyle(totalRowCount, pageSize, blockPage, nowPage, page));
		req.getRequestDispatcher("/membershipboard/MB_List.jsp").forward(req, resp);

	}

}
