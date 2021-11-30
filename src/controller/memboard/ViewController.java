package controller.memboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CodeImpl;
import model.ComBoardDAO;
import model.ComBoardDTO;
import model.CustomDTO;
import model.MemBoardDAO;
import model.MemBoardDTO;

public class ViewController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doPost(req, resp);

		req.setCharacterEncoding("UTF-8");
		String content_cm = req.getParameter("content_cm");
		System.out.println("content_cm" + content_cm);
		// 확인용
		doGet(req, resp);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doGet(req, resp);
		// 확인용
		System.out.println("[-- Controller Confirm --]");
		System.out.println("Current Controller : " + this.getClass().getName());
		System.out.println("Method" + req.getMethod());
		System.out.println("URL FROM : " + req.getHeader("referer"));
		System.out.println("Session Checker : " + req.getSession().getAttribute("user"));

		req.setCharacterEncoding("UTF-8");
		// 세션 확인 코드
		if (req.getSession().getAttribute("user") == null) {
			req.getSession().invalidate();
			req.getRequestDispatcher("/login/LoginDivider.jsp").forward(req, resp);
		}
		// <---------------------------------- 초기 확인용
		// -------------------------------------------------->

		String like = req.getParameter("like");
		String no = req.getParameter("no");
		System.out.println("[NO] : " + no);
		// 페이징 관련 변수
		int nowPage = req.getParameter("nowPage") == null ? 1 : Integer.parseInt(req.getParameter("nowPage"));
		String searchKind = req.getParameter("searchKind") == null ? "" : req.getParameter("searchKind");
		String searchKeyword = req.getParameter("searchKeyword") == null ? "" : req.getParameter("searchKeyword");
		String query = "";

		String pid = Integer.toString(((CustomDTO) req.getSession().getAttribute("user")).getPid());

		// 다음글 이전글 이동으로 페이지가없을때
		if (no == null) {

			req.setAttribute("WHERE", "View");
			req.setAttribute("result_code", CodeImpl.RES_MEM_VIEW_FAILED);
			req.getRequestDispatcher("/membershipboard/MB_Divider.jsp").forward(req, resp);
		}

		// 이전글 다음글 긁기위한 로직
		Map map = new HashMap();

		if (!searchKind.isEmpty()) {
			query += "searchKind=" + searchKind;
			query += "&" + "searchKeyword=" + searchKeyword + "&";
			map.put("searchKind", searchKind);
			map.put("searchKeyword", searchKeyword);

		}

		req.setAttribute("query", query);
		req.setAttribute("nowPage", nowPage);

		// <-------------------------Model 사용 구간
		// -----------------------------------------------

		MemBoardDAO dao = new MemBoardDAO(getServletContext());
		Map<String, Integer> res = dao.prevNext(no, map);
		req.setAttribute("PrevNext", res);
		System.out.println("PREVNEXT : " + res.get("Prev") + " | " + res.get("Next"));

		dao.updateViewCount(no);
		// 누른적 있는지 없는지 확인하기 위함임
		boolean setLike = dao.checkLike(pid, no);

		// 좋아요 로직
		if (like != null) {
			// 누른적이 없다면 업데이틀 할 필요가 없음
			boolean delete = false;

			if (like.equals("true")) {
				System.out.println("[LIKE] 값이 들어왔습니다.TRUE LIKEINFO에 데이터를 추가합니다.");
				delete = false;
			} else {
				System.out.println("[LIKE] 값이 들어왔습니다.FALSE LIKEINFO에 데이터를 삭제합니다.");
				delete = true;
			}

			dao.updateLikeCount(pid, no, delete);
		}

		MemBoardDTO dto = dao.selectOne(no);
		dto.setContent(dto.getContent().replace("\r\n", "<br/>"));
		dao.close();

		// ----------------------------------댓글
		// 로직-----------------------------------------------
		ComBoardDAO cdao = new ComBoardDAO(getServletContext());
		String cm_command = req.getParameter("cm_command");

		if (cm_command != null) {

			if (cm_command.equals("insert")) {
				// 입력로직
				int affected=0;
				String cm_content = req.getParameter("cm_content");
				System.out.println("content_cm : " + cm_content);

				ComBoardDTO cdto = new ComBoardDTO();
				cdto.setMb_no(Integer.parseInt(no));
				cdto.setCus_pid(Integer.parseInt(pid));
				cdto.setCom_content(cm_content);
				affected= cdao.insert(cdto);

			} else {
				// 삭제로직

				int affected=0;
				String cm_no = req.getParameter("cm_no");
				System.out.println(cm_no);
				affected = cdao.delete(cm_no);
				
			}

		}

		
		//댓글 조회 로직
		List<ComBoardDTO> list = cdao.select(no);

		System.out.println("record_cm :" + list);
		System.out.println("record_cm size : " + list.size());
		req.setAttribute("record_cm", list);
		req.setAttribute("record", dto);

		cdao.close();
		if (like != null) {

			req.setAttribute("like", like);
		} else {

			req.setAttribute("like", setLike);
		}

		req.getRequestDispatcher("/membershipboard/MB_View.jsp").forward(req, resp);

	}

}
