package model;

public class ComBoardDTO {

	private String no;
	private int mb_no;
	private int cus_pid;
	private String cus_nn;

	private String com_content;
	public String getCom_content() {
		return com_content;
	}
	public void setCom_content(String com_content) {
		this.com_content = com_content;
	}
	private java.sql.Date postdate;
	
	
	public String getCus_nn() {
		return cus_nn;
	}
	public void setCus_nn(String cus_nn) {
		this.cus_nn = cus_nn;
	}
	public ComBoardDTO() {
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public int getMb_no() {
		return mb_no;
	}
	public void setMb_no(int mb_no) {
		this.mb_no = mb_no;
	}
	public int getCus_pid() {
		return cus_pid;
	}
	public void setCus_pid(int cus_pid) {
		this.cus_pid = cus_pid;
	}
	public java.sql.Date getPostdate() {
		return postdate;
	}
	public void setPostdate(java.sql.Date postdate) {
		this.postdate = postdate;
	}
	
	
}
