package entity;

public class Student {
	private Integer studentID;
	private String firstName;
	private String lastName;
	private String status;
	private Integer numBooks;
	
	public Student() {	
	}
	
	public Student(Integer studentID, String firstName, String lastName, String status, Integer numBooks) {
		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.numBooks = numBooks;
	}
	
	public Integer getStudentID() {
		return studentID;
	}
	
	public void setStudentID(Integer studentID) {
		this.studentID = studentID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getNumBooks() {
		return numBooks;
	}
	
	public void setNumBooks(Integer numBooks) {
		this.numBooks = numBooks;
	}
	
	public String display() {
		return "Student [studentID=" + studentID + ", firstName=" + firstName + ", lastName=" + lastName + ", status="
				+ status + ", numBooks=" + numBooks + "]";
	}
}
