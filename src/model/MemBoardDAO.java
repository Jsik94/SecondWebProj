package model;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

public class MemBoardDAO extends MyDAO {

	public MemBoardDAO(ServletContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// 게시판 전용으로 쓰기 위한 클래스
	// 페이징을 위한 로직, 목록을 위한 로직 등 게시판에 쓸꺼 싹다여기로
	/*
	 * 1. MemBoardDTO selectOne no -> 상세보기로 가기위한 로직
	 * 
	 * 2. beta -> 1.0 에선 start end 넘겨 줘야함 지금은 일단 싹다 긁으셈 List<MemBoardDTO> selectList
	 * map -> map에는 시작과 끝 가져오기 -> 구간쿼리를 이용해서 다뽑아와야함 -> 게시판 리스트를 위한 함수 -> 1.1에서는 태그로
	 * 검색했을때의 결과도 나와야함
	 * 
	 * 3.
	 * 
	 * int update MemBoardDTO -> 상세보기에서 수정할 때 필요한 과정
	 * 
	 * 4. int delete no -> 상세보기에서 삭제 -> 1.1에선 관리자가 리스트에서도 삭제함 ->
	 * 
	 * 5.
	 * 
	 * 
	 * 
	 * 
	 */

	// 1.상세보기로 가기위한 로직

	public MemBoardDTO selectOne(String no) {

		MemBoardDTO dto = null;

		String sql = "Select m.*,cus_nn FROM mem_board m JOIN custom c ON m.cus_pid = c.cus_pid WHERE mb_no = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, no);
			rs = psmt.executeQuery();

			if (rs.next()) {
				dto = new MemBoardDTO();
				dto.setNo(rs.getInt(1));
				dto.setTitle(rs.getString(2));
				dto.setPid(rs.getInt(3));
				dto.setContent(rs.getString(4));
				dto.setPostDate(rs.getDate(5));
				dto.setViewCount(rs.getInt(6));
				dto.setLike(rs.getInt(7));
				dto.setAttachFile(rs.getString(8));
				dto.setDownCount(rs.getInt(9));
				dto.setNickname(rs.getString(10));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dto;
	}

	// 2. 리스트에 뿌려줄 로직 현재 : beta (구간쿼리 X , 검색 기반 X)
	public List<MemBoardDTO> select(Map map) {
		List<MemBoardDTO> records = new Vector();

		// String sql = "SELECT m.*,c.cus_nn FROM mem_board m JOIN custom c ON m.cus_pid
		// = c.cus_pid ORDER BY mb_no desc";

		int start = (int) map.get("start");
		int end = (int) map.get("end");

		// 구간쿼리
		String sql = "SELECT * FROM(SELECT ROWNUM R,T.*FROM(SELECT * FROM mem_board M JOIN custom C ON M.cus_pid = C.cus_pid ";

		if (map.get("searchKind") != null) {

			String condition = "WHERE ";
			if (map.get("searchKind").toString().equals("title")) {
				condition += " mb_title LIKE '%" + map.get("searchKeyword").toString() + "%' ";
			} else if (map.get("searchKind").toString().equals("content")) {
				condition += " mb_content LIKE '%" + map.get("searchKeyword").toString() + "%' ";
			} else {
				condition += " mb_title LIKE '%" + map.get("searchKeyword").toString() + "%' OR mb_content LIKE '%"
						+ map.get("searchKeyword").toString() + "%' ";
			}

			sql += condition;
		}

		sql += "ORDER BY mb_no DESC) T ) K WHERE R BETWEEN ? and ?";

		System.out.println(this.getClass().getName() + "|SELECT|QUERY :" + sql);
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, start);
			psmt.setInt(2, end);
			rs = psmt.executeQuery();
			while (rs.next()) {
				MemBoardDTO dto = new MemBoardDTO();
				dto.setNo(rs.getInt(2));
				dto.setTitle(rs.getString(3));
				dto.setPid(rs.getInt(4));
				dto.setContent(rs.getString(5));
				dto.setPostDate(rs.getDate(6));
				dto.setViewCount(rs.getInt(7));
				dto.setLike(rs.getInt(8));
				dto.setAttachFile(rs.getString(9));
				dto.setDownCount(rs.getInt(10));
				dto.setNickname(rs.getString(14));
				records.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return records;
	}

	public int getTotalRecordCount(Map map) {
		int totalRecordCount = 0;
		System.out.println(this.getClass().getName() + "|getTotalRecordCount|Start");
		String sql = "SELECT count(*) FROM mem_board ";

		if (map.get("searchKind") != null) {

			String condition = "WHERE";
			if (map.get("searchKind").toString().equals("title")) {
				condition += " mb_title LIKE '%" + map.get("searchKeyword").toString() + "%' ";
			} else if (map.get("searchKind").toString().equals("content")) {
				condition += " mb_content LIKE '%" + map.get("searchKeyword").toString() + "%' ";
			} else {
				condition += " mb_title LIKE '%" + map.get("searchKeyword").toString() + "%' OR mb_content LIKE '%"
						+ map.get("searchKeyword").toString() + "%' ";
			}

			sql += condition;
		}

		System.out.println(this.getClass().getName() + "|getTotalRecordCount|QUERY :" + sql);
		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();

			rs.next();

			totalRecordCount = rs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(this.getClass().getName() + "|getTotalRecordCount|End");
		return totalRecordCount;
	}

	public int insert(Map map) {
		int affected = 0;
		MemBoardDTO dto = (MemBoardDTO) map.get("record");

		String sql = "INSERT INTO MEM_BOARD VALUES(SEQ_MEM_BOARD_MB_NO.NEXTVAL,?,?,?,SYSDATE,DEFAULT,DEFAULT,?,DEFAULT)";

		try {

			psmt = con.prepareStatement(sql);
			psmt.setString(1, dto.getTitle());
			psmt.setInt(2, dto.getPid());
			psmt.setString(3, dto.getContent());
			if (dto.getAttachFile() != null) {
				psmt.setString(4, dto.getAttachFile());
			} else {
				psmt.setNull(4, Types.NVARCHAR);
			}

			affected = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return affected;
	}

	public int update(Map map) {
		int affected = 0;

		MemBoardDTO dto = (MemBoardDTO) map.get("record");
		int whichCase = (int) map.get("case");

		String sql_basic = "UPDATE MEM_BOARD SET mb_title = ? , mb_content = ?";
		if (whichCase != CodeImpl.MEM_EDIT_CASE_UPDATENO) {
			sql_basic += ",mb_attachfile = ? ,mb_downcount = DEFAULT ";
		}
		String sql_suffix = " WHERE mb_no = " + dto.getNo();

		// String sql = "UPDATE MEM_BOARD SET mb_title = ? , mb_content = ?";
		String sql = sql_basic + sql_suffix;

		System.out.println("UPDATE SQL : " + sql);
		try {

			psmt = con.prepareStatement(sql);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			if (whichCase != CodeImpl.MEM_EDIT_CASE_UPDATENO) {

				if (whichCase == CodeImpl.MEM_EDIT_CASE_DELETEPREV) {
					// 예전것만 삭제하는 경우이므로 NULL로 초기화해야함
					psmt.setNull(3, Types.NVARCHAR);
				} else {
					// 새걸로 바꾸는 케이스 이므로
					psmt.setString(3, dto.getAttachFile());

				}

			}

			affected = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return affected;
	}

	public int delete(String no) {
		int affected = 0;
		String sql = "DELETE MEM_BOARD WHERE mb_no = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, no);

			affected = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return affected;
	}

	public void updateDownCount(String no) {
		System.out.println(this.getClass().toString() + " | DAO | updateDownCount | START");

		String sql = "UPDATE mem_board SET mb_downcount = mb_downcount+1 WHERE mb_no = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, no);
			psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(this.getClass().toString() + " | DAO | updateDownCount | FAILED");

		}
		System.out.println(this.getClass().toString() + " | DAO | updateDownCount | SUCCESS");

	}

	public void updateViewCount(String no) {
		System.out.println(this.getClass().toString() + " | DAO | updateViewCount | START");

		String sql = "UPDATE mem_board SET mb_viewcount = mb_viewcount+1 WHERE mb_no = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, no);
			psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(this.getClass().toString() + " | DAO | updateViewCount | FAILED");

		}
		System.out.println(this.getClass().toString() + " | DAO | updateViewCount | SUCCESS");

	}

	public boolean checkLike(String pid, String no) {
		boolean result = false;

		String sql = "SELECT count(*) FROM LIKE_INFO WHERE cus_pid = ? AND mb_no = ?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pid);
			psmt.setString(2, no);
			rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt(1) == 1) {
				return true;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}

	public int updateLikeCount(String pid, String no, boolean delete) {
		int affected = 0;
		System.out.println(this.getClass().toString() + " | DAO | updateLikeCount | START");
		/*
		 * CAUTION 트랙잭션임
		 * 
		 * 1)delete 가 true인 경우 -> 이미 예전에 따봉을 한번 박아서 취소해야하는 경우임 ->이경우 delete 한번수행 ->no를
		 * 기준으로 Mem_board 업데이트한번 수행
		 * 
		 * 2)deleted 가 false인 경우 -> 이제 따봉을 박아야함 ->이경우 insert 한번 수행 ->no를 기준으로 mem_board
		 * 업데이트 한번 수행
		 * 
		 * 
		 */

		String sql = "";

		if (delete) {
			sql = "DELETE LIKE_INFO WHERE mb_no = ? AND cus_pid = ? ";
		} else {
			sql = "INSERT INTO LIKE_INFO VALUES(SEQ_LIKE_INFO_LIKE_ID.NEXTVAL,? ,? ,SYSDATE)";
		}

		try {
			con.setAutoCommit(false);
			psmt = con.prepareStatement(sql);
			psmt.setString(1, no);
			psmt.setString(2, pid);
			affected = psmt.executeUpdate();
			System.out.println("1) 결과 : " + affected);
			if (affected == 0) {
				con.rollback();
				return affected;
			}

			sql = "UPDATE MEM_BOARD SET MB_LIKE = ( SELECT count(*) FROM LIKE_INFO WHERE MB_NO = ?) WHERE MB_NO =?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, no);
			psmt.setString(2, no);
			affected = psmt.executeUpdate();

			System.out.println("2) 결과 : " + affected);
			if (affected == 0) {
				con.rollback();
				return affected;
			}

			con.commit();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return affected;

	}

	public Map<String, Integer> prevNext(String no, Map map) {

		Map<String, Integer> result = new HashMap<>();

		System.out.println(this.getClass().toString() + " | DAO | prevNext | START");

		try {
			String sql = "SELECT mb_no FROM mem_board WHERE mb_no = (SELECT MIN(mb_no) FROM mem_board WHERE mb_no > ? ";
			String condition = " AND";
			if (map.get("searchKind") != null) {

				
				if (map.get("searchKind").toString().equals("title")) {
					condition += " mb_title LIKE '%" + map.get("searchKeyword").toString() + "%' ";
				} else if (map.get("searchKind").toString().equals("content")) {
					condition += " mb_content LIKE '%" + map.get("searchKeyword").toString() + "%' ";
				} else {
					condition += " mb_title LIKE '%" + map.get("searchKeyword").toString()
							+ "%' OR mb_content LIKE '%" + map.get("searchKeyword").toString() + "%' ";
				}
				sql+=condition+") "+condition;
			}else {
				sql+=")";
			}
			System.out.println(this.getClass().toString() + " | DAO | prev | SQL : " + sql);

			psmt = con.prepareStatement(sql);
			psmt.setString(1, no);
			rs = psmt.executeQuery();

			if (rs.next()) {

				result.put("Prev", rs.getInt(1));

			}
			sql = "SELECT mb_no FROM mem_board WHERE mb_no = (SELECT MAX(mb_no) FROM mem_board WHERE mb_no < ? ";

			if (map.get("searchKind") != null) {
				
				sql+=condition+") "+condition;
			}else {
				sql+=")";
			}
			System.out.println(this.getClass().toString() + " | DAO | Next | SQL : " + sql);
			psmt = con.prepareStatement(sql);
			psmt.setString(1, no);
			rs = psmt.executeQuery();

			if (rs.next()) {


				result.put("Next", rs.getInt(1));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

}
