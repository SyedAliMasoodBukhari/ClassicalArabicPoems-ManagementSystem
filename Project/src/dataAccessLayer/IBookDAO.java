
package dataAccessLayer;

import java.util.ArrayList;

import transferObject.BookTO;

public interface IBookDAO {

	public boolean insertBookDAO(BookTO bookTO);
	public boolean updateBookDAO(String currBookName,String newBookName,String newBookAuthor);
	public boolean deleteBookDAO(String bookName);
	public ArrayList<BookTO> getAllBooksDAO();
}
