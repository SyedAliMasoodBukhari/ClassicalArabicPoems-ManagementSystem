//Made by F21_9458
package businessLogicLayer;

import java.util.ArrayList;

import dataAccessLayer.IDALFacade;
import transferObject.BookTO;
import transferObject.PoemsTO;

public class BookBO implements IBookBO {

    private final IDALFacade objDAL;

    public BookBO(IDALFacade idalFacadeObj) {
        this.objDAL = idalFacadeObj;
    }

    @Override
    public boolean insertBookBO(BookTO bookTO) {
        return objDAL.insertBookDAO(bookTO);
    }

    @Override
    public boolean updateBookBO(String currBookName, String newBookName, String newBookAuthor) {
        return objDAL.updateBookDAO(currBookName, newBookName, newBookAuthor);
    }

    @Override
    public boolean deleteBookBO(String bookName) {
        ArrayList<String> result = objDAL.getAllPoemsByBookDAO(bookName);
        for (int i = 0; i < result.size(); ++i) {
            int j = 0;
            ArrayList<PoemsTO> objTOList = objDAL.getAPoemByBookDAO(bookName, result.get(i), 1);
            for (PoemsTO o : objTOList) {
                PoemsTO objTO = new PoemsTO(bookName, result.get(i), o.getMisra1(), o.getMisra2(), "", "");
                if (j < objTOList.size()) {
                    objDAL.deleteRoots(objTO);
                    objDAL.deleteTokens(objTO);
                    j++;
                }
            }

            if (j == objTOList.size()) {
                objDAL.deletePoemDAO(bookName, result.get(i), 1);
            }
        }
        objDAL.deleteEmptyRoots();
        return objDAL.deleteBookDAO(bookName);
    }

    @Override
    public ArrayList<BookTO> getAllBooksBO() {
        return objDAL.getAllBooksDAO();
    }
}
