// Made by Ali Masood 21f-9417
// Transfer Object for poems to transfer data between PoemsBO and PoemsDAO
package transferObject;

public class PoemsTO {

    private String bookTitle; // to set poem against this book title
    private String poemTitle;
    private String misra1;
    private String misra2;
    private String misra1WithoutAraab;
    private String misra2WithoutAraab;

    public PoemsTO(String bookTitle, String poemTitle, String misra1, String misra2, String misra1WithoutAraab, String misra2WithoutAraab) {
        this.bookTitle = bookTitle;
        this.poemTitle = poemTitle;
        this.misra1 = misra1;
        this.misra2 = misra2;
        this.misra1WithoutAraab = misra1WithoutAraab;
        this.misra2WithoutAraab = misra2WithoutAraab;
    }
    
    public PoemsTO() {
        super();
    }
    

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getPoemTitle() {
        return poemTitle;
    }

    public void setPoemTitle(String poemTitle) {
        this.poemTitle = poemTitle;
    }

    public String getMisra1() {
        return misra1;
    }

    public void setMisra1(String misra1) {
        this.misra1 = misra1;
    }

    public String getMisra2() {
        return misra2;
    }

    public void setMisra2(String misra2) {
        this.misra2 = misra2;
    }

    public String getMisra1WithoutAraab() {
        return misra1WithoutAraab;
    }

    public void setMisra1WithoutAraab(String misra1WithoutAraab) {
        this.misra1WithoutAraab = misra1WithoutAraab;
    }

    public String getMisra2WithoutAraab() {
        return misra2WithoutAraab;
    }

    public void setMisra2WithoutAraab(String misra2WithoutAraab) {
        this.misra2WithoutAraab = misra2WithoutAraab;
    }

}
