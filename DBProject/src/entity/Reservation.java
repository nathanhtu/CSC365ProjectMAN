package entity;

public class Reservation {
	private Integer reservationID;
	private String serial;
	private Integer studentID;
	private boolean checkedOut;
	
	public Reservation() {
		
	}

	public Reservation(Integer reservationID, String serial, Integer studentID, boolean checkedOut) {
		this.reservationID = reservationID;
		this.serial = serial;
		this.studentID = studentID;
		this.checkedOut = checkedOut;
	}

	public Integer getReservationID() {
		return reservationID;
	}

	public void setReservationID(Integer reservationID) {
		this.reservationID = reservationID;
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

	public boolean isCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	public String display() {
		return "Reservation [reservationID=" + reservationID + ", serial=" + serial + ", studentID=" + studentID
				+ ", checkedOut=" + checkedOut + "]";
	}
	
	
}
