package entity;

public class Book {
	private String serial;
	private String author;
	private String genreId;
	private String genre;
	private Integer stock;
	
	public Book() {
		
	}

	public Book(String serial, String author, String genreId, String genre, Integer stock) {
		this.serial = serial;
		this.author = author;
		this.genreId = genreId;
		this.genre = genre;
		this.stock = stock;
	}
	
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenreId() {
		return genreId;
	}
	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String display() {
		return "Book [serial=" + serial + ", author=" + author + ", genreId=" + genreId + ", genre=" + genre
				+ ", stock=" + stock + "]";
	}
		
}
