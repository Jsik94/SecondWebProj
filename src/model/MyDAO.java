package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

public class MyDAO{
	
	protected Connection con;
	protected PreparedStatement psmt;
	protected ResultSet rs ;
	
	public MyDAO(ServletContext context) {
		

		System.out.println(this.getClass().toString() + " | DAO | contructor | start");
		Context ctx;
		try {
			
			System.out.println("Context init param : " +  (String)context.getInitParameter("JDNI_ROOT"));
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup((String)context.getInitParameter("JDNI_ROOT"));
			con = ds.getConnection();

			System.out.println(this.getClass().toString() + " | DAO | contructor | connection success");
		} catch (NamingException  | SQLException e ) {
			// TODO Auto-generated catch block
				
			System.out.println(this.getClass().toString() + " | DAO | contructor | failed");
			e.printStackTrace();
		} 
		
		
	}
	
	public void close() {
		try {
			if(rs !=null) {
				rs.close();
			}
			
			if(psmt !=null) {
				psmt.close();
			}
			
			if(con !=null) {
				//이제는 끊기는 것이아닌 connection pool에 반납임
				con.close();
			}
			
		}catch(SQLException e) {
			
		}
		
	}
	

	
	
	/*
	  		생각을 해봤을 때, 만들어야할 DAO 객체
	  		
	  		insert Custom Data
	  		update Custom Data
	  		delete Custom Data
	  		
	  		select Custom Data 1명 or 여러명(Like 회원정보록) 
	  		
	  		
	  		insert NB Data
	  		update NB Data
	  		delete NB Data
	  		select NB Data
	  		
	 
	 */
	
	
	
	
	
//	
//	public List<DataRoomDTO> selectList(Map map){
//		
//		List<DataRoomDTO> records = new Vector();
//		//페이지 적용적
//		//String sql = "SELECT * FROM DATAROOM ORDER BY no DESC";
//		
//		
//		String sql = "SELECT * FROM (SELECT T.*, ROWNUM R FROM(SELECT * FROM DATAROOM ORDER BY no DESC) T) WHERE R BETWEEN ? AND ?";
//		
//		
//		try {
//			psmt = con.prepareStatement(sql);
//			psmt.setString(1,map.get("start").toString());
//			psmt.setString(2,map.get("end").toString());
//			
//			rs = psmt.executeQuery();
//			
//			
//			while(rs.next()) {
//				DataRoomDTO dto = new DataRoomDTO();
//				
//				dto.setNo(rs.getInt(1));
//				dto.setName(rs.getString(2));
//				dto.setPassword(rs.getString(3));
//				dto.setTitle(rs.getString(4));
//				dto.setContent(rs.getString(5));
//				dto.setAttachfile(rs.getString(6));
//				dto.setDowncount(rs.getString(7));
//				dto.setPostdate(rs.getDate(8));
//				records.add(dto);
//			}
//			
//			
//		}catch(SQLException e) {
//			e.printStackTrace();
//			
//		}
//		
//		
//		
//		
//		return records;
//	}
//	
//	
//	public int getTotalRowCount() {
//		int totalCount =0;
//		
//		String sql = "SELECT COUNT(*) FROM dataroom";
//		
//		try {
//			psmt = con.prepareStatement(sql);
//			
//			rs = psmt.executeQuery();
//			rs.next();
//			totalCount = rs.getInt(1);
//			
//			
//		}catch(SQLException e) {
//			
//			
//			e.printStackTrace();
//		}
//		
//		
//		return totalCount ;
//		
//	}
//	
//
//	public int insert(DataRoomDTO dto) {
//		// TODO Auto-generated method stub
//		
//		int affected = 0 ;
//		String sql = "INSERT INTO DATAROOM VALUES(SEQ_DATAROOM.NEXTVAL ,?,?,?,?,?,DEFAULT,SYSDATE)" ;
//		
//		try {
//			psmt = con.prepareStatement(sql);
//			psmt.setString(1, dto.getName());
//			psmt.setString(2, dto.getPassword());
//			psmt.setString(3, dto.getTitle());
//			psmt.setString(4, dto.getContent());
//			psmt.setString(5, dto.getAttachfile());
//			
//			affected = psmt.executeUpdate();
//			
//			
//			
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}
//		
//		
//		return affected;
//	}
//
//	public DataRoomDTO selectOne(String no) {
//		// TODO Auto-generated method stub
//		DataRoomDTO dto = null;
//		
//		String sql = "SELECT * FROM dataroom WHERE no = ?";
//		
//		try {
//			dto = new DataRoomDTO();
//			psmt = con.prepareStatement(sql);
//			psmt.setString(1, no);
//			
//			rs = psmt.executeQuery();
//			rs.next();
//			dto.setNo(rs.getInt(1));
//			dto.setName(rs.getString(2));
//			dto.setPassword(rs.getString(3));
//			dto.setTitle(rs.getString(4));
//			dto.setContent(rs.getString(5));
//			dto.setAttachfile(rs.getString(6));
//			dto.setDowncount(rs.getString(7));
//			dto.setPostdate(rs.getDate(8));
//			
//		}catch(SQLException e){
//			
//			e.printStackTrace();
//		}
//		
//		
//		
//		
//		
//		return dto;
//	}
//
//	public boolean isCorrectPassword(String no, String password) {
//		// TODO Auto-generated method stub
//		
//		
//		String sql = "SELECT PASSWORD FROM DATAROOM WHERE no = ?";
//		
//		
//		try {
//			psmt = con.prepareStatement(sql);
//			psmt.setString(1, no);
//			rs = psmt.executeQuery();
//			if(!rs.next()) {
//				
//			} else if(rs.getString(1).equals(password)) {
//				return true;
//			}
//		}catch(SQLException e) {
//			e.printStackTrace();
//			
//			return false;
//		}
//		
//		return false;
//	}
//
//	public int delete(String no) {
//		// TODO Auto-generated method stub
//
//		int affected = 0 ;
//		String sql = "DELETE DATAROOM WHERE no = ?" ;
//		
//		try {
//			psmt = con.prepareStatement(sql);
//			psmt.setString(1, no);
//			
//			affected = psmt.executeUpdate();
//			
//			
//			
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}
//		
//		
//		return affected;
//	}
//
//	public int update(DataRoomDTO dto) {
//		// TODO Auto-generated method stub
//		
//
//		int affected = 0 ;
//		String sql = "UPDATE DATAROOM SET name = ?, password= ? , title = ? , content = ?, attachfile = ?  WHERE no = ?" ;
//		
//		try {
//			psmt = con.prepareStatement(sql);
//			psmt.setString(1, dto.getName());
//			psmt.setString(2, dto.getPassword());
//			psmt.setString(3, dto.getTitle());
//			psmt.setString(4, dto.getContent());
//			psmt.setString(5, dto.getAttachfile());
//			psmt.setInt(6, dto.getNo());
//			affected = psmt.executeUpdate();
//			
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}
//		
//		
//		return affected;
//		
//	}
//
//	public void updateDownCount(String no) {
//		// TODO Auto-generated method stub
//		
//
//		
//		String sql = "UPDATE dataroom SET downcount = downcount+1 WHERE no = ?";
//		
//		
//		try {
//			psmt = con.prepareStatement(sql);
//			psmt.setString(1, no);
//			psmt.executeUpdate();
//
//		}catch(SQLException e) {
//			e.printStackTrace();
//			
//		}
//		
//		
//	}

	
	
}
