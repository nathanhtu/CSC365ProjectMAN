package entity;

import java.sql.Date;

public class Checkout {
	private Integer checkoutID;
	private String serial;
	private Integer studentID;
	private Date checkoutDate;
	private Date checkinDate;
	private Date dueDate;
	
	public Checkout() {
		
	}
	
	public Checkout(Integer checkoutID, String serial, Integer studentID, Date checkoutDate, Date checkinDate,
			Date dueDate) {
		this.checkoutID = checkoutID;
		this.serial = serial;
		this.studentID = studentID;
		this.checkoutDate = checkoutDate;
		this.checkinDate = checkinDate;
		this.dueDate = dueDate;
	}

	public Integer getCheckoutID() {
		return checkoutID;
	}

	public void setCheckoutID(Integer checkoutID) {
		this.checkoutID = checkoutID;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Integer getStudentID() {
		return studentID;
	}

	public void setStudentID(Integer studentID) {
		this.studentID = studentID;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public Date getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String display() {
		return "Checkout [checkoutID=" + checkoutID + ", serial=" + serial + ", studentID=" + studentID
				+ ", checkoutDate=" + checkoutDate + ", checkinDate=" + checkinDate + ", dueDate=" + dueDate + "]";
	}
}
