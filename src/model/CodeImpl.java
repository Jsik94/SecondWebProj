package model;

public interface CodeImpl {
	
	
	/*
	 * 		Code Type
	 * 		일의 자리숫자가 code
	 * 		나머지가 타입들임
	 * 		따라서 10가지 표현가능
	 * 		0 ~ 3 -> 요청 결과 코드로 사용
	 * 
	 * 		1 -> 성공
	 * 		2 -> 실패
	 * 		 
	 * 		4 ~ 6 -> 각 타입에서 내부적으로 처리할 코드
	 */
	
	
	
	//잘못된 임의의 접근
	int RES_WRONG_ACCESS = 9;
	

	
	//About Login

	int RES_LOGIN_SUCC =21;
	int RES_LOGIN_FAILED =22;	
	
	//About SignUp
	
	int RES_SIGNUP_SUCC=31;
	int RES_SIGNUP_FAILED=32;
	
	//about Edit
	
	int RES_EDIT_SUCC=41;
	int RES_EDIT_FAILED=42;
	
	
	
	//Memboard View
	int RES_MEM_VIEW_FAILED=62;
	
	
	//About Upload
	
	int RES_UPLOAD_SUCC = 71;
	int RES_UPLOAD_FAILED=72;
	int RES_UPLOAD_NODATA =73;
	
	
	//MemBoard Write
	int RES_WRITE_SUCC= 81;
	int RES_WRITE_FAILED= 82;
	
	
	//MemBoard Edit
	int RES_MEM_EDIT_SUCC = 91;
	int RES_MEM_EDIT_FAILED = 92;
	int RES_MEM_EDIT_EXCEED = 93;
	
	
	int MEM_EDIT_CASE_UPDATENO=94;
	int MEM_EDIT_CASE_DELETEPREV=95;
	int MEM_EDIT_CASE_UPDATEALL=96;
	
	//MemBoard List
	int REQ_MEM_LIST_YOURPAGE=104; //플래티넘 이상급 회원에서 적용
	
	//MemBoard Delete
	int RES_MEM_DELETE_SUCC=111;
	int RES_MEM_DELETE_FAILED=112;
	
	
}
