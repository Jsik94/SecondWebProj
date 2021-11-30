package controller.custom;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import model.CodeImpl;
import model.CustomDAO;
import model.CustomDTO;

public class EditController extends HttpServlet {

	// 확인용
	// 사용하는 계층
	// IN parameter ,session
	// OUT request -> 내정보로 보내자
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
				
		req.setCharacterEncoding("UTF-8");
		
		

		// uid
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		String nickname = req.getParameter("nickname");
		String gender = req.getParameter("gender");
		String habit = null;
		String grade = req.getParameter("grade");
		String comment = req.getParameter("comment");

		if (req.getParameterValues("inter") != null) {
			habit = "";
			for (String inter : req.getParameterValues("inter")) {
				habit += inter + " ";
			}
		}

		CustomDAO dao = new CustomDAO(getServletContext());
		CustomDTO dto = new CustomDTO();
		System.out.println(id);
		dto.setPid(((CustomDTO)req.getSession().getAttribute("user")).getPid());
		dto.setUid(id);
		dto.setPw(pw);
		dto.setNn(nickname);
		dto.setGender(gender);
		dto.setGrade(grade);
		dto.setComment(comment);
		dto.setHabit(habit);
		
		System.out.println(dto.getHabit());
		int affected = dao.update(dto);

		dao.close();

		if (affected != 0) {
			req.setAttribute("result_code", CodeImpl.RES_EDIT_SUCC);
		} else {
			req.setAttribute("result_code", CodeImpl.RES_EDIT_FAILED);
		}

		req.setAttribute("WHERE", "Edit");

		System.out.println("도대체 무슨 코드가 내려가는 거임" + req.getAttribute("result_code"));
		req.getRequestDispatcher("/login/LoginDivider.jsp").forward(req, resp);

	}

	// 확인용
	// 사용하는 계층
	// IN session
	// OUT request
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
		
		
		//세션 확인 코드 
		if(req.getSession().getAttribute("user")==null) {
			req.getSession().invalidate();
			req.getRequestDispatcher("/login/LoginDivider.jsp").forward(req, resp);
		}
		

		int pid = ((CustomDTO) req.getSession().getAttribute("user")).getPid();
		CustomDAO dao = new CustomDAO(getServletContext());

		Map map = new HashMap();
		map.put("pid", pid);

		CustomDTO dto = dao.selectOne(map);
		System.out.println(this.getClass().getName() + "|조회성공");
		req.setAttribute("record", dto);

		req.getRequestDispatcher("/login/Edit.jsp").forward(req, resp);
	}

}
