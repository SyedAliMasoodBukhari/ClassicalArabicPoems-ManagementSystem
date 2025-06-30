
package businessLogicLayer;

import java.util.ArrayList;

import transferObject.BookTO;

public interface IBookBO {
	public boolean insertBookBO(BookTO bookTO);
	public boolean updateBookBO(String currBookName,String newBookName,String newBookAuthor);
	public boolean deleteBookBO(String Name);
	public ArrayList<BookTO> getAllBooksBO();
}
