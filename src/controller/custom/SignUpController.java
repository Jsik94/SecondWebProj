package controller.custom;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CodeImpl;
import model.CustomDAO;
import model.CustomDTO;

public class SignUpController extends HttpServlet {
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		
		//확인용
		System.out.println("[-- Controller Confirm --]");
		System.out.println("Current Controller : "+this.getClass().getName());
		System.out.println("Method" + req.getMethod());
		System.out.println("URL FROM : " + req.getHeader("referer"));
	
		req.setCharacterEncoding("UTF-8");
		
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		String nickname = req.getParameter("nickname");
		String gender = req.getParameter("gender");
		String habit = null;
		String grade = req.getParameter("grade");
		String comment = req.getParameter("comment");
		
		
		if(req.getParameterValues("inter") !=null) {
			habit = "";
			for(String inter : req.getParameterValues("inter")) {
				habit += inter +" ";
			}
		}
		

		
		CustomDAO dao = new CustomDAO(getServletContext());
		
		boolean result =dao.checkID(id);
		
		//케이스가 두가지로 나뉜다 
		// 1) 회원가입 성공 케이스
		// 2) 회원가입 실패 케이스 (아이디 중복 )
		
		if(result) {
			CustomDTO dto = new CustomDTO();
			dto.setUid(id);
			dto.setPw(pw);
			dto.setNn(nickname);
			dto.setGender(gender);
			dto.setGrade(grade);
			dto.setComment(comment);
			dto.setHabit(habit);
			int affected = dao.insert(dto);

			dao.close();
			
			if(affected !=0) {
				req.setAttribute("result_code", CodeImpl.RES_SIGNUP_SUCC);
			}else {
				req.setAttribute("result_code", CodeImpl.RES_SIGNUP_FAILED);
			}
		}else {
			req.setAttribute("result_code", CodeImpl.RES_SIGNUP_FAILED);
			//메세지로 갔다가 알람 띄우고 historyback
			
		}
		
		req.setAttribute("WHERE", "SignUp");
		
		System.out.println("도대체 무슨 코드가 내려가는 거임" + req.getAttribute("result_code"));
		req.getRequestDispatcher("/login/LoginDivider.jsp").forward(req, resp);
		
		
		
	}
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		
		//확인용
		System.out.println("[-- Controller Confirm --]");
		System.out.println("Current Controller : "+this.getClass().getName());
		System.out.println("Method" + req.getMethod());
		System.out.println("URL FROM : " + req.getHeader("referer"));
	
		
		req.getRequestDispatcher("/login/SignUp.jsp").forward(req, resp);
	}
	
	
	
}
