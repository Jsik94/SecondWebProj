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

public class ComBoardDAO extends MyDAO {

	public ComBoardDAO(ServletContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public List<ComBoardDTO> select(String no) {
		List<ComBoardDTO> list = new Vector();

		String sql = "SELECT * FROM com_board m JOIN custom c ON m.cus_pid = c.cus_pid WHERE mb_no = ? ORDER BY com_no ASC";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, no);

			rs = psmt.executeQuery();

			while (rs.next()) {
				ComBoardDTO dto = new ComBoardDTO();

				dto.setNo(rs.getString(1));
				dto.setMb_no(rs.getInt(2));
				dto.setCus_pid(rs.getInt(3));
				dto.setCom_content(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setCus_nn(rs.getString(9));

				list.add(dto);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return list;
	}

	public int insert(ComBoardDTO dto) {
		int affected = 0;

		String sql = "INSERT INTO com_board VALUES(SEQ_COM_BOARD_COM_NO.NEXTVAL,?,?,?,SYSDATE)";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, dto.getMb_no());
			psmt.setInt(2, dto.getCus_pid());
			psmt.setString(3, dto.getCom_content());

			affected = psmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception

			e.printStackTrace();
		}

		return affected;
	}

	public int delete(String com_no) {
		int affected = 0;

		String sql = "DELETE com_board WHERE com_no = ? ";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, com_no);

			affected = psmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception

			e.printStackTrace();
		}

		return affected;
	}

}
