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
import model.MyDAO;

public class MypageController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		
		//확인용
		System.out.println("[-- Controller Confirm --]");
		System.out.println("Current Controller : "+this.getClass().getName());
		System.out.println("Method" + req.getMethod());
		System.out.println("URL FROM : " + req.getHeader("referer"));
		System.out.println("Session Checker : " + req.getSession().getAttribute("user"));
		
		//세션 확인 코드 
		if(req.getSession().getAttribute("user")==null) {
			req.getSession().invalidate();
			req.getRequestDispatcher("/login/LoginDivider.jsp").forward(req, resp);
		}
		
		int request_code = req.getParameter("request_code")==null? 0 : Integer.parseInt(req.getParameter("request_code"));

		
		CustomDAO dao = new CustomDAO(getServletContext());
		
		Map map = new HashMap();
		CustomDTO dto;
		if(request_code == CodeImpl.REQ_MEM_LIST_YOURPAGE && Integer.parseInt( req.getParameter("others"))!=((CustomDTO)req.getSession().getAttribute("user")).getPid()) {
			map.put("pid",req.getParameter("others"));
			dto =dao.selectOne(map);
			req.setAttribute("others", "타 회원");
		}else {
			map.put("pid",((CustomDTO)req.getSession().getAttribute("user")).getPid());
			System.out.println("세션부터 확인 ..? " +  ((CustomDTO)req.getSession().getAttribute("user")).getPid());
			dto = dao.selectOne(map);
			
		}
		
		dao.close();
		req.setAttribute("record", dto);
		
		
		
		req.getRequestDispatcher("/login/MyPage.jsp").forward(req, resp);
		
	}
	
}
