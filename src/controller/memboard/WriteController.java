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

public class WriteController extends HttpServlet {

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
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
		
		
		
		MultipartRequest mr = utils.FileUtils.upload(req, req.getServletContext().getRealPath("/upload"));
		
		req.setCharacterEncoding("UTF-8");
		int pid = ((CustomDTO)req.getSession().getAttribute("user")).getPid();
		String title = mr.getParameter("title");
		String attachFile = mr.getFilesystemName("attachFile");
		String content = mr.getParameter("content");
		System.out.println("Pid : " +pid);
		System.out.println("title : " +title);
		System.out.println("attachFile : " +attachFile);
		System.out.println("content : " +content);
		
		int affected =0;
		int result_code = 0;
		//logic
		
		if(attachFile!=null) {
			
			System.out.println("저장 경로 : " +  req.getServletContext().getRealPath("/upload"));

			if(mr!=null) {
				result_code= CodeImpl.RES_UPLOAD_SUCC;
			}else {
				result_code= CodeImpl.RES_UPLOAD_FAILED;
			}
			
		}else{
			
			result_code = CodeImpl.RES_UPLOAD_NODATA;
			
		}
		
		//성공적으로 파일을 넣었거나 혹은 파일이 없는 경우에만 SQL 진행
		if(result_code== CodeImpl.RES_UPLOAD_SUCC || result_code==CodeImpl.RES_UPLOAD_NODATA) {
			Map map = new HashMap();
			MemBoardDTO dto = new MemBoardDTO();
			dto.setPid(pid);
			dto.setTitle(title);
			dto.setAttachFile(attachFile);
			dto.setContent(content);
			
			System.out.println("Pid : " +pid);
			System.out.println("title : " +title);
			System.out.println("attachFile : " +attachFile);
			System.out.println("content : " +content);
			map.put("record",dto);
			
			MemBoardDAO dao = new MemBoardDAO(getServletContext());
			affected =dao.insert(map);
			dao.close();
			
			//데이터가 SQL에 입력되지 않았다면 , 삭제
			if(affected !=1) {
				utils.FileUtils.deleteFile(req, "/upload", attachFile);
				result_code= CodeImpl.RES_WRITE_FAILED;
			}else {
				result_code= CodeImpl.RES_WRITE_SUCC;
			}
			
		}else {
			//나머지경우는 파일업로드부터 실패한 경우이므로 삭제
			result_code= CodeImpl.RES_WRITE_FAILED;
		}
		
		
		//packaging
		req.setAttribute("result_code", result_code);
		req.setAttribute("WHERE", "Write");
		System.out.println(this.getClass().getName()+"| result_code : " +result_code);
		req.getRequestDispatcher("/membershipboard/MB_Divider.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//확인용
		System.out.println("[-- Controller Confirm --]");
		System.out.println("Current Controller : "+this.getClass().getName());
		System.out.println("Method" + req.getMethod());
		System.out.println("URL FROM : " + req.getHeader("referer"));
		
		String pid = req.getSession().getAttribute("user").toString();
		//logic
		
		
		//packaging
		req.getRequestDispatcher("/membershipboard/MB_Write.jsp").forward(req, resp);
		
	}
}
