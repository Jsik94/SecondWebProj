package controller.custom;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CodeImpl;
import model.CustomDAO;
import model.MemBoardDAO;

public class LoginController extends HttpServlet{
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		//확인용
		System.out.println("[-- Controller Confirm --]");
		System.out.println("Current Controller : "+this.getClass().getName());
		System.out.println("Method" + req.getMethod());
		System.out.println("URL FROM : " + req.getHeader("referer"));
		
		//로그인을 시도할 때 
		
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");

		System.out.println("map data  id  : " + id + " , pw : "+pw);
		
		CustomDAO dao = new CustomDAO(getServletContext());
		Map map = new HashMap();
		map.put("id",id);
		map.put("pw",pw);
		
		Map result = dao.login(map);
		
		
		System.out.println("Result Map : " +result);
		
		req.setAttribute("WHERE", "Login");
		
		if(result ==null) {
			//나중에 메세지 구현하면바꿀예정 divider.jsp를 통해 alert 뿌려주고 이전페이지로 가게할 생각임 
			req.setAttribute("result_code", CodeImpl.RES_LOGIN_FAILED);

		}else {
			req.setAttribute("result_code", CodeImpl.RES_LOGIN_SUCC);
			req.getSession().setAttribute("user", result.get("user"));
			String autosave = req.getParameter("autosave");
			
			System.out.println("autosave : " + autosave);
			if(autosave!=null) {
				Cookie cookie = new Cookie("uid", id);
				cookie.setMaxAge(60*60*24);
				cookie.setPath(req.getContextPath());
				resp.addCookie(cookie);
			}else {
				Cookie cookie = new Cookie("uid", null) ;
			    cookie.setMaxAge(0) ;
				cookie.setPath(req.getContextPath());
			    resp.addCookie(cookie) ;

			}
			
		}
		
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
		
		
		
		
		//Get Case : Page 이동

		req.getRequestDispatcher("/login/Login.jsp").forward(req, resp);
		
		
	}

}
