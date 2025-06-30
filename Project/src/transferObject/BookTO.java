package transferObject;

public class BookTO {

    private String bookName;
    private String bookAuthor;
    private int poemsCount;

    public BookTO() {
    }

    public BookTO(String bookName, String bookAuthor, int poemsCount) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.poemsCount = poemsCount;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getPoemsCount() {
        return poemsCount;
    }

    public void setPoemsCount(int poemsCount) {
        this.poemsCount = poemsCount;
    }
    

}
