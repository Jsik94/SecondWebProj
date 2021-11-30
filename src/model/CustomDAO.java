package model;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;

public class CustomDAO extends MyDAO {

	String custom_seq;
	String personal_seq;

	public CustomDAO(ServletContext context) {
		super(context);
		// TODO Auto-generated constructor stub
		custom_seq = (String) context.getInitParameter("CUSTOM_SEQ") + ".NEXTVAL";
		personal_seq = (String) context.getInitParameter("PERSONAL_SEQ") + ".NEXTVAL";
	}

	// 회원정보 등록 조회 수정 삭제에 관한 Class
	// 로그인, 내정보보기, 내정보 수정하기, 할 때써야함
	// 즉 해당 DAO의 기능은 아래와같음
	/*
	 * int insert(SOMEDTO) : 회원가입 데이터
	 * 
	 * SOMEDTO selectOne (id값 or PID값으로) : 한사람의 정보를 조회할때
	 * 
	 * boolean login(DTO or id,pw) : 아이디와 비번이 일치하는 사람
	 */

	// 회원가입시 필요한 정보기입
	public int insert(CustomDTO dto) {

		/*
		 * CUATION !!!! 다음은 하나의 트랜잭션이므로 autoCommit을 끄고진행한다.
		 * 
		 */
		int affected = 0;

		String sql = String.format("INSERT INTO custom VALUES (%s,?,?,?,?,?,?,SYSDATE,1)", custom_seq);

		try {

			con.setAutoCommit(false);
			psmt = con.prepareStatement(sql,new String[] {"CUS_PID"});
			psmt.setString(1, dto.getUid());
			psmt.setString(2, dto.getPw());
			psmt.setString(3, dto.getNn());
			psmt.setString(4, dto.getGender());
			psmt.setString(5, dto.getGrade());
			psmt.setString(6, dto.getComment());

			affected = psmt.executeUpdate();
			System.out.println("affected : " +affected);
			// 취향이 입력되었고, 위 쿼리가 제대로 실행되었을 때
			// 개인취향을 테이블에넣어줌
			if (dto.getHabit() != null && affected != 0) {
				// 개인 취향이 존재할 경우를 의미함
				rs = psmt.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
				int cus_pid = 0;
				if (rs.next()) {
					System.out.println(rs.getString(1));
					cus_pid = rs.getInt(1); // pid 긁어옴

					// 확인용
					System.out.println("autoIncrement: " + cus_pid);
				}
				System.out.println("habits : " + dto.getHabit());
				StringTokenizer stk = new StringTokenizer(dto.getHabit(), " ");

				while (stk.hasMoreTokens()) {
					
					System.out.println(personal_seq);
					sql = String.format("INSERT INTO personal VALUES(%s,?,?)", personal_seq);
					psmt = con.prepareStatement(sql);
					psmt.setString(1, stk.nextToken());
					psmt.setInt(2, cus_pid);
					affected = psmt.executeUpdate();

					// 하나라도 안들어가면 롤백
					if (affected == 0) {
						con.rollback();
						return 0;
					}

				}

			}

			// 완벽히 다들어감
			con.commit();
		} catch (SQLException e) {

			try {
				e.printStackTrace();
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return affected;
	}

	public CustomDTO selectOne(Map map) {
		CustomDTO dto = null;

		String condition = " WHERE ";
		String target;
		// uid가 있을때를 대비한 method
		if (map.get("pid") != null) {
			condition += " cus_pid = ?";
			target = map.get("pid").toString();
		} else {
			condition += " cus_uid = ?";
			target = map.get("uid").toString();
		}

		String sql = "SELECT * FROM custom " + condition;

		System.out.println("SQL : " + sql);

		System.out.println("target  : " + target);

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, target);
			rs = psmt.executeQuery();

			if (rs.next()) {
				dto = new CustomDTO();
				dto.setPid(rs.getInt(1));
				dto.setUid(rs.getString(2));
				dto.setPw(rs.getString(3));
				dto.setNn(rs.getString(4));
				dto.setGender(rs.getString(5));
				dto.setGrade(rs.getString(6));
				dto.setComment(rs.getString(7).replace("\r\n", "<br/>"));
				dto.setSignDate(rs.getDate(8));
				dto.setAuth_code(rs.getInt(9));
				
			}
			
			sql = "SELECT per_hab FROM PERSONAL WHERE cus_pid = ?";
			psmt= con.prepareStatement(sql);
			psmt.setInt(1, dto.getPid());
			rs = psmt.executeQuery();
			String habit ="";
			while(rs.next()) {
				habit +=rs.getString(1)+" ";
			}
			System.out.println("Habit val : " + habit);
			
			if(!habit.isEmpty()) {
				dto.setHabit(habit);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dto;
	}

	// 있다면 PID 반환
	public Map login(Map map) {

		System.out.println("DAO : login method ");
		Map result = null;
		String id = (String) map.get("id");
		String pw = (String) map.get("pw");

		System.out.println("map data  id  : " + id + " , pw : " + pw);
		String sql = "SELECT cus_pw,cus_pid,cus_nn,auth_id FROM CUSTOM WHERE cus_uid = ? ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();

			System.out.println("Executed Query");

			if (!rs.next()) {
				System.out.println("CUS_LOGIN_RESULT : FAILED ");

			} else {
				if (rs.getString(1).equals(pw)) {
					CustomDTO dto = new CustomDTO();
					dto.setPid(rs.getInt(2));
					dto.setNn(rs.getString(3));
					dto.setAuth_code(rs.getInt(4));
					result = new HashMap();
					result.put("user", dto);

					System.out.println("CUS_LOGIN_RESULT : SUC ");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("CUS_LOGIN_RESULT : FAIL ");

			return result;
		}

		return result;
	}

	public boolean checkID(String id) {
		boolean result = false;
		String sql = "SELECT count(*) FROM custom WHERE cus_uid = ?";
		System.out.println("SQL : " + sql);

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			rs.next();
			
			if(rs.getInt(1)==0) {
				result = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}
	
	public boolean isMember(String pid,String pw) {
		boolean result =false;
		
		String sql = "SELECT cus_pw FROM custom WHERE cus_pid = ?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pid);
			rs =psmt.executeQuery();
			rs.next();
			if(rs.getString(1).equals(pw)) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	

	public int update(CustomDTO dto) {
		System.out.println(this.getClass().getName()+" | CUSTOMDAO | update | start");
		/*
		 * CUATION !!!! 다음은 하나의 트랜잭션이므로 autoCommit을 끄고진행한다.
		 * 
		 */
		
		int affected = 0;

		String sql = "UPDATE CUSTOM SET ";
		sql+= " cus_nn =? , cus_gen = ? , cus_grade = ? , cus_comment= ? ";
		if(dto.getPw()!=null) {
			sql+=",cus_pw = ?" ;
		}
		
		sql+= "WHERE cus_uid ='"+dto.getUid()+"'";
		
		System.out.println("Completed SQL : "+sql);
		try {

			con.setAutoCommit(false);
			psmt = con.prepareStatement(sql);
			psmt.setString(1, dto.getNn());
			psmt.setString(2, dto.getGender());
			psmt.setString(3, dto.getGrade());
			psmt.setString(4, dto.getComment());

			if(dto.getPw()!=null) {
				psmt.setString(5, dto.getPw());
				
			}
			
			affected = psmt.executeUpdate();
			System.out.println(this.getClass().getName()+" | CUSTOMDAO | update | First Query : " + affected);
			//위에 쿼리만 실행된다면 무조건 아래를 돌아야한다. 이전데이터삭제해야하므로
			if (affected != 0) {

				int cus_pid = dto.getPid();
				
				System.out.println("Pid check : " + cus_pid);
				//싹다지우고
				sql = "DELETE personal WHERE cus_pid = ?";
				psmt = con.prepareStatement(sql);
				psmt.setInt(1, cus_pid);
				psmt.executeUpdate();
				System.out.println(this.getClass().getName()+" | CUSTOMDAO | update | Second Query");
				
				//다시추가 
				if(dto.getHabit()!=null) {
					System.out.println("habits : " + dto.getHabit());
					StringTokenizer stk = new StringTokenizer(dto.getHabit(), " ");

					while (stk.hasMoreTokens()) {
						
						System.out.println(personal_seq);
						sql = String.format("INSERT INTO personal VALUES(%s,?,?)", personal_seq);
						psmt = con.prepareStatement(sql);
						psmt.setString(1, stk.nextToken());
						psmt.setInt(2, cus_pid);
						affected = psmt.executeUpdate();

						// 하나라도 안들어가면 롤백
						if (affected == 0) {
							con.rollback();
							return 0;
						}

					}
					
					System.out.println(this.getClass().getName()+" | CUSTOMDAO | update | Third Query");
				}
				

			}

			// 완벽히 다들어감
			con.commit();
		} catch (SQLException e) {

			try {
				e.printStackTrace();
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block

				System.out.println(this.getClass().getName()+" | CUSTOMDAO | update | faield : " + affected);
				e1.printStackTrace();
				return 0 ;
			}

			System.out.println(this.getClass().getName()+" | CUSTOMDAO | update | faield : " + affected);
			return 0;
		}
		System.out.println(this.getClass().getName()+" | CUSTOMDAO | update | success : " + affected);
		return affected;
	}
	
	
	
	

}
