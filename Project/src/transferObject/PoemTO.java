package transferObject;

public class PoemTO {

	String bookName;
	String poemName;
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getPoemName() {
		return poemName;
	}
	public void setPoemName(String poemName) {
		this.poemName = poemName;
	}
	
	public PoemTO(String bookName, String poemName) {
		super();
		this.bookName = bookName;
		this.poemName = poemName;
	}


}
