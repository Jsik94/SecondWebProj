package elTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.jsp.PageContext;

//xml에 기재된 함수와 매핑될 실제 함수
//xml에 반드시 class를 기재해줘야함 
//public static으로 정의해야 한다.
//반드시 출력해야하므로 반환갑이 존재해야한다.
public class MyTagLib {

	/*
	 * 반드시 퍼플릭static 선언
	 */

	// [문자열이 숫자 형식이면 true,아니면 false반환]
	public static boolean isNumber(String value) {
		for (int i = 0; i < value.length(); i++) {
			if (!(value.codePointAt(i) >= '0' && value.codePointAt(i) <= '9'))
				return false;
		}
		return true;
	}//////

	public static boolean isContain(String habits, String target) {

		// 비었다면상관없음
		if (!habits.isEmpty()) {
			StringTokenizer stk = new StringTokenizer(habits, " ");
			// 여러개중 하나라도 있다면 OK
			while (stk.hasMoreTokens()) {
				if (stk.nextToken().equals(target)) {
					return true;
				}
			}
		}

		return false;
	}

	public static String convertGrade(String grade_code) {

		String output = "오기입 수정 바람";

		switch (grade_code) {

			case "ele":
				output="초등학생";
				break;
			case "mid":
				output="중학생";
				break;
			case "high":
				output="고등학생";
				break;
			case "uni":
				output="대학생";
				break;
		}

		return output;
	}
	
	public static String convertHabits(String habits) {
		String output = "";
		
		StringTokenizer stk = new StringTokenizer(habits," ");
		while(stk.hasMoreTokens()) {
			
			switch (stk.nextToken()) {
			case "eco":
				output+=" 경제";
				break;
			case "spo":
				output+=" 스포츠";
				
				break;
			case "ent":
				output+=" 연예";
				
				break;
			case "pol":
				output+=" 정치";
				
				break;

			default:
				break;
			}
			
		}
		
		return output;
	}
	

	/*
	 * 주민번호 예]123456-1234567 9 : 1800 ~ 1899년에 태어난 남성 0 : 1800 ~ 1899년에 태어난 여성 1 :
	 * 1900 ~ 1999년에 태어난 남성 2 : 1900 ~ 1999년에 태어난 여성 3 : 2000 ~ 2099년에 태어난 남성 4 :
	 * 2000 ~ 2099년에 태어난 여성 5 : 1900 ~ 1999년에 태어난 외국인 남성 6 : 1900 ~ 1999년에 태어난 외국인
	 * 여성 7 : 2000 ~ 2099년에 태어난 외국인 남성 8 : 2000 ~ 2099년에 태어난 외국인 여성
	 */
	public static String getGender(String ssn) {
		int beginIndex = ssn.indexOf("-") + 1;
		int endIndex = beginIndex + 1;
		String gender = ssn.substring(beginIndex, endIndex);
		switch (Integer.parseInt(gender)) {
		case 1:
		case 3:
		case 9:
			return "남성";
		case 0:
		case 2:
		case 4:
			return "여성";
		case 5:
		case 7:
			return "외국인 남성";
		case 6:
		case 8:
			return "외국인 여성";
		default:
			return "유효하지 않은 주민번호입니다";

		}
	}/////////////

//	public static String isMemeber(String id,String pw,PageContext page) {
//		
//		BbsDAO dao = new BbsDAO(page.getServletContext());
//		//리턴을 안하는 이유 close 해줘야함
////		return dao.isMember(id, pw) ? (id+"님 반갑습니다<br/>") : "로그인후 이용하세요<br/>";
//		
//		if(dao.isMember(id, pw)) {
//			dao.close();
//			return id+"님 반갑습니다<br/>";
//		}
//		dao.close();
//		return "로그인후 이용하세요<br/>";
//		
//	}

}
